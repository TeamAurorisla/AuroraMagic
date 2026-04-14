package net.teamaurorisla.auroramagic.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.teamaurorisla.auroramagic.capability.mana.ManaManager;
import net.teamaurorisla.auroramagic.capability.mana.ManaType;
import net.teamaurorisla.auroramagic.registry.AMAttribute;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class EffectManaOverflow extends MobEffect {

    private static final UUID MANA_OVERFLOW = UUID.fromString("8dd4ab10-f22a-4695-a00f-138ab7f8d377");
    private static final int TICKS_PER_TRIGGER = 100;

    public EffectManaOverflow(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    public void applyEffectTick(@NotNull LivingEntity entity, int amplifier) {
        super.applyEffectTick(entity, amplifier);
        if (entity.level().isClientSide() || !(entity instanceof Player player)) {
            return;
        }

        ManaManager manaManager = new ManaManager(player);
        double stableMana = manaManager.get(ManaType.STABLE);
        double maxStableMana = manaManager.get(ManaType.MAX_STABLE);

        if (stableMana <= maxStableMana) {
            manaManager.add(ManaType.STABLE, 1);
        }

    }

    @Override
    public void addAttributeModifiers(@NotNull LivingEntity entity, @NotNull AttributeMap attributeMap, int amplifier) {
        super.addAttributeModifiers(entity, attributeMap, amplifier);

        AttributeInstance instanceStable = entity.getAttribute(AMAttribute.MAX_STABLE_MANA.get());
        AttributeInstance instanceSurge = entity.getAttribute(AMAttribute.MAX_SURGE_MANA.get());

        if (instanceStable == null || instanceSurge == null) {
            return;
        }

        instanceStable.removeModifier(MANA_OVERFLOW);
        double stableModifier = 2.0 * (amplifier);
        AttributeModifier stable = new AttributeModifier(
                MANA_OVERFLOW,
                "mana_overflow_modifier",
                stableModifier,
                AttributeModifier.Operation.ADDITION
        );

        instanceSurge.removeModifier(MANA_OVERFLOW);
        double surgeModifier = - 2.0 * (amplifier);
        AttributeModifier surge = new AttributeModifier(
                MANA_OVERFLOW,
                "mana_overflow_modifier",
                surgeModifier,
                AttributeModifier.Operation.ADDITION
        );

        instanceStable.addTransientModifier(stable);
        instanceSurge.addTransientModifier(surge);
    }

    @Override
    public void removeAttributeModifiers(@NotNull LivingEntity entity, @NotNull AttributeMap attributeMap, int amplifier) {
        super.removeAttributeModifiers(entity, attributeMap, amplifier);

        AttributeInstance instanceStable = entity.getAttribute(AMAttribute.MAX_STABLE_MANA.get());
        if (instanceStable != null) {
            instanceStable.removeModifier(MANA_OVERFLOW);
        }

        AttributeInstance instanceSurge = entity.getAttribute(AMAttribute.MAX_SURGE_MANA.get());
        if (instanceSurge != null) {
            instanceSurge.removeModifier(MANA_OVERFLOW);
        }
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration % (TICKS_PER_TRIGGER - (20 * amplifier)) == 0;
    }

}
