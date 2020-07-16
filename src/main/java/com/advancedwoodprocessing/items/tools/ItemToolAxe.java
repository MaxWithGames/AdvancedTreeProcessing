package com.advancedwoodprocessing.items.tools;

import com.advancedwoodprocessing.Main;
import com.advancedwoodprocessing.init.ModItems;
import com.advancedwoodprocessing.util.IHasModel;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;

public class ItemToolAxe extends ItemAxe implements IHasModel{
	public ItemToolAxe(String name, Item.ToolMaterial material, CreativeTabs tab) {
		super(material, 6.0f, -3.2f);
		
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
