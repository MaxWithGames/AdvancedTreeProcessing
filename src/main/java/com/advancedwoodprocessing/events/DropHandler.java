package com.advancedwoodprocessing.events;

import com.advancedwoodprocessing.blocks.WoodProcessor;
import com.advancedwoodprocessing.init.ModBlocks;
import com.advancedwoodprocessing.items.tools.ItemScraper;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class DropHandler {
	@SubscribeEvent
	public void scraper(HarvestDropsEvent event)
	{
		if (event.getWorld().isRemote || event.getHarvester() == null || event.isSilkTouching())
			return;

		Item held = event.getHarvester().getHeldItemMainhand().getItem(); 
		//System.out.println(held);
		
		Block block = event.getState().getBlock();
		//System.out.println(block);
		
		if ((block == Blocks.LOG || block == Blocks.LOG2) && (held instanceof ItemScraper)) {
			event.getDrops().clear();
			//event.setDropChance(1.0F);
			//event.getDrops().add(new ItemStack(ModBlocks.WOODPROCESSOR));
			
			event.getWorld().setBlockState(event.getPos(), ModBlocks.WOODPROCESSOR.getDefaultState());
		}
	}
	
	@SubscribeEvent
	public void axe(HarvestDropsEvent event)
	{
		if (event.getWorld().isRemote || event.getHarvester() == null || event.isSilkTouching())
			return;

		Item held = event.getHarvester().getHeldItemMainhand().getItem(); 
		//System.out.println(held);
		
		Block block = event.getState().getBlock();
		//System.out.println(block);
		
		if ((block == Blocks.LOG || block == Blocks.LOG2) && (held instanceof ItemAxe)) {
			event.getDrops().clear();
			event.setDropChance(1.0F);
			event.getDrops().add(new ItemStack(ModBlocks.SMALL_LOG, 4));
		}
	}
}
