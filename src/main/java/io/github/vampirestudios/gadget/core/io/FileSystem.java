package io.github.vampirestudios.gadget.core.io;

import io.github.vampirestudios.gadget.Reference;
import io.github.vampirestudios.gadget.api.ApplicationManager;
import io.github.vampirestudios.gadget.api.app.Application;
import io.github.vampirestudios.gadget.api.io.Drive;
import io.github.vampirestudios.gadget.api.io.Folder;
import io.github.vampirestudios.gadget.api.task.Callback;
import io.github.vampirestudios.gadget.api.task.Task;
import io.github.vampirestudios.gadget.api.task.TaskManager;
import io.github.vampirestudios.gadget.core.BaseDevice;
import io.github.vampirestudios.gadget.core.io.action.FileAction;
import io.github.vampirestudios.gadget.core.io.drive.AbstractDrive;
import io.github.vampirestudios.gadget.core.io.drive.ExternalDrive;
import io.github.vampirestudios.gadget.core.io.drive.InternalDrive;
import io.github.vampirestudios.gadget.core.io.task.TaskGetFiles;
import io.github.vampirestudios.gadget.core.io.task.TaskGetMainDrive;
import io.github.vampirestudios.gadget.core.io.task.TaskSendAction;
import io.github.vampirestudios.gadget.tileentity.TileEntityBaseDevice;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.regex.Pattern;

public class FileSystem {
    public static final Pattern PATTERN_FILE_NAME = Pattern.compile("^[\\w'. _]{1,32}$");
    public static final Pattern PATTERN_DIRECTORY = Pattern.compile("^(/)|(/[\\w'. _]{1,32})*$");

    public static final String DIR_ROOT = "/";
    public static final String DIR_APPLICATION_DATA = DIR_ROOT + "Programfiles";
    public static final String DIR_APPLICATION_DATA_32 = DIR_ROOT + "Programfiles (x86)";
    public static final String DIR_HOME = DIR_ROOT + "NeonOS";
    public static final String DIR_USERS = DIR_ROOT + "Users";
    public static final String LAPTOP_DRIVE_NAME = "NeonOS (C:)";

    private AbstractDrive mainDrive = null;
    private Map<UUID, AbstractDrive> additionalDrives = new HashMap<>();
    private AbstractDrive attachedDrive = null;

    private TileEntityBaseDevice tileEntity;

    private EnumDyeColor attachedDriveColor = EnumDyeColor.WHITE;

    public FileSystem(TileEntityBaseDevice tileEntity, NBTTagCompound fileSystemTag) {
        this.tileEntity = tileEntity;

        load(fileSystemTag);
    }

    @SideOnly(Side.CLIENT)
    public static void sendAction(Drive drive, FileAction action, @Nullable Callback<Response> callback) {
        if (BaseDevice.getPos() != null) {
            Task task = new TaskSendAction(drive, action);
            task.setCallback((nbt, success) ->
            {
                if (callback != null) {
                    callback.execute(Response.fromTag(nbt.getCompoundTag("response")), success);
                }
            });
            TaskManager.sendTask(task);
        }
    }

    public static void getApplicationFolder(Application app, Callback<Folder> callback) {
        if(!ApplicationManager.getSystemApplications().contains(app.getInfo()))
        {
            callback.execute(null, false);
            return;
        }
        if (BaseDevice.getMainDrive() == null) {
            Task task = new TaskGetMainDrive(BaseDevice.getPos());
            task.setCallback((nbt, success) ->
            {
                if (success) {
                    setupApplicationFolder(app, callback);
                } else {
                    callback.execute(null, false);
                }
            });
            TaskManager.sendTask(task);
        } else {
            setupApplicationFolder(app, callback);
        }
    }

