package net.thegaminghuskymc.gadgetmod.tileentity.render;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.thegaminghuskymc.gadgetmod.tileentity.TileEntity3DPrinter;

public class ThreedePrinterRenderer extends TileEntitySpecialRenderer<TileEntity3DPrinter> {

    @Override
    public void render(TileEntity3DPrinter te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);
    }

}
