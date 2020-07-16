package com.advancedwoodprocessing.blocks;

import com.advancedwoodprocessing.init.ModBlocks;
import com.advancedwoodprocessing.init.ModItems;
import com.advancedwoodprocessing.util.IHasModel;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class SmallLog extends BlockBase implements IHasModel{
	static final PropertyBool on_woodprocessor = PropertyBool.create("on_woodprocessor");
	
	@Override
	protected BlockStateContainer createBlockState() {
	    return new BlockStateContainer(this, new IProperty[] {on_woodprocessor});
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
	    return getDefaultState().withProperty(on_woodprocessor, meta == 0 ? false : true);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
	    boolean bool = state.getValue(on_woodprocessor).booleanValue();
	    if (bool)
	    	return 1;
	    return 0;
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return state.withProperty(on_woodprocessor, worldIn.getBlockState(pos.add(0, -1, 0)).getBlock() instanceof WoodProcessor);
	}
	
	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		if (this.harvesters != null)
			if ((this.harvesters.get().getHeldItemMainhand().getItem() instanceof ItemAxe) &&  
			(world.getBlockState(pos.add(0, -1, 0)).getBlock() instanceof WoodProcessor)) {
				 drops.add(new ItemStack(ModItems.PLANK, 2));
				 return;
			}
		drops.add(new ItemStack(ModBlocks.SMALL_LOG,  1));
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
		if (source.getBlockState(pos.add(0, -1, 0)).getBlock() instanceof WoodProcessor)
			return new AxisAlignedBB(0.3125f, -0.25f, 0.3125f, 0.6875f, 0.25f, 0.6875f);
        return new AxisAlignedBB(0.3125f, 0.0f, 0.3125f, 0.6875f, 0.5f, 0.6875f);
    }
	
	public SmallLog(String name, Material material, CreativeTabs tab) {
		super(name, material, tab);
		
		setHardness(0.0f);
		setHarvestLevel("axe", 0);
		setLightOpacity(0);
		
		setDefaultState(this.blockState.getBaseState().withProperty(on_woodprocessor, false));
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
}
