package com.advancedwoodprocessing.util.entities;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.advancedwoodprocessing.blocks.BonfireBase;
import com.advancedwoodprocessing.init.ModBlocks;
import com.advancedwoodprocessing.init.ModItems;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityCounter extends TileEntity implements ITickable{

	private ArrayList<Long> lifespans = new ArrayList<Long>();
    private int count = 0;
    
    public TileEntityCounter() {
    	super();
    }
    
    public void addLifespan(long lifespan) {
    	//System.out.println("Lifespan added");
        this.lifespans.add(lifespan);
    }
    
    public int getCount() {
    	return this.count;
    }
    
    public ArrayList<Long> getLifespans() {
    	return this.lifespans;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {

        tagCompound.setInteger("l_count", this.lifespans.size());
        for (int i = 0; i < this.lifespans.size(); i++)
        	tagCompound.setLong("lifespan_" + i, lifespans.get(i));
        
        return super.writeToNBT(tagCompound);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {

        int count = tagCompound.getInteger("l_count");
        this.lifespans.clear();
        
        for (int i = 0; i < count; i++)
        	this.lifespans.add(tagCompound.getLong("lifespan_" + i));

        super.readFromNBT(tagCompound);
    }

	@Override
	public void update() {		
		for (int i = 0; i < this.lifespans.size(); i++)
			this.lifespans.set(i, this.lifespans.get(i) + 1);
		
		this.lifespans.removeIf(e -> e >= ModItems.PLANK_BURNING.getMaxDamage());
		this.count = lifespans.size();
			
		sendUpdates();

		//Does literally nothing but saves some performance
		if (this.getCount() == 0)
			this.getWorld().setBlockState(pos, ModBlocks.BONFIRE.getDefaultState());
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
