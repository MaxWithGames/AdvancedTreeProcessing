package com.advancedwoodprocessing.init;

import java.util.ArrayList;
import java.util.List;

import com.advancedwoodprocessing.items.ItemBase;
import com.advancedwoodprocessing.items.tools.ItemScraper;
import com.advancedwoodprocessing.items.tools.ItemToolAxe;

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
	
	public static final Item FLINT_AXE_HEAD = new ItemBase("flint_axe_head", AWT_TAB);
	public static final Item PLANK = new ItemBase("plank", AWT_TAB);
	public static final Item HARDEN_FLINT = new ItemBase("harden_flint", AWT_TAB);
	public static final Item FLINT_AND_CLAY = new ItemBase("flint_and_clay", AWT_TAB);
}
