package com.advancedwoodprocessing.items.tools;

import com.advancedwoodprocessing.Main;
import com.advancedwoodprocessing.init.ModItems;
import com.advancedwoodprocessing.util.IHasModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemAxe;

public class ItemSaw extends ItemAxe implements IHasModel {

    public ItemSaw(String name, ToolMaterial material, CreativeTabs tab, float damage, float speed) {
        super(material, damage, speed);

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
