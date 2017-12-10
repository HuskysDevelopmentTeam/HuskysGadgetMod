package net.thegaminghuskymc.gadgetmod.network.task;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.api.ApplicationManager;
import net.thegaminghuskymc.gadgetmod.object.AppInfo;
import net.thegaminghuskymc.gadgetmod.proxy.CommonProxy;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public class MessageSyncApplications implements IMessage, IMessageHandler<MessageSyncApplications, MessageSyncApplications> {
    private List<AppInfo> allowedApps;

    public MessageSyncApplications() {
    }

    public MessageSyncApplications(List<AppInfo> allowedApps) {
        this.allowedApps = allowedApps;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(allowedApps.size());
        for (AppInfo appInfo : allowedApps) {
            ByteBufUtils.writeUTF8String(buf, appInfo.getId().toString());
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        int size = buf.readInt();
        allowedApps = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            AppInfo info = ApplicationManager.getApplication(ByteBufUtils.readUTF8String(buf));
            allowedApps.add(info);
        }
    }

    @Override
    public MessageSyncApplications onMessage(MessageSyncApplications message, MessageContext ctx) {
        ReflectionHelper.setPrivateValue(CommonProxy.class, HuskyGadgetMod.proxy, message.allowedApps, "allowedApps");
        return null;
    }
}
