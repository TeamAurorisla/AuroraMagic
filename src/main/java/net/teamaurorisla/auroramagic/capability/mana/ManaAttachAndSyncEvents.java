package net.teamaurorisla.auroramagic.capability.mana;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.teamaurorisla.auroramagic.AuroraMagic;
import net.teamaurorisla.auroramagic.spell.SpellCastingService;

@Mod.EventBusSubscriber(modid = AuroraMagic.MODID)
public class ManaAttachAndSyncEvents {

    @SubscribeEvent
    public static void onAttachMana(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player player) {
            if (!(player.getCapability(ManaProvider.MANA_CAPABILITY).isPresent())) {
                event.addCapability(ManaProvider.LOCATION, new ManaProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        Player oldPlayer = event.getOriginal();
        Player newPlayer = event.getEntity();
        if (!newPlayer.level().isClientSide()) {
            oldPlayer.reviveCaps();
            ManaManager oldManager = ManaManager.of(oldPlayer);
            ManaManager newManager = ManaManager.of(newPlayer);
            if (event.isWasDeath()) oldManager.set(ManaType.STABLE, 0.0).set(ManaType.SURGE, 0.0);
            newManager.getManaData().setSelf(oldManager.getManaData()); //没调用newManager.setManaData()是因为这个地方给客户端发包没用，必须在Respawn和ChangedDimension事件里调用客户端发包。
            if (event.isWasDeath()) {
                newManager.getManaData().setOverloaded(false);
            }
            oldPlayer.invalidateCaps();
        }
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        if (!player.level().isClientSide()) ManaManager.of(player).sendToPlayerClient();
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        Player player = event.getEntity();
        if (!player.level().isClientSide()) ManaManager.of(player).sendToPlayerClient();
    }

    @SubscribeEvent
    public static void onPlayerChangeDim(PlayerEvent.PlayerChangedDimensionEvent event) {
        Player player = event.getEntity();
        if (!player.level().isClientSide()) ManaManager.of(player).sendToPlayerClient();
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        Player player = event.player;
        if (player.level().isClientSide() || !(player instanceof net.minecraft.server.level.ServerPlayer serverPlayer)) {
            return;
        }
        if (serverPlayer.tickCount % 20 != 0) {
            return;
        }
        boolean wasOverloaded = ManaManager.of(serverPlayer).isOverloaded();
        SpellCastingService.regenerateStableMana(serverPlayer);
        if (wasOverloaded && !ManaManager.of(serverPlayer).isOverloaded()) {
            serverPlayer.sendSystemMessage(Component.translatable("message.auroramagic.mana.overload_recovered").withStyle(ChatFormatting.AQUA));
        }
    }

}
