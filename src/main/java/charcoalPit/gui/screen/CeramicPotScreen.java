package charcoalPit.gui.screen;

import charcoalPit.CharcoalPit;
import charcoalPit.gui.menu.CeramicPotMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CeramicPotScreen extends AbstractContainerScreen<CeramicPotMenu> {

    public static final ResourceLocation GUI_TEXTURES=ResourceLocation.fromNamespaceAndPath("minecraft","textures/gui/container/dispenser.png");

    public CeramicPotScreen(CeramicPotMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        guiGraphics.blit(GUI_TEXTURES,this.leftPos,this.topPos,0,0,this.imageWidth,this.imageHeight);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics,mouseX,mouseY);
    }
}
