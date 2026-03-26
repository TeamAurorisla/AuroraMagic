package net.teamaurorisla.auroramagic.spell;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.teamaurorisla.auroramagic.capability.mana.ManaManager;
import net.teamaurorisla.auroramagic.capability.mana.ManaType;

import java.util.List;

public final class SpellCastingService {

    private static final double OVERLOAD_ALLOWANCE = 4.0;
    private static final double OVERLOAD_STABLE_PENALTY = 4.0;

    private SpellCastingService() {
    }

    public static SpellCastOutcome cast(ServerLevel level, ServerPlayer player, SpellDefinition spell) {
        ManaManager manaManager = ManaManager.of(player);
        SpellCastCostResult costResult = paySpellCost(manaManager, spell.baseManaCost());
        if (costResult == SpellCastCostResult.NORMAL_CAST || costResult == SpellCastCostResult.OVERLOAD_CAST) {
            applySpellEffect(level, player, spell);
        }
        return new SpellCastOutcome(spell, costResult);
    }

    private static SpellCastCostResult paySpellCost(ManaManager manaManager, int baseManaCost) {
        double currentMana = manaManager.getCurrentMana();
        if (currentMana >= baseManaCost) {
            manaManager.consume(baseManaCost, true);
            return SpellCastCostResult.NORMAL_CAST;
        }
        if (manaManager.isOverloaded()) {
            return SpellCastCostResult.BLOCKED_CONTINUOUS_OVERLOAD;
        }
        if (currentMana + OVERLOAD_ALLOWANCE >= baseManaCost) {
            manaManager.consume(currentMana, true);
            manaManager.setOverloaded(true, OVERLOAD_STABLE_PENALTY);
            return SpellCastCostResult.OVERLOAD_CAST;
        }
        return SpellCastCostResult.INSUFFICIENT_MANA;
    }

    private static void applySpellEffect(ServerLevel level, ServerPlayer player, SpellDefinition spell) {
        switch (spell.spellId()) {
            case "blaze_burst" -> castBlazeBurst(level, player);
            case "frost_nova" -> castFrostNova(level, player);
            case "storm_strike" -> castStormStrike(level, player);
            case "earth_guard" -> castEarthGuard(level, player);
            default -> throw new IllegalArgumentException("Unsupported spell effect: " + spell.spellId());
        }
    }

    private static void castBlazeBurst(ServerLevel level, ServerPlayer player) {
        Vec3 look = player.getLookAngle();
        Vec3 eyePosition = player.getEyePosition().add(look.scale(0.75));
        SmallFireball fireball = new SmallFireball(level, player, look.x, look.y, look.z);
        fireball.setPos(eyePosition.x, eyePosition.y, eyePosition.z);
        level.addFreshEntity(fireball);
        level.sendParticles(ParticleTypes.FLAME, eyePosition.x, eyePosition.y, eyePosition.z, 14, 0.15, 0.15, 0.15, 0.01);
        level.playSound(null, player.blockPosition(), SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 0.9F, 1.1F);
    }

    private static void castFrostNova(ServerLevel level, ServerPlayer player) {
        AABB area = player.getBoundingBox().inflate(4.0D, 2.0D, 4.0D);
        List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, area, entity -> entity != player && entity.isAlive());
        for (LivingEntity target : targets) {
            target.hurt(player.damageSources().magic(), 4.0F);
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 1));
        }
        Vec3 center = player.position().add(0.0D, 1.0D, 0.0D);
        level.sendParticles(ParticleTypes.SNOWFLAKE, center.x, center.y, center.z, 40, 2.2D, 0.5D, 2.2D, 0.02D);
        level.playSound(null, player.blockPosition(), SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 0.7F, 1.8F);
    }

    private static void castStormStrike(ServerLevel level, ServerPlayer player) {
        Vec3 strikePosition = resolveStrikePosition(player);
        LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(level);
        if (lightningBolt != null) {
            lightningBolt.moveTo(strikePosition.x, strikePosition.y, strikePosition.z);
            lightningBolt.setCause(player);
            level.addFreshEntity(lightningBolt);
        }
        level.sendParticles(ParticleTypes.ELECTRIC_SPARK, strikePosition.x, strikePosition.y + 1.0D, strikePosition.z, 26, 0.35D, 0.8D, 0.35D, 0.08D);
        level.playSound(null, player.blockPosition(), SoundEvents.TRIDENT_THUNDER, SoundSource.PLAYERS, 0.9F, 1.0F);
    }

    private static void castEarthGuard(ServerLevel level, ServerPlayer player) {
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 1));
        player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 200, 0));
        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 80, 0));
        Vec3 center = player.position().add(0.0D, 1.0D, 0.0D);
        level.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, center.x, center.y, center.z, 18, 0.45D, 0.6D, 0.45D, 0.02D);
        level.playSound(null, player.blockPosition(), SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.PLAYERS, 0.85F, 0.9F);
    }

    private static Vec3 resolveStrikePosition(ServerPlayer player) {
        HitResult hitResult = player.pick(24.0D, 0.0F, false);
        if (hitResult.getType() != HitResult.Type.MISS) {
            return hitResult.getLocation();
        }
        Vec3 fallback = player.position().add(player.getLookAngle().scale(12.0D));
        return new Vec3(fallback.x, Mth.clamp(fallback.y, player.level().getMinBuildHeight(), player.level().getMaxBuildHeight()), fallback.z);
    }

    public static void regenerateStableMana(ServerPlayer player) {
        ManaManager manaManager = ManaManager.of(player);
        double stableMana = manaManager.get(ManaType.STABLE);
        double stableManaMax = manaManager.get(ManaType.MAX_STABLE);
        if (stableMana < stableManaMax) {
            manaManager.add(ManaType.STABLE, 0.5D);
        }
        if (manaManager.isOverloaded() && manaManager.get(ManaType.STABLE) >= manaManager.get(ManaType.MAX_STABLE)) {
            manaManager.setOverloaded(false, -OVERLOAD_STABLE_PENALTY);
        }
    }
}
