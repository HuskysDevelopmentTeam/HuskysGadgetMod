package net.husky.device.programs.social_medias.task;

import net.husky.device.api.task.Task;
import net.husky.device.programs.email.ApplicationEmail.Email;
import net.husky.device.programs.email.ApplicationEmail.EmailManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.List;

public class TaskViewMessage extends Task
{
	private int index;
	
	public TaskViewMessage()
	{
		super("view_messages");
	}
	
	public TaskViewMessage(int index)
	{
		this();
		this.index = index;
	}
	
	@Override
	public void prepareRequest(NBTTagCompound nbt) 
	{
		nbt.setInteger("Index", this.index);
	}

	@Override
	public void processRequest(NBTTagCompound nbt, World world, EntityPlayer player) 
	{
		List<Email> emails = EmailManager.INSTANCE.getEmailsForAccount(player);
		if(emails != null)
		{
			int index = nbt.getInteger("Index");
			if(index >= 0 && index < emails.size())
			{
				emails.get(index).setRead(true);
			}
		}
	}

	@Override
	public void prepareResponse(NBTTagCompound nbt) {}

	@Override
	public void processResponse(NBTTagCompound nbt) {}
	
}
