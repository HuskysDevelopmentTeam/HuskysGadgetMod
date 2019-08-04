package io.github.vampirestudios.gadget.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import io.github.vampirestudios.gadget.Reference;

public class ItemEasterEgg extends ItemFood {

    public ItemEasterEgg() {
        super(4, 0.3F, false);
        this.setRegistryName(Reference.MOD_ID, "easter_egg_item");
        this.setTranslationKey("easter_egg");
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
        super.onFoodEaten(stack, worldIn, player);
        player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 300, 2));
        player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 300, 1));
        player.addPotionEffect(new PotionEffect(MobEffects.SATURATION, 300, 10));
    }

}
