package io.github.vampirestudios.gadget.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import io.github.vampirestudios.gadget.entity.EntitySeat;

import java.util.List;

public class SeatUtil {
    public static void createSeatAndSit(World worldIn, BlockPos pos, EntityPlayer playerIn, double yOffset) {
        List<EntitySeat> seats = worldIn.getEntitiesWithinAABB(EntitySeat.class, new AxisAlignedBB(pos));
        if (!seats.isEmpty()) {
            EntitySeat seat = seats.get(0);
            if (seat.getRidingEntity() == null) {
                playerIn.startRiding(seat);
            }
        } else {
            EntitySeat seat = new EntitySeat(worldIn, pos, yOffset);
            worldIn.spawnEntity(seat);
            playerIn.startRiding(seat);
        }
    }
}