package com.advancedwoodprocessing.util.handlers;

import com.advancedwoodprocessing.util.Reference;
import com.advancedwoodprocessing.util.containers.ContainerBonfire;
import com.advancedwoodprocessing.util.entities.TileEntityBonfire;
import com.advancedwoodprocessing.util.guis.GuiBonfire;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiHandlers implements IGuiHandler {
    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

        if(ID == Reference.GUI_BONFIRE) return new ContainerBonfire(player.inventory,(TileEntityBonfire) world.getTileEntity(new BlockPos(x,y,z)));
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

        if(ID == Reference.GUI_BONFIRE) return new GuiBonfire(player.inventory,(TileEntityBonfire) world.getTileEntity(new BlockPos(x,y,z)));
        return null;
    }
}
