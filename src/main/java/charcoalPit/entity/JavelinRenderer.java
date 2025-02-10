package charcoalPit.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class JavelinRenderer extends EntityRenderer<ThrownJavelin> {

    private final ItemRenderer itemRenderer;
    private final RandomSource random = RandomSource.create();

    public JavelinRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer=context.getItemRenderer();
    }

    @Override
    public ResourceLocation getTextureLocation(ThrownJavelin entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }

    @Override
    public void render(ThrownJavelin p_entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTick, p_entity.yRotO, p_entity.getYRot()) - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTick, p_entity.xRotO, p_entity.getXRot()) - 135.0F));
        poseStack.translate(0.7F,-0.7F,0F);
        ItemStack stack=p_entity.getDefaultPickupItem();
        BakedModel model=this.itemRenderer.getModel(stack, p_entity.level(),null,p_entity.getId());
        itemRenderer.render(stack, ItemDisplayContext.HEAD,false,poseStack,bufferSource,packedLight, OverlayTexture.NO_OVERLAY,model);
        poseStack.popPose();
        super.render(p_entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
