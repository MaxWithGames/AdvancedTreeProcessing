package com.advancedwoodprocessing.events;

import java.util.ArrayList;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class DestroyHandler {
	static ArrayList<Vec3i> offsets;
	
	public DestroyHandler() {
		offsets = (ArrayList<Vec3i>) PlaceHandler.offsets.clone();
		offsets.add(new Vec3i(0, 0, 0));
	}
	
	@SubscribeEvent
	public void bonfire(BlockEvent.BreakEvent event) {		
		if (!event.getWorld().isRemote) {
			BlockPos pos = event.getPos();
			
			for (Vec3i offset : offsets) {
				IBlockState state = event.getWorld().getBlockState(pos.add(offset).add(0, 1, 0));
				if (PlaceHandler.forbiddenBlocks.contains(state.getBlock()))
					event.getWorld().destroyBlock(pos.add(offset).add(0, 1, 0), true);
			}
		}
	}
	
}
