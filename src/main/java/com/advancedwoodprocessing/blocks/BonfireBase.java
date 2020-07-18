package com.advancedwoodprocessing.blocks;

import com.advancedwoodprocessing.init.ModItems;
import com.advancedwoodprocessing.util.BlockTileEntity;
import com.advancedwoodprocessing.util.IHasModel;
import com.advancedwoodprocessing.util.entities.TileEntityCounter;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BonfireBase extends BlockTileEntity<TileEntityCounter> implements IHasModel{
	@Override
    public Class<TileEntityCounter> getTileEntityClass() {

        return TileEntityCounter.class;
    }

    @Override
    public TileEntityCounter createTileEntity(World world, IBlockState blockState) {

        return new TileEntityCounter();
    }
	
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    	if (!worldIn.isRemote) {
    		
    		ItemStack held = playerIn.getHeldItem(hand);
    		
    		if (held.getItem() == ModItems.PLANK_BURNING) {
    			
    			NBTTagCompound ItemNBT = held.getTagCompound();
    			
    			if (ItemNBT.hasKey("time_created")) {
    				this.getTileEntity(worldIn, pos).addLifespan(worldIn.getTotalWorldTime() - ItemNBT.getLong("time_created"));
    				playerIn.replaceItemInInventory(playerIn.inventory.currentItem, new ItemStack(Blocks.AIR));
    			}
    		}
    	}
    	
        return true;
    }
    
	public BonfireBase(String name, Material material, CreativeTabs tab) {
		super(name, material, tab);
	}
}
