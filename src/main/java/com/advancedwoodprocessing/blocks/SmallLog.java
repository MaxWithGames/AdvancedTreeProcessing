package com.advancedwoodprocessing.blocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.advancedwoodprocessing.events.DropHandler;
import com.advancedwoodprocessing.init.ModBlocks;
import com.advancedwoodprocessing.init.ModItems;
import com.advancedwoodprocessing.util.BlockTileEntity;
import com.advancedwoodprocessing.util.IHasModel;
import com.advancedwoodprocessing.util.entities.TileEntityCounter;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class SmallLog extends BlockBase implements IHasModel{
	static final PropertyBool ON_WOODPROCESSOR = PropertyBool.create("on_woodprocessor");
	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	
	public static final ArrayList<EnumFacing> POSSIBLE_FACES = new ArrayList<EnumFacing>(Arrays.asList(
		EnumFacing.EAST,
		EnumFacing.NORTH,
		EnumFacing.SOUTH,
		EnumFacing.WEST
	));
	
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, POSSIBLE_FACES.get((new Random()).nextInt(4)));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
	    return new BlockStateContainer(this, new IProperty[] {FACING, ON_WOODPROCESSOR});
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
	    return getDefaultState().withProperty(ON_WOODPROCESSOR, meta % 2 == 0 ? false : true).withProperty(FACING, EnumFacing.VALUES[meta / 2]);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		int offset = state.getValue(ON_WOODPROCESSOR).booleanValue() ? 1 : 0;
	    return state.getValue(FACING).getIndex() * 2 + offset;
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return state.withProperty(ON_WOODPROCESSOR, worldIn.getBlockState(pos.add(0, -1, 0)).getBlock() instanceof WoodProcessor);
	}
	
	private int getPlanksDropCount() {
		double luck = Math.random();
		if (luck < 0.25)
			return 1;
		if (luck > 0.75)
			return 3;
		return 2;
	}
	
	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		if (this.harvesters != null)
			if ((this.harvesters.get().getHeldItemMainhand().getItem() instanceof ItemAxe) &&  
			(world.getBlockState(pos.add(0, -1, 0)).getBlock() instanceof WoodProcessor)) {
				for (Item plank: ModItems.PLANKS) {
					if ((this.getRegistryName().toString() + "_plank").equals(plank.getRegistryName().toString()))
						drops.add(new ItemStack(plank, getPlanksDropCount()));
				}
				return;
			}
		drops.add(new ItemStack(this));
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
		if (source.getBlockState(pos.add(0, -1, 0)).getBlock() instanceof WoodProcessor)
			return new AxisAlignedBB(0.25f, -0.25f, 0.25f, 0.75f, 0.25f, 0.75f);
        return new AxisAlignedBB(0.25f, 0.0f, 0.25f, 0.75f, 0.5f, 0.75f);
    }
	
	private int timer = 100;
	
	public SmallLog(String name, Material material, CreativeTabs tab) {
		super(name, material, tab);
		
		setHardness(0.01f);
		setHarvestLevel("axe", 0);
		setLightOpacity(0);
		
		setDefaultState(this.blockState.getBaseState().withProperty(ON_WOODPROCESSOR, false).withProperty(FACING, EnumFacing.EAST));
		
		ModBlocks.SMALL_LOGS.add(this);
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
