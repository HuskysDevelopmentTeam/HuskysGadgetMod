package io.github.vampirestudios.gadget.tileentity.render;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import io.github.vampirestudios.gadget.tileentity.TileEntity3DPrinter;

public class ThreedePrinterRenderer extends TileEntitySpecialRenderer<TileEntity3DPrinter> {

    @Override
    public void render(TileEntity3DPrinter te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);
    }

}
