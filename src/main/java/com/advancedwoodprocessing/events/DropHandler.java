package com.advancedwoodprocessing.events;

import com.advancedwoodprocessing.blocks.WoodProcessor;
import com.advancedwoodprocessing.init.ModBlocks;
import com.advancedwoodprocessing.items.tools.ItemKnife;
import com.advancedwoodprocessing.items.tools.ItemScraper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockLog;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;

@EventBusSubscriber
public class DropHandler {
	public static NonNullList<ItemStack> Woods = null;
	
	@SubscribeEvent
	public void scraper(HarvestDropsEvent event)
	{
		for (ItemStack item : Woods) {
			System.out.println(item.getDisplayName());
			System.out.println(item.getItem().getUnlocalizedName());
			System.out.println(item.getItem().getRegistryName());
			for (int i = 0; i < 10; i++)
				System.out.println("dmg " + i + ":" + item.getItem().getMetadata(i));
			System.out.println(item.getMetadata());
		}
		
		if (event.getWorld().isRemote || event.getHarvester() == null || event.isSilkTouching())
			return;

		Item held = event.getHarvester().getHeldItemMainhand().getItem(); 	
		Block block = event.getState().getBlock();
		
		if ((block == Blocks.LOG || block == Blocks.LOG2) && (held instanceof ItemScraper)) {
			event.getDrops().clear();
			//event.setDropChance(1.0F);
			//event.getDrops().add(new ItemStack(ModBlocks.WOODPROCESSOR));
			
			event.getWorld().setBlockState(event.getPos(), ModBlocks.WOODPROCESSOR.getDefaultState());
		}

		if ((block == Blocks.LOG || block == Blocks.LOG2) && (held instanceof ItemKnife)) {
			event.getDrops().clear();
			//event.setDropChance(1.0F);
			//event.getDrops().add(new ItemStack(ModBlocks.WOODPROCESSOR));

			event.getWorld().setBlockState(event.getPos(), Blocks.CRAFTING_TABLE.getDefaultState());
		}
	}
	
	private boolean checkBlockOreDict(ItemStack blockItem, NonNullList<ItemStack> oreDict) {
		for(ItemStack item : oreDict) {
			if(OreDictionary.itemMatches(new ItemStack(item.getItem()), blockItem, false))
				return true;
		}
		return false;
	}
	
	@SubscribeEvent
	public void axe(HarvestDropsEvent event)
	{
		if (event.getWorld().isRemote || event.getHarvester() == null || event.isSilkTouching())
			return;

		Item held = event.getHarvester().getHeldItemMainhand().getItem(); 
		Block block = event.getState().getBlock();
		
		if (checkBlockOreDict(new ItemStack(Item.getItemFromBlock(block)), Woods) && (held instanceof ItemAxe)) {
			event.getDrops().clear();
			event.setDropChance(1.0F);
			event.getDrops().add(new ItemStack(ModBlocks.SMALL_LOG, 8));
		}
	}
}
