package com.advancedwoodprocessing.events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.advancedwoodprocessing.blocks.WoodProcessor;
import com.advancedwoodprocessing.init.ModBlocks;
import com.advancedwoodprocessing.items.tools.ItemKnife;
import com.advancedwoodprocessing.items.tools.ItemSaw;
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
	
	public static final ArrayList<String> woodNames = new ArrayList<String>(Arrays.asList(
		"tile.log.oak",
		"tile.log.big_oak",
		"tile.log.birch",
		"tile.log.acacia",
		"tile.log.jungle",
		"tile.log.spruce",
		"tile.log_4.dead_log",
		"tile.log_3.mahogany_log",
		"tile.log_3.eucalyptus_log",
		"tile.log_3.jacaranda_log",
		"tile.log_3.ebony_log",
		"tile.log_2.redwood_log",
		"tile.log_2.pine_log",
		"tile.log_2.willow_log",
		"tile.log_2.hellbark_log",
		"tile.log_1.palm_log",
		"tile.log_1.mangrove_log",
		"tile.log_1.ethereal_log",
		"tile.log_1.magic_log",
		"tile.log_0.umbran_log",
		"tile.log_0.cherry_log",
		"tile.log_0.sacred_oak_log",
		"tile.log_0.fir_log"
	));
	
	public static int getWoodType(String name) {
		return woodNames.indexOf(name);
	}
	
	@SubscribeEvent
	public void scraper(HarvestDropsEvent event)
	{
		/*for (ItemStack item : Woods) {
			System.out.println(item.getDisplayName());
			System.out.println(item.getItem().getUnlocalizedName());
			System.out.println(item.getItem().getRegistryName());
			for (int i = 0; i < 10; i++)
				System.out.println("dmg " + i + ":" + item.getItem().getMetadata(i));
			System.out.println(item.getMetadata());
		}*/
		
		if (event.getWorld().isRemote || event.getHarvester() == null || event.isSilkTouching())
			return;
		
		//System.out.println(event.getDrops().get(0).getUnlocalizedName());
		
		Item held = event.getHarvester().getHeldItemMainhand().getItem(); 	
		Block block = event.getState().getBlock();
		
		if ((checkBlockOreDict(new ItemStack(Item.getItemFromBlock(block)), Woods)) && (held instanceof ItemScraper)) {
			event.getDrops().clear();
			//event.setDropChance(1.0F);
			//event.getDrops().add(new ItemStack(ModBlocks.WOODPROCESSOR));
			
			event.getWorld().setBlockState(event.getPos(), ModBlocks.WOODPROCESSOR.getDefaultState());
		}

		if ((checkBlockOreDict(new ItemStack(Item.getItemFromBlock(block)), Woods)) && (held instanceof ItemKnife)) {
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
	public void saw(HarvestDropsEvent event)
	{
		if (event.getWorld().isRemote || event.getHarvester() == null || event.isSilkTouching())
			return;

		Item held = event.getHarvester().getHeldItemMainhand().getItem(); 
		Block block = event.getState().getBlock();
		
		
		if (checkBlockOreDict(new ItemStack(Item.getItemFromBlock(block)), Woods) && (held instanceof ItemSaw)) {
			event.setDropChance(1.0F);
			
			int woodNumber = 0;
			if (woodNames.contains(event.getDrops().get(0).getUnlocalizedName())) {
					woodNumber = woodNames.indexOf(event.getDrops().get(0).getUnlocalizedName());
					System.out.println(woodNumber);
			}
			
			event.getDrops().clear();
			event.getDrops().add(new ItemStack(ModBlocks.SMALL_LOGS.get(woodNumber), 8));
		}
	}
}
