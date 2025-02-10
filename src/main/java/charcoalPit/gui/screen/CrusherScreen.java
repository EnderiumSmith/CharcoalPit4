package charcoalPit.gui.screen;

/*import charcoalPit.CharcoalPit;
import charcoalPit.core.DataComponentRegistry;
import charcoalPit.gui.menu.CrusherMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.Optional;

public class CrusherScreen extends AbstractContainerScreen<CrusherMenu> {

    public static final ResourceLocation GUI_TEXTURES=ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID,"textures/gui/container/crusher.png");

    public CrusherScreen(CrusherMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics,mouseX,mouseY);
        FluidStack fluidStack=this.menu.slots.get(this.menu.slots.size()-1).getItem().get(DataComponentRegistry.FLUID_DATA).copy();
        drawFluidTooltip(fluidStack,16000,mouseX,mouseY,106,35,32,16,guiGraphics);
    }

    public void drawFluidTooltip(FluidStack stack, int cap, int mousex, int mousey, int x1, int y1, int x2, int y2, GuiGraphics guiGraphics){
        if(!stack.isEmpty()&&isHovering(x1,y1,x2,y2,mousex,mousey)) {
            ArrayList<Component> list = new ArrayList<>();
            list.add(stack.getHoverName());
            list.add(Component.literal(stack.getAmount() + "/" + cap + " mB"));
            Optional<TooltipComponent> tooltipComponent = Optional.empty();
            guiGraphics.renderTooltip(this.font,list, tooltipComponent,mousex,mousey);

        }
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderLabels(guiGraphics, mouseX, mouseY);
        guiGraphics.blit(GUI_TEXTURES, 106, 35, 176, 60, 32, 16);
        int processTotal=this.menu.array.get(1);
        if(processTotal!=0){
            int progress=this.menu.array.get(0);
            int l=progress*22/processTotal;
            guiGraphics.blit(GUI_TEXTURES,62,37,176,17,l+1,13);
        }
        if(this.menu.array.get(2)>0) {
            int rf = (int) (this.menu.array.get(2) * 40 / 32001D) + 1;
            guiGraphics.blit(GUI_TEXTURES, 10, 63 - rf, 201, 41 - rf, 12, rf);
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        guiGraphics.blit(GUI_TEXTURES,this.leftPos,this.topPos,0,0,this.imageWidth,this.imageHeight);
        FluidStack fluidStack=this.menu.slots.get(this.menu.slots.size()-1).getItem().get(DataComponentRegistry.FLUID_DATA).copy();
        renderFluid(fluidStack,guiGraphics,leftPos,topPos,106,51,16,16000D);
        renderFluid(fluidStack,guiGraphics,leftPos,topPos,122,51,16,16000D);
    }

    public void renderFluid(FluidStack fluidStack, GuiGraphics guiGraphics, int leftPos, int topPos,int x,int y, int h, double cap){
        if(fluidStack.isEmpty())
            return;
        IClientFluidTypeExtensions fluidTexture=IClientFluidTypeExtensions.of(fluidStack.getFluid());
        TextureAtlasSprite sprite=this.minecraft.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(fluidTexture.getStillTexture(fluidStack));
        guiGraphics.setColor(1,1,1,2);
        int height=(int)((h)*fluidStack.getAmount()/(cap+1))+1;
        int c=fluidTexture.getTintColor(fluidStack);
        while (height>=16){
            innerBlit(guiGraphics,sprite.atlasLocation(),leftPos+x,leftPos+x+16,topPos+y-height,topPos+y+16-height,0,
                    sprite.getU0(),sprite.getU1(),sprite.getV0(),sprite.getV1(),(c>>16&255)/255.0F, (c>>8&255)/255.0F, (c&255)/255.0F, 1F);
            height-=16;
        }
        if(height>0){
            innerBlit(guiGraphics,sprite.atlasLocation(),leftPos+x,leftPos+x+16,topPos+y-height,topPos+y,0,
                    sprite.getU0(),sprite.getU1(),sprite.getV0(),(sprite.getV1()-sprite.getV0())*(height/16F)+sprite.getV0(),(c>>16&255)/255.0F, (c>>8&255)/255.0F, (c&255)/255.0F, 1F);
        }
        guiGraphics.setColor(1,1,1,1);
    }

    void innerBlit(
            GuiGraphics guiGraphics,
            ResourceLocation atlasLocation,
            int x1,
            int x2,
            int y1,
            int y2,
            int blitOffset,
            float minU,
            float maxU,
            float minV,
            float maxV,
            float red,
            float green,
            float blue,
            float alpha
    ) {
        RenderSystem.setShaderTexture(0, atlasLocation);
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.enableBlend();
        Matrix4f matrix4f = guiGraphics.pose().last().pose();
        BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        bufferbuilder.addVertex(matrix4f, (float)x1, (float)y1, (float)blitOffset)
                .setUv(minU, minV)
                .setColor(red, green, blue, alpha);
        bufferbuilder.addVertex(matrix4f, (float)x1, (float)y2, (float)blitOffset)
                .setUv(minU, maxV)
                .setColor(red, green, blue, alpha);
        bufferbuilder.addVertex(matrix4f, (float)x2, (float)y2, (float)blitOffset)
                .setUv(maxU, maxV)
                .setColor(red, green, blue, alpha);
        bufferbuilder.addVertex(matrix4f, (float)x2, (float)y1, (float)blitOffset)
                .setUv(maxU, minV)
                .setColor(red, green, blue, alpha);
        BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
        RenderSystem.disableBlend();
    }
}*/
