package io.github.vampirestudios.gadget;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class DamageSourceFence extends DamageSource
{
	public DamageSourceFence(String damageTypeIn)
	{
		super(damageTypeIn);
	}
	
	@Override
	public ITextComponent getDeathMessage(EntityLivingBase entityLivingBaseIn)
	{
		return new TextComponentString(entityLivingBaseIn.getName() + " was zapped to death while trying to get inside the military base without an ID card");
	}
}