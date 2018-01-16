package net.thegaminghuskymc.gadgetmod.core.io.drive;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.thegaminghuskymc.gadgetmod.core.io.FileSystem;
import net.thegaminghuskymc.gadgetmod.core.io.ServerFolder;
import net.thegaminghuskymc.gadgetmod.core.io.action.FileAction;

import javax.annotation.Nullable;
import java.util.UUID;
import java.util.function.Predicate;

public final class NetworkDrive extends AbstractDrive {

    private static final Predicate<NBTTagCompound> PREDICATE_DRIVE_TAG = tag ->
            tag.hasKey("name", Constants.NBT.TAG_STRING)
                    && tag.hasKey("uuid", Constants.NBT.TAG_STRING)
                    && tag.hasKey("root", Constants.NBT.TAG_COMPOUND);

    private BlockPos pos;

    private NetworkDrive() {}

    public NetworkDrive(String name, BlockPos pos) {
        super(name);
        this.pos = pos;
        this.root = null;
    }

    @Nullable
    @Override
    public ServerFolder getRoot(World world) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof Interface) {
            Interface impl = (Interface) tileEntity;
            AbstractDrive drive = impl.getDrive();
            if (drive != null) {
                return drive.getRoot(world);
            }
        }
        return null;
    }

    @Override
    public FileSystem.Response handleFileAction(FileSystem fileSystem, FileAction action, World world) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof Interface) {
            Interface impl = (Interface) tileEntity;
            AbstractDrive drive = impl.getDrive();
            if (drive.handleFileAction(fileSystem, action, world).getStatus() == FileSystem.Status.SUCCESSFUL) {
                tileEntity.markDirty();
                return FileSystem.createSuccessResponse();
            }
        }
        return FileSystem.createResponse(FileSystem.Status.DRIVE_UNAVAILABLE, "The network drive could not be found");
    }

    @Nullable
    @Override
    public ServerFolder getFolder(String path) {
        return null;
    }

    @Nullable
    public static AbstractDrive fromTag(NBTTagCompound driveTag) {
        if (!PREDICATE_DRIVE_TAG.test(driveTag))
            return null;

        AbstractDrive drive = new NetworkDrive();
        drive.name = driveTag.getString("name");
        drive.uuid = UUID.fromString(driveTag.getString("uuid"));

        NBTTagCompound folderTag = driveTag.getCompoundTag("root");
        drive.root = ServerFolder.fromTag(folderTag.getString("file_name"), folderTag.getCompoundTag("data"));

        return drive;
    }

    @Override
    public NBTTagCompound toTag() {
        NBTTagCompound driveTag = new NBTTagCompound();
        driveTag.setString("name", name);
        driveTag.setString("uuid", uuid.toString());

        NBTTagCompound folderTag = new NBTTagCompound();
        folderTag.setString("file_name", root.getName());
        folderTag.setTag("data", root.toTag());
        driveTag.setTag("root", folderTag);

        return driveTag;
    }

    @Override
    public Type getType() {
        return Type.NETWORK;
    }

    public interface Interface {
        AbstractDrive getDrive();

        boolean canAccessDrive();
    }
}
