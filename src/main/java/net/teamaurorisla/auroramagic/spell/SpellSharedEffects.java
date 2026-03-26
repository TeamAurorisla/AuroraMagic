package net.teamaurorisla.auroramagic.spell;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public final class SpellSharedEffects {

    private static final Map<SpellSchool, List<SharedEffectKey>> PHASE_ONE_DEFAULTS;

    static {
        EnumMap<SpellSchool, List<SharedEffectKey>> map = new EnumMap<>(SpellSchool.class);
        map.put(SpellSchool.BLAZE, List.of(SharedEffectKey.BLAZE_BURN_UP, SharedEffectKey.BLAZE_EMBER_RESIDUE));
        map.put(SpellSchool.FROST, List.of(SharedEffectKey.FROST_SLOW, SharedEffectKey.FROST_CRYSTALLIZE));
        map.put(SpellSchool.STORM, List.of(SharedEffectKey.STORM_CHARGE, SharedEffectKey.STORM_CONDUCT));
        map.put(SpellSchool.EARTH, List.of(SharedEffectKey.EARTH_FORTIFY, SharedEffectKey.EARTH_STAGGER));
        map.put(SpellSchool.DIVINE, List.of());
        PHASE_ONE_DEFAULTS = Map.copyOf(map);
    }

    private SpellSharedEffects() {
    }

    public static Map<SpellSchool, List<SharedEffectKey>> phaseOneDefaults() {
        return PHASE_ONE_DEFAULTS;
    }
}
