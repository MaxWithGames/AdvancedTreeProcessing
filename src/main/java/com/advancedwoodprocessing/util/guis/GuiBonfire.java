package com.advancedwoodprocessing.util.guis;

import com.advancedwoodprocessing.util.containers.ContainerBonfire;
import com.advancedwoodprocessing.util.entities.TileEntityBonfire;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiBonfire extends GuiContainer {

    private final InventoryPlayer player;
    private final TileEntityBonfire tileEntity;

    public GuiBonfire(InventoryPlayer player, TileEntityBonfire tileentity, InventoryPlayer player1, TileEntityBonfire tileEntity) {
        super(new ContainerBonfire(player, tileentity));

        this.player = player1;
        this.tileEntity = tileEntity;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String tileName = this.tileEntity.getDisplayName().getUnformattedText();
        this.fontRenderer.drawString(tileName, (this.xSize / 2 - this.fontRenderer.getStringWidth(tileName) / 2) + 3, 8, 4210752);
        this.fontRenderer.drawString(this.player.getDisplayName().getUnformattedText(), 122, this.ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){

    }


}
