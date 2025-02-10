package charcoalPit.TESR;

import charcoalPit.CharcoalPit;
import charcoalPit.tile.TileQuern;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.entity.EndermanRenderer;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.neoforge.client.model.data.ModelData;

public class TESRQuern implements BlockEntityRenderer<TileQuern> {

    public static final ResourceLocation QUERN_STONE_ID=ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID, "block/quern_stone");
    public static final ModelResourceLocation QUERN_STONE=ModelResourceLocation.standalone(QUERN_STONE_ID);
    RandomSource rand=RandomSource.create();

    @Override
    public void render(TileQuern blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        BakedModel QUERN= Minecraft.getInstance().getModelManager().getModel(QUERN_STONE);
        poseStack.pushPose();
        if(blockEntity.total>0){
            float rot=((blockEntity.time+partialTick)/ blockEntity.total)*360F;
            poseStack.translate(0.5F,0F,0.5F);
            poseStack.mulPose(Axis.YP.rotationDegrees(rot));
            poseStack.translate(-0.5F,0F,-0.5F);
        }
        Minecraft.getInstance().getBlockRenderer().getModelRenderer().tesselateWithAO(blockEntity.getLevel(),QUERN,blockEntity.getBlockState(),blockEntity.getBlockPos(),poseStack,bufferSource.getBuffer(RenderType.SOLID),true,rand,10,packedOverlay, ModelData.EMPTY, RenderType.SOLID);
        poseStack.popPose();
        if(!blockEntity.inventory.getStackInSlot(0).isEmpty()) {
            poseStack.pushPose();
            poseStack.translate(0.5F,13F/16F,0.5F);
            float rot=(blockEntity.getLevel().getGameTime()+partialTick)/20F;
            poseStack.mulPose(Axis.YP.rotation(rot));
            ItemRenderer itemRenderer=Minecraft.getInstance().getItemRenderer();
            BakedModel model=itemRenderer.getModel(blockEntity.inventory.getStackInSlot(0), blockEntity.getLevel(),null,10);
            itemRenderer.render(blockEntity.inventory.getStackInSlot(0), ItemDisplayContext.GROUND,false,poseStack,bufferSource,packedLight,packedOverlay,model);
            poseStack.popPose();
        }
    }
}
