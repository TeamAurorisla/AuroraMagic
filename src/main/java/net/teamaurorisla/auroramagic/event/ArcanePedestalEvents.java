package net.teamaurorisla.auroramagic.event;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.IItemHandler;
import net.teamaurorisla.auroramagic.AuroraMagic;
import net.teamaurorisla.auroramagic.block.entity.ArcanePedestalBlockEntity;
import net.teamaurorisla.auroramagic.renderer.ArcanePedestalRenderer;
import net.teamaurorisla.auroramagic.registry.AMBlockEntity;

@Mod.EventBusSubscriber(modid = AuroraMagic.MODID)
public class ArcanePedestalEvents {

    @SubscribeEvent
    public static void onBreak(BlockEvent.BreakEvent event) {
        LevelAccessor levelAccessor = event.getLevel();
        if (levelAccessor instanceof Level level && !level.isClientSide()) {
            BlockPos pos = event.getPos();
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof ArcanePedestalBlockEntity pedestal) {
                IItemHandler itemHandler = pedestal.getCapability(ForgeCapabilities.ITEM_HANDLER, Direction.UP).resolve().orElse(null);
                if (itemHandler == null) {
                    return;
                }

                ItemStack displayItem = itemHandler.extractItem(0, 1, false);
                if (!displayItem.isEmpty()) {
                    Vec3 posC = pos.getCenter();
                    level.addFreshEntity(new ItemEntity(level, posC.x, posC.y + 1, posC.z, displayItem));
                }
            }
        }
    }

}

/**
 * 处理客户端事件
 */
@Mod.EventBusSubscriber(modid = AuroraMagic.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
class ClientEvents {

    @SubscribeEvent
    public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(AMBlockEntity.ARCANE_PEDESTAL.get(), ArcanePedestalRenderer::new);
    }

}