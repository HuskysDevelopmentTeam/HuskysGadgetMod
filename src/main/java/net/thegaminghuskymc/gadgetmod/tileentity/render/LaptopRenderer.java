package net.thegaminghuskymc.gadgetmod.tileentity.render;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.thegaminghuskymc.gadgetmod.block.BlockLaptop;
import net.thegaminghuskymc.gadgetmod.init.GadgetBlocks;
import net.thegaminghuskymc.gadgetmod.init.GadgetItems;
import net.thegaminghuskymc.gadgetmod.tileentity.TileEntityLaptop;

import java.util.Objects;

public class LaptopRenderer extends TileEntitySpecialRenderer<TileEntityLaptop> {

    private Minecraft mc = Minecraft.getMinecraft();

    private EntityItem entityItem = new EntityItem(Minecraft.getMinecraft().world, 0D, 0D, 0D);

    @Override
    public void render(TileEntityLaptop te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
    	BlockPos pos = te.getPos();
        IBlockState state = te.getWorld().getBlockState(pos).withProperty(BlockLaptop.TYPE, BlockLaptop.Type.SCREEN);

        bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(x, y, z);

            if (te.isExternalDriveAttached()) {
                GlStateManager.pushMatrix();
                {
                    GlStateManager.translate(0.5, 0, 0.5);
                    GlStateManager.rotate(te.getBlockMetadata() * -100F - 90F, 0, 1, 0);
                    GlStateManager.translate(-0.5, 0, -0.5);
                    GlStateManager.translate(0.595, -0.2075, -0.005);
                    entityItem.hoverStart = 0.0F;
                    entityItem.setItem(new ItemStack(Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(Reference.MOD_ID, "flash_drive_" + te.getExternalDriveColor().getName()))), 1));
                    Minecraft.getMinecraft().getRenderManager().renderEntity(entityItem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F, false);
                    GlStateManager.translate(0.1, 0, 0);
                }
                GlStateManager.popMatrix();
            }

            GlStateManager.pushMatrix();
            {
                GlStateManager.translate(0.5, 0, 0.5);
                GlStateManager.rotate(te.getBlockMetadata() * -90F + 180F, 0, 1, 0);
                GlStateManager.translate(-0.5, 0, -0.5);
                GlStateManager.translate(0, 0.07, 0.12 + 1.0/16.0);
                GlStateManager.rotate(te.getScreenAngle(partialTicks), 1, 0, 0);
                GlStateManager.translate(0, -0.04, -1.0/16.0);

                GlStateManager.disableLighting();
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder buffer = tessellator.getBuffer();
                buffer.begin(7, DefaultVertexFormats.BLOCK);
                buffer.setTranslation(-pos.getX(), -pos.getY(), -pos.getZ());

                BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
                IBakedModel ibakedmodel = mc.getBlockRendererDispatcher().getBlockModelShapes().getModelForState(state);
                blockrendererdispatcher.getBlockModelRenderer().renderModel(getWorld(), ibakedmodel, state, pos, buffer, false);

                buffer.setTranslation(0.0D, 0.0D, 0.0D);
                tessellator.draw();
                GlStateManager.enableLighting();
            }
            GlStateManager.popMatrix();
        }
        GlStateManager.popMatrix();
    }
}