package com.advancedwoodprocessing.init;

import java.util.ArrayList;
import java.util.List;

import com.advancedwoodprocessing.blocks.SmallLog;
import com.advancedwoodprocessing.events.DropHandler;
import com.advancedwoodprocessing.items.BurningPlank;
import com.advancedwoodprocessing.items.ItemBase;
import com.advancedwoodprocessing.items.tools.ItemKnife;
import com.advancedwoodprocessing.items.tools.ItemSaw;
import com.advancedwoodprocessing.items.tools.ItemScraper;
import com.advancedwoodprocessing.items.tools.ItemToolAxe;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemAxe;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

public class ModItems {
	static final CreativeTabs AWT_TAB = (new CreativeTabs("awp") {

		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(ModItems.FLINT_BONE_AXE);
		}
		
	});
	
	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	public static final ToolMaterial MATERAIL_SCRAPER = EnumHelper.addToolMaterial("material_scraper", 0, 1, 2.0f, 0.0f, 0);
	public static final ItemTool SCRAPER = new ItemScraper("scraper", MATERAIL_SCRAPER, AWT_TAB);
	
	public static final ToolMaterial MATERAIL_FLINT_AXE = EnumHelper.addToolMaterial("material_flint_axe", 0, 15, 2.0f, 0.0f, 10);
	public static final ItemAxe FLINT_AXE = new ItemToolAxe("flint_axe", MATERAIL_FLINT_AXE, AWT_TAB);
	
	public static final ToolMaterial MATERAIL_FLINT_BONE_AXE = EnumHelper.addToolMaterial("material_flint_bone_axe", 0, 29, 2.0f, 0.0f, 10);
	public static final ItemAxe FLINT_BONE_AXE = new ItemToolAxe("flint_bone_axe", MATERAIL_FLINT_BONE_AXE, AWT_TAB);
	
	public static final ToolMaterial MATERIAL_KNIFE = EnumHelper.addToolMaterial("material_knife", 0, 5, 2.0f, 0.0f, 0);
	public static final ItemTool KNIFE = new ItemKnife("knife", MATERIAL_KNIFE, AWT_TAB);

	public static final ToolMaterial MATERIAL_FLINT_SAW = EnumHelper.addToolMaterial("material_flint_saw",0,32,2.0f,0.0f, 10);
	public static final ItemAxe FLINT_SAW = new ItemSaw("flint_saw",MATERIAL_FLINT_SAW, AWT_TAB, 3.0f, -3.2f);

	public static final ToolMaterial MATERIAL_STONE_SAW = EnumHelper.addToolMaterial("material_stone_saw",0,64,4.0f,0.0f, 10);
	public static final ItemAxe STONE_SAW = new ItemSaw("stone_saw",MATERIAL_STONE_SAW, AWT_TAB, 4.0f, -3.0f);

	public static final ToolMaterial MATERIAL_IRON_SAW = EnumHelper.addToolMaterial("material_iron_saw",0,128,6.0f,0.0f, 10);
	public static final ItemAxe IRON_SAW = new ItemSaw("iron_saw",MATERIAL_IRON_SAW, AWT_TAB, 5.0f, -2.8f);

	public static final ToolMaterial MATERIAL_GOLDEN_SAW = EnumHelper.addToolMaterial("material_golden_saw",0,256,6.0f,0.0f, 20);
	public static final ItemAxe GOLDEN_SAW = new ItemSaw("golden_saw",MATERIAL_GOLDEN_SAW, AWT_TAB, 5.0f, -2.8f);

	public static final ToolMaterial MATERIAL_DIAMOND_SAW = EnumHelper.addToolMaterial("material_diamond_saw",0,512,9.0f,0.0f, 10);
	public static final ItemAxe DIAMOND_SAW = new ItemSaw("diamond_saw",MATERIAL_DIAMOND_SAW, AWT_TAB, 6.0f, -2.0f);

	public static final Item FLINT_AXE_HEAD = new ItemBase("flint_axe_head", AWT_TAB);
	//public static final Item PLANK = new ItemBase("plank", AWT_TAB);
	public static final List<Item> PLANKS = new ArrayList<Item>();
	
	static {
		for (String name : DropHandler.woodNames)
			PLANKS.add(new ItemBase(name.replaceAll("\\.", "_") + "_plank", ModItems.AWT_TAB));
	}

	public static final Item SILICON_DIE = new ItemBase("silicon_die", AWT_TAB);
	public static final Item HARDEN_FLINT = new ItemBase("harden_flint", AWT_TAB);
	public static final Item FLINT_AND_CLAY = new ItemBase("flint_and_clay", AWT_TAB);
	public static final Item STRING_FOR_BOW = new ItemBase("string_for_bow", AWT_TAB);
	
	public static final Item COAL_DUST = new ItemBase("awp_coal_dust", AWT_TAB);
	public static final Item BOW_AND_STICK = new ItemBase("bow_and_stick", AWT_TAB).setMaxStackSize(1);
	public static final Item PLANK_BURNING = new BurningPlank("plank_burning", AWT_TAB);
}
