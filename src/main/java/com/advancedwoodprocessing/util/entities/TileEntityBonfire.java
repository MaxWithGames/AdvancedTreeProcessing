package com.advancedwoodprocessing.util.entities;

import com.advancedwoodprocessing.init.ModBlocks;
import com.advancedwoodprocessing.init.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TileEntityBonfire extends TileEntity implements ITickable {

    private ItemStackHandler handler = new ItemStackHandler(7);
    private int burningProgress = -1;
    private int autoBurning = 1;

    public TileEntityBonfire(){
        super();
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return (T) this.handler;
        return super.getCapability(capability, facing);
    }

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
            burningProgress = 0;
            System.out.println(burningProgress);
        }

        sendUpdates();
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

    public boolean isUsableByPlayer(EntityPlayer player){
        return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
    }

    public void setField(int id, int value){
        switch(id)
        {
            case 0:
                this.burningProgress = value;
                break;
            case 1:
                this.autoBurning = value;
                break;
        }
    }

    public int getField(int id){
        switch(id)
        {
            case 0:
                return this.burningProgress;
            case 1:
                return this.autoBurning;
            default:
                return 0;
        }
    }
}
