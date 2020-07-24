package com.advancedwoodprocessing.util.entities;

import com.advancedwoodprocessing.init.ModBlocks;
import com.advancedwoodprocessing.init.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
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
import java.util.ArrayList;

public class TileEntityBonfire extends TileEntity implements ITickable {

    public ItemStackHandler handler = new ItemStackHandler(7);
    private int burningProgress = -1;
    private int autoBurning = 0;
    private int coockTime = 3000;

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
        compound.setInteger("burningProgress", this.burningProgress);
        compound.setInteger("autoBurning", this.autoBurning);
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

        boolean kill = true;
        if(ModItems.PLANKS.contains(plank.getItem())){
            kill = false;
        }

        int add = 0;
        for(int i = 0;i < 4;i++){
            if(burning_plank[i].getItem() == ModItems.PLANK_BURNING){
                kill = false;
                add++;
                NBTTagCompound nbt = new NBTTagCompound();
                if (burning_plank[i].hasTagCompound())
                    nbt = burning_plank[i].getTagCompound();
                else
                    nbt = new NBTTagCompound();

                if (!nbt.hasKey("time_created")) {
                    nbt.setLong("time_created", this.getWorld().getTotalWorldTime());
                    burning_plank[i].setTagCompound(nbt);
                }

                if (!this.getWorld().isRemote) {
                    long lifespan = this.getWorld().getTotalWorldTime() - burning_plank[i].getTagCompound().getLong("time_created");

                    if (lifespan > burning_plank[i].getMaxDamage()) {

                        burning_plank[i].shrink(1);

                    }else
                        burning_plank[i].setItemDamage((int)lifespan);
                }
            }
        }

        for(int i = 0; i <= autoBurning;i++){
            if(burning_plank[i].isEmpty()){
                if(ModItems.PLANKS.contains(plank.getItem())){
                    plank.shrink(1);
                    handler.setStackInSlot(i,new ItemStack(ModItems.PLANK_BURNING,1));
                }
            }
        }

        if(in.isEmpty()){
            if(burningProgress != -1){
                burningProgress = -1;
            }
        }else {
            if(out.getCount() == out.getMaxStackSize()){
                if(burningProgress != -1){
                    burningProgress = -1;
                }
            }

            if(out.isEmpty()){
                if(recipe(in.getItem()) != null){
                    if(burningProgress == -1){
                        burningProgress = 0;
                    }
                }
            }

            if(!out.isEmpty() && out.getCount() < out.getMaxStackSize()){
                if(recipe(in.getItem()) == out.getItem()){
                    if(burningProgress == -1){
                        burningProgress = 0;
                    }
                }else {
                    if(burningProgress != -1){
                        burningProgress = -1;
                    }
                }
            }
        }

        if(burningProgress != -1){
            burningProgress += add;
        }

        if(burningProgress >= coockTime){
            if(out.isEmpty()){
                handler.setStackInSlot(5,new ItemStack(recipe(in.getItem()),1));
            }else {
                out.shrink(-1);
            }

            in.shrink(1);
            burningProgress = -1;
        }

        if(kill){
            this.getWorld().setBlockState(pos, ModBlocks.BONFIRE.getDefaultState());
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
            case 2:
                return this.coockTime;
            default:
                return 0;
        }
    }

    public void setBurningPlanks(ArrayList<Long> lifespans){
        NBTTagCompound nbt = new NBTTagCompound();

        for (int i = 0;i < 4;i++) {
            nbt.setLong("time_created", world.getTotalWorldTime() - lifespans.get(i));

            handler.setStackInSlot(i, new ItemStack(ModItems.PLANK_BURNING, 1));
            handler.getStackInSlot(i).setTagCompound(nbt);
        }
    }

    private Item recipe (Item in){

        if (in == Items.STICK) return Item.getItemFromBlock(Blocks.TORCH);
        if (in == Items.CLAY_BALL) return Items.BRICK;
        if (in == Item.getItemFromBlock(Blocks.COBBLESTONE)) return Item.getItemFromBlock(Blocks.STONE);
        if (in == Items.PORKCHOP) return Items.COOKED_PORKCHOP;
        if (in == Items.CHICKEN) return Items.COOKED_CHICKEN;
        if (in == Items.RABBIT) return Items.COOKED_RABBIT;
        if (in == Items.MUTTON) return Items.COOKED_MUTTON;
        if (in == Items.BEEF) return Items.COOKED_BEEF;
        if (in == Items.POTATO) return Items.BAKED_POTATO;
        if (in == Items.FISH) return Items.COOKED_FISH;

        return null;
    }
}
