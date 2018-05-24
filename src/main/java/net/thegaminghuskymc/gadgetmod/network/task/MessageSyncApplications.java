package net.thegaminghuskymc.gadgetmod.network.task;

import com.google.common.collect.ImmutableList;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.thegaminghuskymc.gadgetmod.HuskyGadgetMod;
import net.thegaminghuskymc.gadgetmod.api.AppInfo;
import net.thegaminghuskymc.gadgetmod.api.ApplicationManager;

import java.util.List;

public class MessageSyncApplications implements IMessage, IMessageHandler<MessageSyncApplications, MessageSyncApplications> {
    private List<AppInfo> allowedApps;

    public MessageSyncApplications() {}

    public MessageSyncApplications(List<AppInfo> allowedApps)
    {
        this.allowedApps = allowedApps;
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(allowedApps.size());
        for(AppInfo appInfo : allowedApps)
        {
            ByteBufUtils.writeUTF8String(buf, appInfo.getId().toString());
        }
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        int size = buf.readInt();
        ImmutableList.Builder<AppInfo> builder = new ImmutableList.Builder<>();
        for(int i = 0; i < size; i++)
        {
            String appId = ByteBufUtils.readUTF8String(buf);
            AppInfo info = ApplicationManager.getApplication(appId);
            if(info != null)
            {
                builder.add(info);
            }
            else
            {
                HuskyGadgetMod.getLogger().error("Missing application '" + appId + "'");
            }
        }
        allowedApps = builder.build();
    }

    @Override
    public MessageSyncApplications onMessage(MessageSyncApplications message, MessageContext ctx)
    {
        ReflectionHelper.setPrivateValue(ApplicationManager.class, null, message.allowedApps, "whitelistedApps");
        return null;
    }
}
