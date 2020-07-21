package com.advancedwoodprocessing.blocks;

import com.advancedwoodprocessing.Main;
import com.advancedwoodprocessing.init.ModBlocks;
import com.advancedwoodprocessing.util.BlockTileEntity;
import com.advancedwoodprocessing.util.Reference;
import com.advancedwoodprocessing.util.entities.TileEntityBonfire;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

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
            playerIn.openGui(Main.instance, Reference.GUI_BONFIRE, worldIn, pos.getX(), pos.getY(), pos.getZ());

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

//
//	@Override
//	protected BlockStateContainer createBlockState() {
//	    return new BlockStateContainer(this, new IProperty[] {BURNING_PLANKS_COUNT, PLANKS_COUNT});
//	}

//    @Override
//    public IBlockState getStateFromMeta(int meta) {
//        return getDefaultState().withProperty(PLANKS_COUNT, meta);
//    }
//
//    @Override
//    public int getMetaFromState(IBlockState state) {
//        return state.getValue(PLANKS_COUNT).intValue();
//    }

}
