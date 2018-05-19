package net.thegaminghuskymc.gadgetmod.core.io.task;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.thegaminghuskymc.gadgetmod.api.io.Drive;
import net.thegaminghuskymc.gadgetmod.api.io.Folder;
import net.thegaminghuskymc.gadgetmod.api.task.Task;
import net.thegaminghuskymc.gadgetmod.core.BaseDevice;
import net.thegaminghuskymc.gadgetmod.core.io.FileSystem;
import net.thegaminghuskymc.gadgetmod.core.io.drive.AbstractDrive;
import net.thegaminghuskymc.gadgetmod.tileentity.TileEntityBaseDevice;

public class TaskGetMainDrive extends Task {
    private BlockPos pos;

    private AbstractDrive mainDrive;

    private TaskGetMainDrive() {
        super("get_main_drive");
    }

    public TaskGetMainDrive(BlockPos pos) {
        this();
        this.pos = pos;
    }

    @Override
    public void prepareRequest(NBTTagCompound nbt) {
        nbt.setLong("pos", pos.toLong());
    }

    @Override
    public void processRequest(NBTTagCompound nbt, World world, EntityPlayer player) {
        TileEntity tileEntity = world.getTileEntity(BlockPos.fromLong(nbt.getLong("pos")));
        if (tileEntity instanceof TileEntityBaseDevice) {
            TileEntityBaseDevice laptop = (TileEntityBaseDevice) tileEntity;
            FileSystem fileSystem = laptop.getFileSystem();
            mainDrive = fileSystem.getMainDrive();
            this.setSuccessful();
        }
    }

    @Override
    public void prepareResponse(NBTTagCompound nbt) {
        if (this.isSucessful()) {
            NBTTagCompound mainDriveTag = new NBTTagCompound();
            mainDriveTag.setString("name", mainDrive.getName());
            mainDriveTag.setString("uuid", mainDrive.getUUID().toString());
            mainDriveTag.setString("type", mainDrive.getType().toString());
            nbt.setTag("main_drive", mainDriveTag);
            nbt.setTag("structure", mainDrive.getDriveStructure().toTag());
        }
    }

    @Override
    public void processResponse(NBTTagCompound nbt) {
        if (this.isSucessful()) {
            if (Minecraft.getMinecraft().currentScreen instanceof BaseDevice) {
                NBTTagCompound structureTag = nbt.getCompoundTag("structure");
                Drive drive = new Drive(nbt.getCompoundTag("main_drive"));
                drive.syncRoot(Folder.fromTag(FileSystem.LAPTOP_DRIVE_NAME, structureTag));
                drive.getRoot().validate();

                if (BaseDevice.getMainDrive() == null) {
                    BaseDevice.setMainDrive(drive);
                }
            }
        }
    }
}
