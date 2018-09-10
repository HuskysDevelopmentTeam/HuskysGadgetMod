package net.thegaminghuskymc.gadgetmod.block;

import net.hdt.huskylib2.interf.IModBlock;
import net.hdt.huskylib2.item.ItemModBlock;
import net.hdt.huskylib2.util.ProxyRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class BlockMod extends Block implements IModBlock {

	private final String[] variants;
	private final String bareName;
	private CreativeTabs creativeTabs;

	public BlockMod(String name, Material materialIn, String... variants) {
		super(materialIn);

		if(variants.length == 0)
			variants = new String[] { name };

		bareName = name;
		this.variants = variants;

		if(registerInConstruction())
			setTranslationKey(name);
		setCreativeTab(getCreativeTabs());
	}

	@Override
	public Block setTranslationKey(String name) {
		super.setTranslationKey(getPrefix() + name);
		setRegistryName(getPrefix(), name);
		ProxyRegistry.register(this);
		ProxyRegistry.register(createItemBlock(new ResourceLocation(getPrefix(), name)));
		return this;
	}

	public void setCreativeTabs(CreativeTabs creativeTabs) {
		this.creativeTabs = creativeTabs;
	}

	public CreativeTabs getCreativeTabs() {
		if(creativeTabs == null) {
			return CreativeTabs.SEARCH;
		} else {
			return creativeTabs;
		}
	}
	
	public ItemBlock createItemBlock(ResourceLocation res) {
		return new ItemModBlock(this, res);
	}
	
	public boolean registerInConstruction() {
		return true;
	}
	
	@Override
	public String getBareName() {
		return bareName;
	}

	@Override
	public String[] getVariants() {
		return variants;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemMeshDefinition getCustomMeshDefinition() {
		return null;
	}

	@Override
	public EnumRarity getBlockRarity(ItemStack stack) {
		return EnumRarity.COMMON;
	}

	@Override
	public IProperty[] getIgnoredProperties() {
		return new IProperty[0];
	}

	@Override
	public IProperty getVariantProp() {
		return null;
	}

	@Override
	public Class getVariantEnum() {
		return null;
	}

}
