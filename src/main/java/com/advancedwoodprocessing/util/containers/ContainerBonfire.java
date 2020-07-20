package com.advancedwoodprocessing.util.containers;

import com.advancedwoodprocessing.util.entities.TileEntityBonfire;
import com.advancedwoodprocessing.util.entities.TileEntityCounter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerBonfire extends Container {

    private final TileEntityBonfire tileEntityBonfire;

    public ContainerBonfire(InventoryPlayer inventoryPlayer, TileEntityBonfire tileEntityBonfire) {
        this.tileEntityBonfire = tileEntityBonfire;

        IItemHandler handler = tileEntityBonfire.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);


        //слоты : 0, 1, 2, 3 - для горящих досок, 4 - вход печки, 5 - выход, 6 - дрова
        //координаты взяты из справочника потолочникова
        this.addSlotToContainer(new SlotItemHandler(handler, 0, 26, 11));
        this.addSlotToContainer(new SlotItemHandler(handler, 1, 26, 59));
        this.addSlotToContainer(new SlotItemHandler(handler, 2, 26, 35));
        this.addSlotToContainer(new SlotItemHandler(handler, 3, 26, 36));
        this.addSlotToContainer(new SlotItemHandler(handler, 4, 50, 35));
        this.addSlotToContainer(new SlotItemHandler(handler, 5, 81, 36));
        this.addSlotToContainer(new SlotItemHandler(handler, 6, 7, 35));

        // добовляет инвентарь игрока
        for(int y = 0; y < 3; y++)
        {
            for(int x = 0; x < 9; x++)
            {
                this.addSlotToContainer(new Slot(inventoryPlayer, x + y*9 + 9, 8 + x*18, 84 + y*18));
            }
        }

        for(int x = 0; x < 9; x++)
        {
            this.addSlotToContainer(new Slot(inventoryPlayer, x, 8 + x * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return false;
    }
}
