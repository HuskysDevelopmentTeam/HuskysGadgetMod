package net.thegaminghuskymc.gadgetmod.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.thegaminghuskymc.gadgetmod.network.task.*;

public class PacketHandler {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);

    private static int ID = 1;
    
    public static void init() {
        registerMessage(MessageRequest.class, MessageRequest.class, Side.SERVER);
        registerMessage(MessageResponse.class, MessageResponse.class, Side.CLIENT);
        registerMessage(MessageSyncApplications.class, MessageSyncApplications.class, Side.CLIENT);
        registerMessage(MessageSyncConfig.class, MessageSyncConfig.class, Side.CLIENT);
        registerMessage(MessageSyncBlock.class, MessageSyncBlock.class, Side.SERVER);
        registerMessage(MessageUnlockAdvancement.class, MessageUnlockAdvancement.class, Side.SERVER);
        registerMessage(MessageNotification.class, MessageNotification.class, Side.CLIENT);
    }
    
    public static <REQ extends IMessage, REPLY extends IMessage> void registerMessage(Class<? extends IMessageHandler<REQ, REPLY>> handlerClazz, Class<REQ> messageClazz, Side side) {
    	INSTANCE.registerMessage(handlerClazz, messageClazz, ID, side);
    	ID++;
    }
}
