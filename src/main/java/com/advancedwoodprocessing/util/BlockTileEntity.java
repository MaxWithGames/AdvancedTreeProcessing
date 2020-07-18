package com.advancedwoodprocessing.util;

import javax.annotation.Nullable;

import com.advancedwoodprocessing.blocks.BlockBase;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public abstract class BlockTileEntity<T extends TileEntity> extends BlockBase {

    public BlockTileEntity(String name, Material material, CreativeTabs tab) {

        super(name, material, tab);

        GameRegistry.registerTileEntity(this.getTileEntityClass(), this.getRegistryName().toString());
    }

    public abstract Class<T> getTileEntityClass();

    public T getTileEntity(IBlockAccess world, BlockPos position) {

        return (T) world.getTileEntity(position);
    }

    @Override
    public boolean hasTileEntity(IBlockState blockState) {

        return true;
    }

    @Nullable
    @Override
    public abstract T createTileEntity(World world, IBlockState blockState);
}
