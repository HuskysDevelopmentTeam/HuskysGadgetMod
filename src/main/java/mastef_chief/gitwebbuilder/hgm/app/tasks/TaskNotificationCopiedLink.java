package mastef_chief.gitwebbuilder.hgm.app.tasks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.thegaminghuskymc.gadgetmod.api.app.Notification;
import net.thegaminghuskymc.gadgetmod.api.app.annontation.DeviceTask;
import net.thegaminghuskymc.gadgetmod.api.app.emojie_packs.Icons;
import net.thegaminghuskymc.gadgetmod.api.task.Task;

import static mastef_chief.gitwebbuilder.hgm.Reference.MOD_ID;

@DeviceTask(modId = MOD_ID, taskId = "notification_copiedlink")
public class TaskNotificationCopiedLink extends Task {

    public TaskNotificationCopiedLink() {}

    /**
     * Called before the request is sent off to the server.
     * You should store the data you want to sendTask into the NBT Tag
     *
     * @param nbt The NBT to be sent to the server
     */
    @Override
    public void prepareRequest(NBTTagCompound nbt) {

    }

    /**
     * Called when the request arrives to the server. Here you can perform actions
     * with your request. Data attached to the NBT from {@link Task#prepareRequest(NBTTagCompound nbt)}
     * can be accessed from the NBT tag parameter.
     *
     * @param nbt    The NBT Tag received from the client
     * @param world  The World it will get called from
     * @param player The Player it get's called from
     */
    @Override
    public void processRequest(NBTTagCompound nbt, World world, EntityPlayer player) {
        Notification notification = new Notification(Icons.COPY, TextFormatting.BOLD + "Copied", "Link To Clipboard");
        notification.pushTo((EntityPlayerMP)player);
    }

    /**
     * Called before the response is sent back to the client.
     * You should store the data you want to sendTask back into the NBT Tag
     *
     * @param nbt The NBT to be sent back to the client
     */
    @Override
    public void prepareResponse(NBTTagCompound nbt) {

    }

    /**
     * Called when the response arrives to the client. Here you can update data
     * on the client side. If you want to update any UI component, you should set
     * a Callback before you sendTask the request.
     *
     * @param nbt The NBT Tag received from the server
     */
    @Override
    public void processResponse(NBTTagCompound nbt) {

    }
}
