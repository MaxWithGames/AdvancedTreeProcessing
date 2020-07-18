package com.advancedwoodprocessing.items.tools;

import com.advancedwoodprocessing.Main;
import com.advancedwoodprocessing.init.ModItems;
import com.advancedwoodprocessing.util.IHasModel;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTool;

import java.util.Set;

public class ItemKnife extends ItemTool implements IHasModel {

    public ItemKnife(String name, Item.ToolMaterial material, CreativeTabs tab) {
        super(1.0F, -2.8F, material, null);

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
