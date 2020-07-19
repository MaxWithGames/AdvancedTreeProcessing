package com.advancedwoodprocessing.events;

import com.advancedwoodprocessing.init.ModBlocks;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class PlaceHandler {
	
	@SubscribeEvent
	public void bonfire_base(BlockEvent.PlaceEvent event) {
		if (!event.getWorld().isRemote) {
			boolean cancel = false;
			
			BlockPos pos = event.getPos();
			
			if (event.getPlacedBlock().getBlock() == ModBlocks.BONFIRE_BASE) {
				for (int i = -1; i <= 1; i++)
					for (int j = -1; j <= 1; j++)
						if ((i != 0) || (j != 0)) {
							BlockPos checkedPos = pos.add(i, 0, j);
							if (event.getWorld().getBlockState(checkedPos).getBlock() != Blocks.AIR)
								cancel = true;
						}
			} else {
				for (int i = -1; i <= 1; i++)
					for (int j = -1; j <= 1; j++)
						if ((i != 0) || (j != 0)) {
							BlockPos checkedPos = pos.add(i, 0, j);
							if (event.getWorld().getBlockState(checkedPos).getBlock() == ModBlocks.BONFIRE_BASE)
								cancel = true;
						}
			}
			
			event.setCanceled(cancel);
		}
	}
}
