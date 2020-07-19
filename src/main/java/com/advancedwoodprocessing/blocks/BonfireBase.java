package com.advancedwoodprocessing.blocks;

import java.util.Random;

import com.advancedwoodprocessing.init.ModItems;
import com.advancedwoodprocessing.util.BlockTileEntity;
import com.advancedwoodprocessing.util.IHasModel;
import com.advancedwoodprocessing.util.entities.TileEntityCounter;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BonfireBase extends BlockTileEntity<TileEntityCounter> implements IHasModel{
	public static final PropertyInteger PLANKS_COUNT = PropertyInteger.create("planks_count", 0, 15);
	
	@SideOnly(Side.CLIENT)
    @SuppressWarnings("incomplete-switch")
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
		int planksCount = this.getTileEntity(worldIn, pos).getCount();
		System.out.println(planksCount);
		
		if (planksCount > 0) {
			double d0 = (double)pos.getX() + 0.5D;
			double d1 = (double)pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
			double d2 = (double)pos.getZ() + 0.5D;
			double d3 = rand.nextDouble() * 0.6D - 0.3D;
			double d4 = rand.nextDouble() * 0.6D - 0.3D;
		
			worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
			worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
		}
    }
	
	//@Override
	//public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    //{
    //    return new AxisAlignedBB(-1.f, 0.0f, -1.f, 2.0f, 1.0f, 2.0f);
    //}
	
	@Override
	protected BlockStateContainer createBlockState() {
	    return new BlockStateContainer(this, new IProperty[] {PLANKS_COUNT});
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
	    return getDefaultState().withProperty(PLANKS_COUNT, meta);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
	    return state.getValue(PLANKS_COUNT).intValue();
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return state.withProperty(PLANKS_COUNT, this.getTileEntity(worldIn, pos).getCount());
	}
	
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
    
    //@Override
    //public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state)
    //{
    //}
    
	public BonfireBase(String name, Material material, CreativeTabs tab) {
		super(name, material, tab);
	}
}