package com.advancedwoodprocessing.init;

import java.util.ArrayList;
import java.util.List;

import com.advancedwoodprocessing.blocks.BlockBase;
import com.advancedwoodprocessing.blocks.BlockPlank;
import com.advancedwoodprocessing.blocks.SmallLog;
import com.advancedwoodprocessing.blocks.WoodProcessor;
import com.advancedwoodprocessing.items.ItemBase;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ModBlocks {
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	public static final Block WOODPROCESSOR = new WoodProcessor("woodprocessor", Material.WOOD, ModItems.AWT_TAB);
	public static final Block SMALL_LOG = new SmallLog("small_log", Material.WOOD, ModItems.AWT_TAB);
	public static final Block BLOCK_PLANK = new BlockPlank("block_plank", Material.WOOD, ModItems.AWT_TAB);
	public static final Block BURNING_BLOCK_PLANK = new BlockPlank("burning_block_plank", Material.WOOD, ModItems.AWT_TAB);
}
