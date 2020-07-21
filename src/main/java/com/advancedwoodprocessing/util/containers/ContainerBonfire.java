package com.advancedwoodprocessing.util.containers;

import com.advancedwoodprocessing.util.entities.TileEntityBonfire;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerBonfire extends Container {

    private final TileEntityBonfire tileentity;
    private int burningProgress = -1;
    private int autoBurning;


    public ContainerBonfire(InventoryPlayer player, TileEntityBonfire tileEntityBonfire){

        this.tileentity = tileEntityBonfire;
        IItemHandler handler = tileEntityBonfire.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        //slots : 0, 1, 2, 3 - for burning plank, 4 - in, 5 - out, 6 - plank
        this.addSlotToContainer(new SlotItemHandler(handler, 0, 40, 43));
        this.addSlotToContainer(new SlotItemHandler(handler, 1, 66, 43));
        this.addSlotToContainer(new SlotItemHandler(handler, 2, 92, 43));
        this.addSlotToContainer(new SlotItemHandler(handler, 3, 118, 43));
        this.addSlotToContainer(new SlotItemHandler(handler, 4, 53, 12));
        this.addSlotToContainer(new SlotItemHandler(handler, 5, 133, 12));
        this.addSlotToContainer(new SlotItemHandler(handler, 6, 13, 12));

        // player inventory
        for(int y = 0; y < 3; y++){
            for(int x = 0; x < 9; x++)
            {
                this.addSlotToContainer(new Slot(player, x + y*9 + 9, 8 + x*18, 84 + y*18));
            }
        }

        for(int x = 0; x < 9; x++){
            this.addSlotToContainer(new Slot(player, x, 8 + x * 18, 142));
        }
    }

    @Override
    public void detectAndSendChanges(){
        super.detectAndSendChanges();

        for(int i = 0; i < this.listeners.size(); ++i)
        {
            IContainerListener listener = (IContainerListener)this.listeners.get(i);

            if(this.burningProgress != this.tileentity.getField(0)) listener.sendWindowProperty(this, 0, this.tileentity.getField(0));
            if(this.autoBurning != this.tileentity.getField(1)) listener.sendWindowProperty(this, 1, this.tileentity.getField(1));

        }

        this.burningProgress = this.tileentity.getField(0);
        this.autoBurning = this.tileentity.getField(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data){
        this.tileentity.setField(id, data);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return this.tileentity.isUsableByPlayer(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()){
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index <= 6){
                System.out.println("asdasd");
                if(index == 5) {
                    if (!this.mergeItemStack(itemstack1, 7, 43, true)) {
                        return ItemStack.EMPTY;
                    }

                    slot.onSlotChange(itemstack1, itemstack);
                }else{
                    if (!this.mergeItemStack(itemstack1, 7, 43, false)){
                        return ItemStack.EMPTY;
                    }
                }
            }

            if (itemstack1.isEmpty()){
                slot.putStack(ItemStack.EMPTY);
            }else{
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()){
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }

}
