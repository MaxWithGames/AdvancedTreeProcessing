package com.advancedwoodprocessing.init;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.advancedwoodprocessing.blocks.*;
import com.advancedwoodprocessing.items.ItemBase;
import com.advancedwoodprocessing.events.DropHandler;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.math.Vec3i;

public class ModBlocks {
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	public static final List<Block> SMALL_LOGS = new ArrayList<Block>();
	
	static {
		for (String name : DropHandler.woodNames)
			new SmallLog(name.replaceAll("\\.", "_"), Material.WOOD, ModItems.AWT_TAB);
	}
	
	//for (int i = 0; i < woodNames.size(); i++)
	//	System.out.println(i);
	//	SMALL_LOGS.add(new SmallLog(name.replaceAll(".", "_"), Material.WOOD, ModItems.AWT_TAB));
	
	public static final Block WOODPROCESSOR = new WoodProcessor("woodprocessor", Material.WOOD, ModItems.AWT_TAB);
	public static final Block BLOCK_PLANK = new BlockPlank("block_plank", Material.WOOD, ModItems.AWT_TAB);
	public static final Block BONFIRE_BASE = new BonfireBase("bonfire_base", Material.WOOD, ModItems.AWT_TAB);
	public static final Block BONFIRE = new Bonfire("bonfire", Material.WOOD, ModItems.AWT_TAB);
	public static final Block WPKKING_BONFIRE = new WorkingBonfire("working_bonfire", Material.WOOD, ModItems.AWT_TAB);
}
