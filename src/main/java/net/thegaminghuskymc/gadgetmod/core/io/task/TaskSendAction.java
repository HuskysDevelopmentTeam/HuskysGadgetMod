package net.thegaminghuskymc.gadgetmod.core.io.task;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.thegaminghuskymc.gadgetmod.api.io.Drive;
import net.thegaminghuskymc.gadgetmod.api.task.Task;
import net.thegaminghuskymc.gadgetmod.core.BaseDevice;
import net.thegaminghuskymc.gadgetmod.core.io.FileSystem;
import net.thegaminghuskymc.gadgetmod.core.io.action.FileAction;
import net.thegaminghuskymc.gadgetmod.tileentity.TileEntityLaptop;

public class TaskSendAction extends Task {
    private String uuid;
    private FileAction action;
    private BlockPos pos;

    private FileSystem.Response response;

    private TaskSendAction() {
        super("send_action");
    }

    public TaskSendAction(Drive drive, FileAction action) {
        this();
        this.uuid = drive.getUUID().toString();
        this.action = action;
        this.pos = BaseDevice.getPos();
    }

    @Override
    public void prepareRequest(NBTTagCompound nbt) {
        nbt.setString("uuid", uuid);
        nbt.setTag("action", action.toTag());
        nbt.setLong("pos", pos.toLong());
    }

    @Override
    public void processRequest(NBTTagCompound nbt, World world, EntityPlayer player) {
        FileAction action = FileAction.fromTag(nbt.getCompoundTag("action"));
        TileEntity tileEntity = world.getTileEntity(BlockPos.fromLong(nbt.getLong("pos")));
        if (tileEntity instanceof TileEntityLaptop) {
            TileEntityLaptop laptop = (TileEntityLaptop) tileEntity;
            response = laptop.getFileSystem().readAction(nbt.getString("uuid"), action, world);
            this.setSuccessful();
        }
    }

    @Override
    public void prepareResponse(NBTTagCompound nbt) {
        nbt.setTag("response", response.toTag());
    }

    @Override
    public void processResponse(NBTTagCompound nbt) {

    }
}
