package com.advancedwoodprocessing.util.guis;

import com.advancedwoodprocessing.util.Reference;
import com.advancedwoodprocessing.util.containers.ContainerBonfire;
import com.advancedwoodprocessing.util.entities.TileEntityBonfire;
import net.minecraft.client.gui.GuiPageButtonList;
import net.minecraft.client.gui.GuiSlider;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiBonfire extends GuiContainer {
    private static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MOD_ID + ":textures/gui/bonfire.png");
    private final InventoryPlayer player;
    private final TileEntityBonfire tileentity;
    private GuiSlider slider = null;

    public GuiBonfire(InventoryPlayer player, TileEntityBonfire tileentity) {
        super(new ContainerBonfire(player, tileentity));

        this.player = player;
        this.tileentity = tileentity;
    }

    @Override
    public void initGui() {
        super.initGui();

        slider = new GuiSlider(new GuiPageButtonList.GuiResponder() {
            @Override
            public void setEntryValue(int id, boolean value) {

            }

            @Override
            public void setEntryValue(int id, float value) {
                tileentity.setField(1,(int) value);
            }

            @Override
            public void setEntryValue(int id, String value) {

            }
        }, 0, this.guiLeft, this.guiTop - 20, "test", 0, 3, tileentity.getField(1), new GuiSlider.FormatHelper() {
            @Override
            public String getText(int id, String name, float value) {
                return "test " + tileentity.getField(1);
            }
        });

        buttonList.add(slider);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){}

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(TEXTURES);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        if(tileentity.getField(0) != -1){
            double k =  ((double) tileentity.getField(0))/tileentity.getField(2);
            int l = (int) (k * 24);
            this.drawTexturedModalRect(this.guiLeft + 82, this.guiTop + 13, 176, 0, l + 1, 16);
        }
        int f = (18+8)*(tileentity.getField(1));
        this.drawTexturedModalRect(this.guiLeft + 44 + f, this.guiTop + 64, 176, 17, 8, 13);
    }
}
