package net.teamaurorisla.auroramagic.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.teamaurorisla.auroramagic.block.entity.ArcanePedestalBlockEntity;
import org.jetbrains.annotations.NotNull;

public class ArcanePedestalRenderer implements BlockEntityRenderer<ArcanePedestalBlockEntity> {

    public ArcanePedestalRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(ArcanePedestalBlockEntity blockEntity, float partialTick, @NotNull PoseStack poseStack,
                       @NotNull MultiBufferSource buffer, int packedLight, int packedOverlay) {

        IItemHandler itemHandler = blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, Direction.UP).resolve().orElse(null);
        if (itemHandler == null) {
            return;
        }

        ItemStack displayItem = itemHandler.getStackInSlot(0);
        if (displayItem.isEmpty()) {
            return;
        }

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        Level level = blockEntity.getLevel();

        long gameTime = level != null ? level.getGameTime() : 0; // 获取游戏时间
        float time = gameTime + partialTick;  // 时间计算
        float rotation = (time % 360) * 2.0F;   // 转一圈的时间
        float bobOffset = (float) Math.sin(time / 20.0D) * 0.05F;  // 浮动效果

        poseStack.pushPose();
        poseStack.translate(0.5D, 1.5D + bobOffset, 0.5D);
        poseStack.mulPose(Axis.YP.rotationDegrees(rotation));
        poseStack.scale(0.5F, 0.5F, 0.5F);

        int lightLevel = getLightLevel(level, blockEntity.getBlockPos());  //光照
        itemRenderer.renderStatic(displayItem, ItemDisplayContext.FIXED, lightLevel, OverlayTexture.NO_OVERLAY, poseStack, buffer, level, 1);

        poseStack.popPose();
    }

    // 光照，来自 Mattias150784/Pedestals
    private int getLightLevel(Level level, BlockPos pos) {
        if (level == null) {
            return LightTexture.pack(0, 0);
        }
        int blockLight = level.getBrightness(LightLayer.BLOCK, pos);
        int skyLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(blockLight, skyLight);
    }
}
