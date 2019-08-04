package io.github.vampirestudios.gadget.tileentity.render;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import io.github.vampirestudios.gadget.Reference;
import io.github.vampirestudios.gadget.tileentity.TileEntityServerRack;

import java.util.Objects;

public class ServerRackRenderer extends TileEntitySpecialRenderer<TileEntityServerRack> {

    private Minecraft mc = Minecraft.getMinecraft();

    private EntityItem entityItem = new EntityItem(Minecraft.getMinecraft().world, 0D, 0D, 0D);

    @Override
    public void render(TileEntityServerRack te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        BlockPos pos = te.getPos();
        IBlockState state = te.getWorld().getBlockState(pos);

        bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(x, y, z);

            if (te.hasServers()) {
                GlStateManager.pushMatrix();
                {
                    GlStateManager.translate(0.5, 0, 0.5);
                    GlStateManager.rotate(te.getBlockMetadata() * -100F - 90F, 0, 1, 0);
                    GlStateManager.translate(-0.5, 0, -0.5);
                    GlStateManager.translate(0.595, -0.2075, -0.005);
                    entityItem.hoverStart = 0.0F;
                    entityItem.setItem(new ItemStack(Objects.requireNonNull(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(Reference.MOD_ID, "sever_computer"))), 1));
                    Minecraft.getMinecraft().getRenderManager().renderEntity(entityItem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F, false);
                    GlStateManager.translate(0.1, 0, 0);
                }
                GlStateManager.popMatrix();
            }
        }
        GlStateManager.popMatrix();
    }
}
