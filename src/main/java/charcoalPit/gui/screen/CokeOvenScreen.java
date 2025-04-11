package charcoalPit.gui.screen;

import java.util.ArrayList;
import java.util.Optional;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;

import charcoalPit.CharcoalPit;
import charcoalPit.fluid.FluidRegistry;
import charcoalPit.gui.menu.CokeOvenMenu;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import org.joml.Matrix4f;

public class CokeOvenScreen extends AbstractContainerScreen<CokeOvenMenu> {

	public static final ResourceLocation GUI_TEXTURES=ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID,"textures/gui/container/coke_oven.png");

	public CokeOvenScreen(CokeOvenMenu menu, Inventory playerInventory, Component title) {
		super(menu, playerInventory, title);
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		super.render(guiGraphics, mouseX, mouseY, partialTick);
		renderTooltip(guiGraphics,mouseX,mouseY);
		FluidStack fluidStack=new FluidStack(FluidRegistry.CREOSOTE.source, this.menu.array.get(2));
		drawFluidTooltip(fluidStack,16000,mouseX,mouseY,98,22,16,29,guiGraphics);
	}

	public void drawFluidTooltip(FluidStack stack,int cap,int mousex, int mousey, int x1,int y1,int x2,int y2,GuiGraphics guiGraphics){
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
		int burnTotal=this.menu.array.get(1);
		if(burnTotal==0)
			burnTotal=200;
		int burnTime=this.menu.array.get(0);
		if(burnTime>0) {
			int k = burnTime * 13 / burnTotal;
			guiGraphics.blit(GUI_TEXTURES,71,23+12-k,176,12-k,14,k+1);
		}
		guiGraphics.blit(GUI_TEXTURES, 98, 22, 176, 31, 16, 29);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
		guiGraphics.blit(GUI_TEXTURES,this.leftPos,this.topPos,0,0,this.imageWidth,this.imageHeight);
		FluidStack fluidStack=new FluidStack(FluidRegistry.CREOSOTE.source, this.menu.array.get(2));
		renderFluid(fluidStack,guiGraphics,leftPos,topPos,98,51,29,16000D);
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
					sprite.getU0(),sprite.getU1(),sprite.getV0(),sprite.getV1(),(c>>16&255)/255.0F, (c>>8&255)/255.0F, (c&255)/255.0F, 1F/*(c>>24&255)/255f*/);
			height-=16;
		}
		if(height>0){
			innerBlit(guiGraphics,sprite.atlasLocation(),leftPos+x,leftPos+x+16,topPos+y-height,topPos+y,0,
					sprite.getU0(),sprite.getU1(),sprite.getV0(),(sprite.getV1()-sprite.getV0())*(height/16F)+sprite.getV0(),(c>>16&255)/255.0F, (c>>8&255)/255.0F, (c&255)/255.0F, 1F/*(c>>24&255)/255f*/);
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
}
