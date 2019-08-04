package io.github.vampirestudios.gadget.tileentity;

import net.hdt.huskylib2.block.tile.TileMod;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import io.github.vampirestudios.gadget.entity.EntitySeat;

import java.util.List;

public class TileEntityOfficeChair extends TileMod {

    @SideOnly(Side.CLIENT)
    public float getRotation() {
        List<EntitySeat> seats = world.getEntitiesWithinAABB(EntitySeat.class, new AxisAlignedBB(pos));
        if (!seats.isEmpty()) {
            EntitySeat seat = seats.get(0);
            if (seat.getControllingPassenger() != null) {
                if (seat.getControllingPassenger() instanceof EntityLivingBase) {
                    EntityLivingBase living = (EntityLivingBase) seat.getControllingPassenger();
                    living.renderYawOffset = living.rotationYaw;
                    living.prevRenderYawOffset = living.rotationYaw;
                    return living.rotationYaw;
                }
                return seat.getControllingPassenger().rotationYaw;
            }
        }
        return getBlockMetadata() * 90F;
    }
}