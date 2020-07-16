package com.advancedwoodprocessing.init;

import java.util.ArrayList;
import java.util.List;

import com.advancedwoodprocessing.blocks.BlockBase;
import com.advancedwoodprocessing.blocks.SmallLog;
import com.advancedwoodprocessing.blocks.WoodProcessor;
import com.advancedwoodprocessing.items.ItemBase;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ModBlocks {
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	public static final Block WOODPROCESSOR = new WoodProcessor("woodprocessor", Material.WOOD, CreativeTabs.BUILDING_BLOCKS);
	public static final Block SMALL_LOG = new SmallLog("small_log", Material.WOOD, CreativeTabs.BUILDING_BLOCKS);
}
