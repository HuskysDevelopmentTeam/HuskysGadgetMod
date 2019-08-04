package io.github.vampirestudios.gadget.tileentity.render;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import io.github.vampirestudios.gadget.Reference;
import io.github.vampirestudios.gadget.api.print.IPrint;
import io.github.vampirestudios.gadget.api.print.PrintingManager;
import io.github.vampirestudios.gadget.block.BlockPrinter;
import io.github.vampirestudios.gadget.tileentity.TileEntityPrinter;

public class PrinterRenderer extends TileEntitySpecialRenderer<TileEntityPrinter> {

    private static final ModelPaper MODEL_PAPER = new ModelPaper();

    @Override
    public void render(TileEntityPrinter te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.pushMatrix();
        {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.translate(x, y, z);

            if (te.hasPaper()) {
                GlStateManager.pushMatrix();
                {
                    GlStateManager.translate(0.5, 0.5, 0.5);
                    IBlockState state = te.getWorld().getBlockState(te.getPos());
                    GlStateManager.rotate(state.getValue(BlockPrinter.FACING).getHorizontalIndex() * -90F, 0, 1, 0);
                    GlStateManager.rotate(22.5F, 1, 0, 0);
                    GlStateManager.translate(0, 0.1, 0.35);
                    GlStateManager.translate(-11 * 0.015625, -13 * 0.015625, -0.5 * 0.015625);
                    MODEL_PAPER.render(null, 0F, 0F, 0F, 0F, 0F, 0.3F);
                }
                GlStateManager.popMatrix();
            }

            GlStateManager.pushMatrix();
            {
                if (te.isLoading()) {
                    GlStateManager.translate(0.5, 0.5, 0.5);
                    IBlockState state1 = te.getWorld().getBlockState(te.getPos());
                    GlStateManager.rotate(state1.getValue(BlockPrinter.FACING).getHorizontalIndex() * -90F, 0, 1, 0);
                    GlStateManager.rotate(22.5F, 1, 0, 0);
                    double progress = Math.max(-0.4, -0.4 + (0.4 * ((double) (te.getRemainingPrintTime() - 10) / 20)));
                    GlStateManager.translate(0, progress, 0.36875);
                    GlStateManager.translate(-11 * 0.015625, -13 * 0.015625, -0.5 * 0.015625);
                    MODEL_PAPER.render(null, 0F, 0F, 0F, 0F, 0F, 0.015625F);
                } else if (te.isPrinting()) {
                    GlStateManager.translate(0.5, 0.078125, 0.5);
                    IBlockState state1 = te.getWorld().getBlockState(te.getPos());
                    GlStateManager.rotate(state1.getValue(BlockPrinter.FACING).getHorizontalIndex() * -90F, 0, 1, 0);
                    GlStateManager.rotate(90F, 1, 0, 0);
                    double progress = -0.35 + (0.50 * ((double) (te.getRemainingPrintTime() - 20) / te.getTotalPrintTime()));
                    GlStateManager.translate(0, progress, 0);
                    GlStateManager.translate(-11 * 0.015625, -13 * 0.015625, -0.5 * 0.015625);
                    MODEL_PAPER.render(null, 0F, 0F, 0F, 0F, 0F, 0.015625F);

                    GlStateManager.translate(0.3225, 0.085, -0.001);
                    GlStateManager.rotate(180F, 0, 1, 0);
                    GlStateManager.scale(0.3, 0.3, 0.3);

                    IPrint print = te.getPrint();
                    if (print != null) {
                        IPrint.Renderer renderer = PrintingManager.getRenderer(print);
                        renderer.render(print.toTag());
                    }
                }
            }
            GlStateManager.popMatrix();
        }
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(0, -0.5, 0);
            super.render(te, x, y, z, partialTicks, destroyStage, alpha);
        }
        GlStateManager.popMatrix();
    }

    public static class ModelPaper extends ModelBase {
        public static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/model/paper.png");

        private ModelRenderer box = new ModelRenderer(this, 0, 0).addBox(0, 0, 0, 22, 30, 1);

        @Override
        public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);
            box.render(scale);
        }
    }
}
