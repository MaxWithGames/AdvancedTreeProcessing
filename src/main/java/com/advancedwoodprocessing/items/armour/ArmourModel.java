package com.advancedwoodprocessing.items.armour;

import com.advancedwoodprocessing.util.IHasModel;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;

public class ArmourModel extends ItemArmor implements IHasModel {
    public ArmourModel(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
        super(materialIn, renderIndexIn, equipmentSlotIn);
    }

    @Override
    public void registerModel() {

    }
}
