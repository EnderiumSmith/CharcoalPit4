package charcoalPit.gui.screen;

import charcoalPit.CharcoalPit;
import charcoalPit.gui.menu.BlastFurnaceMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class BlastFurnaceScreen extends AbstractContainerScreen<BlastFurnaceMenu> {

    public static final ResourceLocation GUI_TEXTURES=ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID,"textures/gui/container/blast_furnace.png");

    public static final int C_TO_MIN=273-1000;
    public static final int MAX_TEMP_LOWER=3300;

    public BlastFurnaceScreen(BlastFurnaceMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics,mouseX,mouseY);
        if(isHovering(47,36,3,34,mouseX,mouseY)){
            guiGraphics.renderTooltip(this.font,Component.literal("T="+this.menu.array.get(4)+"C"),mouseX,mouseY);
        }
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderLabels(guiGraphics, mouseX, mouseY);
        int burnTotal=this.menu.array.get(3);
        if(burnTotal==0)
            burnTotal=200;
        int burnTime=this.menu.array.get(2);
        if(burnTime>0) {
            int k = burnTime * 13 / burnTotal;
            guiGraphics.blit(GUI_TEXTURES,56,36+12-k,176,12-k,14,k+1);
        }
        int processTotal=this.menu.array.get(1);
        if(processTotal!=0){
            int progress=this.menu.array.get(0);
            int l=progress*24/processTotal;
            guiGraphics.blit(GUI_TEXTURES,79,34,176,14,l+1,16);
        }
        int temperature=this.menu.array.get(4);
        if(temperature>0){
            int t=temperature*34/MAX_TEMP_LOWER;
            guiGraphics.blit(GUI_TEXTURES,47,69-t,187,95-t,3,t);
        }

    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        guiGraphics.blit(GUI_TEXTURES,this.leftPos,this.topPos,0,0,this.imageWidth,this.imageHeight);
    }
}
