package com.advancedwoodprocessing.blocks;

import com.advancedwoodprocessing.init.ModBlocks;
import com.advancedwoodprocessing.init.ModItems;
import com.advancedwoodprocessing.util.IHasModel;
import com.advancedwoodprocessing.util.entities.TileEntityCounter;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class Bonfire extends BlockBase implements IHasModel {

    public Bonfire(String name, Material material, CreativeTabs tab) {
        super(name, material, tab);

        setHardness(0);
        setLightOpacity(0);
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
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {

            if (playerIn.getHeldItem(hand).getItem() == ModItems.PLANK_BURNING){
                worldIn.setBlockState(pos, ModBlocks.BONFIRE_BASE.getDefaultState());
            }

            ItemStack held = playerIn.getHeldItem(hand);

            if (held.getItem() == ModItems.PLANK_BURNING) {

                NBTTagCompound ItemNBT = held.getTagCompound();

                if (ItemNBT.hasKey("time_created")) {
                    if(worldIn.getTileEntity(pos) instanceof TileEntityCounter){
                        ((TileEntityCounter) worldIn.getTileEntity(pos)).addLifespan(worldIn.getTotalWorldTime() - ItemNBT.getLong("time_created"));
                        playerIn.replaceItemInInventory(playerIn.inventory.currentItem, new ItemStack(Blocks.AIR));
                    }
                }
            }
        }

        return true;
    }
}
