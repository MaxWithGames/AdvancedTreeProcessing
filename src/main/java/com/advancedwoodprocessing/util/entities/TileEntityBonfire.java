package com.advancedwoodprocessing.util.entities;

import com.advancedwoodprocessing.init.ModBlocks;
import com.advancedwoodprocessing.init.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TileEntityBonfire extends TileEntity implements ITickable {

    private ItemStackHandler handler = new ItemStackHandler(7);
    private int burningProgress = -1;
    private int autoBurning;


    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("burningProgress", (short)this.burningProgress);
        compound.setInteger("autoBurning", (short)this.autoBurning);
        compound.setTag("Inventory", this.handler.serializeNBT());
        return compound;

    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.handler.deserializeNBT(compound.getCompoundTag("Inventory"));
        this.burningProgress = compound.getInteger("burningProgress");
        this.autoBurning = compound.getInteger("autoBurning");

    }

    @Override
    public void update() {
        ItemStack[] burning_plank = new ItemStack[] {handler.getStackInSlot(0), handler.getStackInSlot(1),
                handler.getStackInSlot(2), handler.getStackInSlot(3)};
        ItemStack plank = this.handler.getStackInSlot(6);
        ItemStack in = this.handler.getStackInSlot(4);
        ItemStack out = this.handler.getStackInSlot(5);

        if(!in.isEmpty() && (burningProgress == -1)){
            burningProgress = 1200;
        }


    }

    @Override
    @Nullable
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(this.pos, 3, this.getUpdateTag());
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return this.writeToNBT(new NBTTagCompound());
    }

    @Override
    public void onDataPacket(NetworkManager networkManager, SPacketUpdateTileEntity packet) {
        super.onDataPacket(networkManager, packet);
        this.handleUpdateTag(packet.getNbtCompound());
    }

    private void sendUpdates() {
        this.getWorld().markBlockRangeForRenderUpdate(pos, pos);
        this.getWorld().notifyBlockUpdate(pos, this.getWorld().getBlockState(pos), this.getWorld().getBlockState(pos), 3);
        this.getWorld().scheduleBlockUpdate(pos,this.getBlockType(),0,0);
        markDirty();
    }
}
