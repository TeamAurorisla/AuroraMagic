package net.teamaurorisla.auroramagic.spell;

import java.util.List;
import java.util.Map;

public record SpellDefinition(String spellId,
                              SpellSchool school,
                              SpellType type,
                              SpellRarity rarity,
                              SpellCastMode castMode,
                              SpellVisibility visibility,
                              int baseManaCost,
                              double powerMultiplier,
                              Map<String, Double> baseStats,
                              List<SharedEffectKey> sharedEffects) {

    private static final int LARGE_SPELL_MINIMUM_COST = 18;

    public SpellDefinition {
        if (spellId == null || spellId.isBlank()) {
            throw new IllegalArgumentException("spellId must not be blank");
        }
        if (school == null || type == null || rarity == null || castMode == null || visibility == null) {
            throw new IllegalArgumentException("spell enum fields must not be null");
        }
        if (baseManaCost < 0) {
            throw new IllegalArgumentException("baseManaCost must be >= 0");
        }
        if (powerMultiplier <= 0.0) {
            throw new IllegalArgumentException("powerMultiplier must be > 0");
        }
        baseStats = baseStats == null ? Map.of() : Map.copyOf(baseStats);
        sharedEffects = sharedEffects == null ? List.of() : List.copyOf(sharedEffects);
    }

    public boolean isLargeSpell() {
        return baseManaCost >= LARGE_SPELL_MINIMUM_COST;
    }

    public boolean canEquipInSpellSlot() {
        return rarity != SpellRarity.LEGENDARY;
    }
}