    private static void setupApplicationFolder(Application app, Callback<Folder> callback) {
        Folder folder = BaseDevice.getMainDrive().getFolder(FileSystem.DIR_APPLICATION_DATA);
        if (folder != null) {
            if (folder.hasFolder(app.getInfo().getFormattedId())) {
                Folder appFolder = folder.getFolder(app.getInfo().getFormattedId());
                if (appFolder.isSynced()) {
                    callback.execute(appFolder, true);
                } else {
                    Task task = new TaskGetFiles(appFolder, BaseDevice.getPos());
                    task.setCallback((nbt, success) ->
                    {
                        if (success && nbt.hasKey("files", Constants.NBT.TAG_LIST)) {
                            NBTTagList files = nbt.getTagList("files", Constants.NBT.TAG_COMPOUND);
                            appFolder.syncFiles(files);
                            callback.execute(appFolder, true);
                        } else {
                            callback.execute(null, false);
                        }
                    });
                    TaskManager.sendTask(task);
                }
            } else {
                Folder appFolder = new Folder(app.getInfo().getFormattedId());
                folder.add(appFolder, (response, success) ->
                {
                    if (response != null && response.getStatus() == Status.SUCCESSFUL) {
                        callback.execute(appFolder, true);
                    } else {
                        callback.execute(null, false);
                    }
                });
            }
        } else {
            callback.execute(null, false);
        }
    }

    public static Response createSuccessResponse() {
        return new Response(Status.SUCCESSFUL);
    }

    public static Response createResponse(int status, String message) {
        return new Response(status, message);
    }

    private void load(NBTTagCompound fileSystemTag) {
        if (fileSystemTag.hasKey("main_drive", Constants.NBT.TAG_COMPOUND)) {
            mainDrive = InternalDrive.fromTag(fileSystemTag.getCompoundTag("main_drive"));
        }

        if (fileSystemTag.hasKey("drives", Constants.NBT.TAG_LIST)) {
            NBTTagList tagList = fileSystemTag.getTagList("drives", Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < tagList.tagCount(); i++) {
                NBTTagCompound driveTag = tagList.getCompoundTagAt(i);
                AbstractDrive drive = InternalDrive.fromTag(driveTag.getCompoundTag("drive"));
                additionalDrives.put(drive.getUUID(), drive);
            }
        }

        if (fileSystemTag.hasKey("external_drive", Constants.NBT.TAG_COMPOUND)) {
            attachedDrive = ExternalDrive.fromTag(fileSystemTag.getCompoundTag("external_drive"));
        }

        if (fileSystemTag.hasKey("external_drive_color", Constants.NBT.TAG_BYTE)) {
            attachedDriveColor = EnumDyeColor.byMetadata(fileSystemTag.getByte("external_drive_color"));
        }

        setupDefault();
    }

    /**
     * Sets up the default folders for the file system if they don't exist.
     */
    private void setupDefault() {
        if (mainDrive == null) {
            AbstractDrive drive = new InternalDrive(LAPTOP_DRIVE_NAME);
            ServerFolder root = drive.getRoot(tileEntity.getWorld());
            root.add(createProtectedFolder(DIR_USERS), false);
            root.add(createProtectedFolder("Programfiles"), false);
            root.add(createProtectedFolder("Programfiles (x86)"), false);
            root.add(createProtectedFolder("NeonOS"), false);
            mainDrive = drive;
            tileEntity.markDirty();
        }
    }

