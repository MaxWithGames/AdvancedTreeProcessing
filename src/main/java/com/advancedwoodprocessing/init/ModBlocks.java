package com.advancedwoodprocessing.init;

import java.util.ArrayList;
import java.util.List;

import com.advancedwoodprocessing.blocks.*;
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
	public static final Block BONFIRE_BASE = new BonfireBase("bonfire_base", Material.WOOD, ModItems.AWT_TAB);
	public static final Block BONFIRE = new Bonfire("bonfire", Material.WOOD, ModItems.AWT_TAB);
}
