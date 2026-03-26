package net.teamaurorisla.auroramagic;

import com.mojang.logging.LogUtils;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.teamaurorisla.auroramagic.gui.overlay.ManaDataOverlay;
import net.teamaurorisla.auroramagic.network.AMNetworkHandler;
import net.teamaurorisla.auroramagic.registry.*;
import org.slf4j.Logger;

@Mod(AuroraMagic.MODID)
public class AuroraMagic {

    public static final String MODID = "auroramagic";
    public static final Logger LOGGER = LogUtils.getLogger();

    public AuroraMagic(FMLJavaModLoadingContext context) {

        IEventBus bus = context.getModEventBus();

        bus.addListener(this::onFMLCommonSetup);
        bus.addListener(this::onRegisterGuiOverlays);
        bus.addListener(this::onEntityAttributeModification);

        AMItem.REGISTER.init(bus);
        AMItem.ITEM.register(bus);
        AMAttribute.ATTRIBUTE.register(bus);
        AMEffect.EFFECT.register(bus);
        AMBlock.BLOCK.register(bus);
        AMBlockItem.BLOCK_ITEM.register(bus);
        AMBlockEntity.BLOCK_ENTITY.register(bus);
        AMCreativeModeTab.REGISTER.init(bus);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void onFMLCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(AMNetworkHandler::register);
    }

    private void onRegisterGuiOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAbove(VanillaGuiOverlay.HOTBAR.id(), "mana_overlay", new ManaDataOverlay());
    }

    private void onEntityAttributeModification(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, AMAttribute.MANA_EFFICIENCY.get());
        event.add(EntityType.PLAYER, AMAttribute.SPELL_POWER.get());
        event.add(EntityType.PLAYER, AMAttribute.BLAZE_AFFINITY.get());
        event.add(EntityType.PLAYER, AMAttribute.FROST_AFFINITY.get());
        event.add(EntityType.PLAYER, AMAttribute.EARTH_AFFINITY.get());
        event.add(EntityType.PLAYER, AMAttribute.STORM_AFFINITY.get());
        event.add(EntityType.PLAYER, AMAttribute.DIVINE_AFFINITY.get());
        event.add(EntityType.PLAYER, AMAttribute.SPELL_RESISTANCE.get());
        event.add(EntityType.PLAYER, AMAttribute.BLAZE_RESISTANCE.get());
        event.add(EntityType.PLAYER, AMAttribute.FROST_RESISTANCE.get());
        event.add(EntityType.PLAYER, AMAttribute.EARTH_RESISTANCE.get());
        event.add(EntityType.PLAYER, AMAttribute.STORM_RESISTANCE.get());
        event.add(EntityType.PLAYER, AMAttribute.DIVINE_RESISTANCE.get());
        event.add(EntityType.PLAYER, AMAttribute.MAX_STABLE_MANA.get());
        event.add(EntityType.PLAYER, AMAttribute.MAX_SURGE_MANA.get());
    }

}