    private ServerFolder createProtectedFolder(String name) {
        try {
            Constructor<ServerFolder> constructor = ServerFolder.class.getDeclaredConstructor(String.class, boolean.class);
            constructor.setAccessible(true);
            return constructor.newInstance(name, true);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Response readAction(String driveUuid, FileAction action, World world) {
        UUID uuid = UUID.fromString(driveUuid);
        AbstractDrive drive = getAvailableDrives(world, true).get(uuid);
        if (drive != null) {
            Response response = drive.handleFileAction(this, action, world);
            if (response.getStatus() == Status.SUCCESSFUL) {
                tileEntity.markDirty();
            }
            return response;
        }
        return createResponse(Status.DRIVE_UNAVAILABLE, "Drive unavailable or missing");
    }

    public AbstractDrive getMainDrive() {
        return mainDrive;
    }

    public Map<UUID, AbstractDrive> getAvailableDrives(World world, boolean includeMain) {
        Map<UUID, AbstractDrive> drives = new LinkedHashMap<>();

        if (includeMain)
            drives.put(mainDrive.getUUID(), mainDrive);

        additionalDrives.forEach(drives::put);

        if (attachedDrive != null)
            drives.put(attachedDrive.getUUID(), attachedDrive);

        //TODO add network drives
        return drives;
    }

    public boolean setAttachedDrive(ItemStack flashDrive) {
        if (attachedDrive == null) {
            NBTTagCompound flashDriveTag = getExternalDriveTag(flashDrive);
            AbstractDrive drive = ExternalDrive.fromTag(flashDriveTag.getCompoundTag("drive"));
            if (drive != null) {
                drive.setName(flashDrive.getDisplayName());
                attachedDrive = drive;
                attachedDriveColor = EnumDyeColor.byMetadata(flashDrive.getMetadata());

                tileEntity.getPipeline().setByte("external_drive_color", (byte) attachedDriveColor.getMetadata());
                tileEntity.sync();

                return true;
            }
        }
        return false;
    }

    public AbstractDrive getAttachedDrive() {
        return attachedDrive;
    }

    public EnumDyeColor getAttachedDriveColor() {
        return attachedDriveColor;
    }

    @Nullable
    public ItemStack removeAttachedDrive() {
        if (attachedDrive != null) {
            ItemStack stack = new ItemStack(Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(Reference.MOD_ID, "flash_drive_" + getAttachedDriveColor().getName()))), 1, getAttachedDriveColor().getMetadata());
            stack.setStackDisplayName(attachedDrive.getName());
            stack.getTagCompound().setTag("drive", attachedDrive.toTag());
            attachedDrive = null;
            return stack;
        }
        return null;
    }

    private NBTTagCompound getExternalDriveTag(ItemStack stack) {
        NBTTagCompound tagCompound = stack.getTagCompound();
        if (tagCompound == null) {
            tagCompound = new NBTTagCompound();
            tagCompound.setTag("drive", new ExternalDrive(stack.getDisplayName()).toTag());
            stack.setTagCompound(tagCompound);
        } else if (!tagCompound.hasKey("drive", Constants.NBT.TAG_COMPOUND)) {
            tagCompound.setTag("drive", new ExternalDrive(stack.getDisplayName()).toTag());
        }
        return tagCompound;
    }

    public NBTTagCompound toTag() {
        NBTTagCompound fileSystemTag = new NBTTagCompound();

        if (mainDrive != null)
            fileSystemTag.setTag("main_drive", mainDrive.toTag());

        NBTTagList tagList = new NBTTagList();
        additionalDrives.forEach((k, v) -> tagList.appendTag(v.toTag()));
        fileSystemTag.setTag("drives", tagList);

        if (attachedDrive != null) {
            fileSystemTag.setTag("external_drive", attachedDrive.toTag());
            fileSystemTag.setByte("external_drive_color", (byte) attachedDriveColor.getMetadata());
        }

        return fileSystemTag;
    }

    public static class Response {
        private final int status;
        private String message = "";

        private Response(int status) {
            this.status = status;
        }

        private Response(int status, String message) {
            this.status = status;
            this.message = message;
        }

        public static Response fromTag(NBTTagCompound responseTag) {
            return new Response(responseTag.getInteger("status"), responseTag.getString("message"));
        }

        public int getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }

        public NBTTagCompound toTag() {
            NBTTagCompound responseTag = new NBTTagCompound();
            responseTag.setInteger("status", status);
            responseTag.setString("message", message);
            return responseTag;
        }
    }

    public static class Status {
        public static final int FAILED = 0;
        public static final int SUCCESSFUL = 1;
        public static final int FILE_INVALID = 2;
        public static final int FILE_IS_PROTECTED = 3;
        public static final int FILE_EXISTS = 4;
        public static final int FILE_INVALID_NAME = 5;
        public static final int FILE_INVALID_DATA = 6;
        public static final int DRIVE_UNAVAILABLE = 7;
    }
}
