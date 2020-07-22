package com.advancedwoodprocessing.events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.advancedwoodprocessing.init.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class PlaceHandler {
	public static final ArrayList<Block> forbiddenBlocks = new ArrayList<Block>(Arrays.asList(
		ModBlocks.BONFIRE,
		ModBlocks.BONFIRE_BASE,
		ModBlocks.WORKING_BONFIRE
	));
	
	public static final ArrayList<Vec3i> offsets = new ArrayList<Vec3i>(Arrays.asList(
		new Vec3i(-1, 0, -1), new Vec3i(-1, 0, 0), new Vec3i(-1, 0, 1), new Vec3i(0, 0, -1),
		new Vec3i(0, 0, 1), new Vec3i(1, 0, -1), new Vec3i(1, 0, 0), new Vec3i(1, 0, 1)
	));
	
	@SubscribeEvent
	public void bonfire(BlockEvent.PlaceEvent event) {
		if (!event.getWorld().isRemote) {
			boolean cancel = false;
			
			BlockPos pos = event.getPos();
			Block  block = event.getPlacedBlock().getBlock();
			
			if (forbiddenBlocks.contains(block)) {
				for (Vec3i offset : offsets) {
					if (event.getWorld().getBlockState(pos.add(offset)).getBlock() != Blocks.AIR)
						cancel = true;
					if (!event.getWorld().getBlockState(pos.add(offset).add(0, -1, 0)).isFullBlock())
						cancel = true;
				}
			} else {
				for (Vec3i offset : offsets)
					if (forbiddenBlocks.contains(event.getWorld().getBlockState(pos.add(offset)).getBlock()))
						cancel = true;
			}
			
			event.setCanceled(cancel);
			
			//fucking bullshit but fucking works
			if (cancel) {
				ItemStack stack = event.getItemInHand();
				
				event.getPlayer().replaceItemInInventory(event.getPlayer().inventory.currentItem, new ItemStack(Blocks.AIR));
				event.getPlayer().inventoryContainer.detectAndSendChanges();
				
				event.getPlayer().replaceItemInInventory(event.getPlayer().inventory.currentItem, stack);
				event.getPlayer().inventoryContainer.detectAndSendChanges();
			}
		}
	}
}
