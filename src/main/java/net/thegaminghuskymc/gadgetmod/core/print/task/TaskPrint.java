package net.thegaminghuskymc.gadgetmod.core.print.task;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.thegaminghuskymc.gadgetmod.api.print.IPrint;
import net.thegaminghuskymc.gadgetmod.api.task.Task;
import net.thegaminghuskymc.gadgetmod.tileentity.TileEntityPrinter;

/**
 * Author: MrCrayfish
 */
public class TaskPrint extends Task {
    private BlockPos pos;
    private IPrint print;

    private TaskPrint() {
        super("print");
    }

    public TaskPrint(BlockPos pos, IPrint print) {
        this();
        this.pos = pos;
        this.print = print;
    }

    @Override
    public void prepareRequest(NBTTagCompound nbt) {
        nbt.setLong("pos", pos.toLong());
        nbt.setTag("print", IPrint.writeToTag(print));
    }

    @Override
    public void processRequest(NBTTagCompound nbt, World world, EntityPlayer player) {
        TileEntity tileEntity = world.getTileEntity(BlockPos.fromLong(nbt.getLong("pos")));
        if (tileEntity instanceof TileEntityPrinter) {
            TileEntityPrinter printer = (TileEntityPrinter) tileEntity;
            IPrint print = IPrint.loadFromTag(nbt.getCompoundTag("print"));
            printer.addToQueue(print);
            this.setSuccessful();
        }
    }

    @Override
    public void prepareResponse(NBTTagCompound nbt) {

    }

    @Override
    public void processResponse(NBTTagCompound nbt) {

    }
}
