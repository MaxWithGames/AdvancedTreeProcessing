package com.advancedwoodprocessing.blocks;

import com.advancedwoodprocessing.Main;
import com.advancedwoodprocessing.init.ModBlocks;
import com.advancedwoodprocessing.init.ModItems;
import com.advancedwoodprocessing.util.BlockTileEntity;
import com.advancedwoodprocessing.util.Reference;
import com.advancedwoodprocessing.util.entities.TileEntityBonfire;
import com.advancedwoodprocessing.util.handlers.SoundHandler;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class WorkingBonfire extends BlockTileEntity<TileEntityBonfire> {
//    public static final PropertyInteger BURNING_PLANKS_COUNT = PropertyInteger.create("planks_count", 0, 3);
//    public static final PropertyInteger PLANKS_COUNT = PropertyInteger.create("planks_count", 0, 3);

    public WorkingBonfire(String name, Material material, CreativeTabs tab) {
        super(name, material, tab);

        setHardness(0);
        setLightLevel(1.0F);
        setLightOpacity(0);
    }

    @Override
    public Class<TileEntityBonfire> getTileEntityClass() {
        return TileEntityBonfire.class;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return new AxisAlignedBB(0.0f, 0.0f, 0.0f, 1.0f, 0.25f, 1.0f);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        drops.add(new ItemStack(ModBlocks.BONFIRE));
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(ModBlocks.BONFIRE);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!worldIn.isRemote) {

            if((playerIn.getHeldItem(hand).getItem() == ModItems.PLANK)
                &&(worldIn.getTileEntity(pos) instanceof TileEntityBonfire)
                    &&(((TileEntityBonfire)worldIn.getTileEntity(pos))).handler.getStackInSlot(6).getCount() < 64){
                        playerIn.getHeldItem(hand).shrink(1);
                        ((TileEntityBonfire)worldIn.getTileEntity(pos)).handler.getStackInSlot(6).shrink(-1);


            }else {
                playerIn.openGui(Main.instance, Reference.GUI_BONFIRE, worldIn, pos.getX(), pos.getY(), pos.getZ());
            }
        }

        return true;
    }

    @Override
    public TileEntityBonfire createTileEntity(World world, IBlockState state)
    {
        return new TileEntityBonfire();
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    @SuppressWarnings("incomplete-switch")
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        int planksCount = 0;
        for (int i = 0;i < 4;i++){
            if(this.getTileEntity(worldIn, pos).handler.getStackInSlot(i).getItem() == ModItems.PLANK_BURNING){
                planksCount++;
            }
        }

        double d2 = (double)pos.getZ() + 0.5D;
        double d0 = (double)pos.getX() + 0.5D;

        for (int i = 0; i < planksCount; i++) {
            double d1 = (double)pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
            double d3 = rand.nextDouble() * 0.6D - 0.3D;
            double d4 = rand.nextDouble() * 0.6D - 0.3D;
            double s  = rand.nextDouble() * 0.015D;

            worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d3, d1, d2 + d4, 0.0D, s, 0.0D);
            worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d3, d1, d2 + d4, 0.0D, s, 0.0D);
        }

        if (planksCount > 0) {
            worldIn.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.AMBIENT, 0.1F * planksCount, 0.F, true);
            if (rand.nextDouble() > 0.75D)
                worldIn.playSound(
                        pos.getX(), pos.getY(), pos.getZ(),
                        SoundHandler.FIRE_CLAPS.get(rand.nextInt(SoundHandler.FIRE_CLAPS.size())),
                        SoundCategory.AMBIENT, 0.17F * planksCount * rand.nextFloat(), 0.0F, false
                );
        }
    }

}
