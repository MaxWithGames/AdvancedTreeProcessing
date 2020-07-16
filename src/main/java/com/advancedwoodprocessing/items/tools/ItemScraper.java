package com.advancedwoodprocessing.items.tools;

import java.util.Set;

import com.advancedwoodprocessing.Main;
import com.advancedwoodprocessing.init.ModItems;
import com.advancedwoodprocessing.util.IHasModel;
import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTool;

public class ItemScraper extends ItemTool implements IHasModel {
	private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.LOG, Blocks.LOG2);
	
	public ItemScraper(String name, Item.ToolMaterial material, CreativeTabs tab) {
		super(1.0F, -2.8F, material, EFFECTIVE_ON);
		
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(tab);
		
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public void registerModel() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}

}
