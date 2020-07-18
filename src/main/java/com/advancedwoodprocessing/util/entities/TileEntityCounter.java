package com.advancedwoodprocessing.util.entities;

import java.util.ArrayList;
import java.util.List;

import com.advancedwoodprocessing.init.ModItems;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityCounter extends TileEntity implements ITickable{

	private ArrayList<Long> lifespans = new ArrayList<Long>();
    
    public TileEntityCounter() {
    	super();
    }
    
    public void addLifespan(long lifespan) {
        this.lifespans.add(lifespan);
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
		
		if (this.lifespans.size() >= 4)
			System.out.println("Bonfire is ready");
	}
}
