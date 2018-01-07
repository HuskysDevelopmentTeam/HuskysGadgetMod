package net.thegaminghuskymc.gadgetmod.network.task;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.thegaminghuskymc.gadgetmod.init.GadgetBlocks;

public class MessageUnlockAdvancement implements IMessage, IMessageHandler<MessageUnlockAdvancement, IMessage> {

    @Override
    public void fromBytes(ByteBuf buf) {
    }

    @Override
    public void toBytes(ByteBuf buf) {
    }

    @Override
    public IMessage onMessage(MessageUnlockAdvancement message, MessageContext ctx) {
        EntityPlayer pl = ctx.getServerHandler().player;
        World w = pl.world;
        int rad = 10;
        int x = (int) pl.posX + w.rand.nextInt(rad * 2) - rad;
        int z = (int) pl.posZ + w.rand.nextInt(rad * 2) - rad;
        int y = w.getHeight(x, z);
        BlockPos pos = new BlockPos(x, y, z);
        w.setBlockState(pos, GadgetBlocks.EASTER_EGG.getDefaultState());
        return null;
    }

}
