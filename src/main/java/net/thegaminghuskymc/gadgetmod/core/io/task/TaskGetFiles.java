package net.thegaminghuskymc.gadgetmod.core.io.task;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.thegaminghuskymc.gadgetmod.api.io.Folder;
import net.thegaminghuskymc.gadgetmod.api.task.Task;
import net.thegaminghuskymc.gadgetmod.core.io.FileSystem;
import net.thegaminghuskymc.gadgetmod.core.io.ServerFile;
import net.thegaminghuskymc.gadgetmod.core.io.ServerFolder;
import net.thegaminghuskymc.gadgetmod.core.io.drive.AbstractDrive;
import net.thegaminghuskymc.gadgetmod.tileentity.TileEntityLaptop;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class TaskGetFiles extends Task {
    private String uuid;
    private String path;
    private BlockPos pos;

    private List<ServerFile> files;

    private TaskGetFiles() {
        super("get_files");
    }

    public TaskGetFiles(Folder folder, BlockPos pos) {
        this();
        this.uuid = folder.getDrive().getUUID().toString();
        this.path = folder.getPath();
        this.pos = pos;
    }

    protected static String compileDirectory(ServerFile file) {
        if (file.getParent() == null || file.getParent().getParent() == null)
            return "/";

        StringBuilder builder = new StringBuilder();
        ServerFolder parent = file.getParent();
        while (parent != null) {
            builder.insert(0, "/" + parent.getName());
            if (parent.getParent() != null) {
                return builder.toString();
            }
            parent = parent.getParent();
        }
        return builder.toString();
    }

    @Override
    public void prepareRequest(NBTTagCompound nbt) {
        nbt.setString("uuid", uuid);
        nbt.setString("path", path);
        nbt.setLong("pos", pos.toLong());
    }

    @Override
    public void processRequest(NBTTagCompound nbt, World world, EntityPlayer player) {
        TileEntity tileEntity = world.getTileEntity(BlockPos.fromLong(nbt.getLong("pos")));
        if (tileEntity instanceof TileEntityLaptop) {
            TileEntityLaptop laptop = (TileEntityLaptop) tileEntity;
            FileSystem fileSystem = laptop.getFileSystem();
            UUID uuid = UUID.fromString(nbt.getString("uuid"));
            AbstractDrive serverDrive = fileSystem.getAvailableDrives(world, true).get(uuid);
            if (serverDrive != null) {
                ServerFolder found = serverDrive.getFolder(nbt.getString("path"));
                if (found != null) {
                    this.files = found.getFiles().stream().filter(f -> !f.isFolder()).collect(Collectors.toList());
                    this.setSuccessful();
                }
            }
        }
    }

    @Override
    public void prepareResponse(NBTTagCompound nbt) {
        if (this.files != null) {
            NBTTagList list = new NBTTagList();
            this.files.forEach(f -> {
                NBTTagCompound fileTag = new NBTTagCompound();
                fileTag.setString("file_name", f.getName());
                fileTag.setTag("data", f.toTag());
                list.appendTag(fileTag);
            });
            nbt.setTag("files", list);
        }
    }

    @Override
    public void processResponse(NBTTagCompound nbt) {

    }
}
