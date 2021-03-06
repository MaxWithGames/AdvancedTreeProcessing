package com.advancedwoodprocessing.blocks;

import com.advancedwoodprocessing.util.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class WoodProcessor extends BlockBase implements IHasModel{

	public WoodProcessor(String name, Material material, CreativeTabs tab) {
		super(name, material, tab);
		
		setHardness(2.0f);
		setHarvestLevel("axe", 0);
		setLightOpacity(0);
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return new AxisAlignedBB(0.0f, 0.0f, 0.0f, 1.0f, 0.75f, 1.0f);
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
