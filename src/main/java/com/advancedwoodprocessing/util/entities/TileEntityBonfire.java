package com.advancedwoodprocessing.util.entities;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityBonfire extends TileEntity implements ITickable {

    private ItemStackHandler handler = new ItemStackHandler(7);
    private String customName;
    private ItemStack smelting = ItemStack.EMPTY;

    @Override
    public void update() {

    }
}
