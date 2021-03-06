package com.advancedwoodprocessing.util.guis;

import com.advancedwoodprocessing.util.Reference;
import com.advancedwoodprocessing.util.containers.ContainerBonfire;
import com.advancedwoodprocessing.util.entities.TileEntityBonfire;
import com.advancedwoodprocessing.util.handlers.PacketHandler;
import com.advancedwoodprocessing.util.packets.SliderPacket;

import net.minecraft.client.gui.GuiPageButtonList;
import net.minecraft.client.gui.GuiSlider;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiBonfire extends GuiContainer {
    private static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MOD_ID + ":textures/gui/bonfire.png");
    private final InventoryPlayer player;
    private final TileEntityBonfire tileEntity;
    private GuiSlider slider = null;

    public GuiBonfire(InventoryPlayer player, TileEntityBonfire tileEntity) {
        super(new ContainerBonfire(player, tileEntity));


        this.player = player;
        this.tileEntity = tileEntity;
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
//            	PacketHandler.INSTANCE.sendToServer(new SliderPacket(tileEntity.getPos(), (short)value));
            }

            @Override
            public void setEntryValue(int id, String value) {

            }
        }, 0, this.guiLeft, this.guiTop - 20, "test", 0, 3, tileEntity.getField(1), new GuiSlider.FormatHelper() {
            @Override
            public String getText(int id, String name, float value) {
                return "auto tossing " + (tileEntity.getField(1) + 1) + " planks";
            }
        });

        buttonList.add(slider);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){}

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){

        for(int i = 0; i <= 3;i++){
            if(Math.abs(slider.getSliderValue() - i) <= 0.5){
                slider.setSliderValue(i,false);
                if(tileEntity.getField(1) != i) {
                    PacketHandler.INSTANCE.sendToServer(new SliderPacket(tileEntity.getPos(), (short) i));
                }
            }
        }

        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(TEXTURES);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        if(tileEntity.getField(0) != -1){
            double k =  ((double) tileEntity.getField(0))/tileEntity.getField(2);
            int l = (int) (k * 24);
            this.drawTexturedModalRect(this.guiLeft + 83, this.guiTop + 13, 176, 0, l + 1, 16);
        }
        int f = (18+8)*(tileEntity.getField(1));
        this.drawTexturedModalRect(this.guiLeft + 44 + f, this.guiTop + 64, 176, 17, 8, 13);
    }
}
