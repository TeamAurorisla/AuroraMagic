package net.teamaurorisla.auroramagic.spell;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class PlayableSpellCatalog {

    private static final List<SpellDefinition> STARTER_SPELLS = List.of(
            new SpellDefinition(
                    "blaze_burst",
                    SpellSchool.BLAZE,
                    SpellType.OFFENSIVE,
                    SpellRarity.COMMON,
                    SpellCastMode.INSTANT,
                    SpellVisibility.PUBLIC,
                    4,
                    1.0,
                    Map.of("damage", 5.0),
                    List.of(SharedEffectKey.BLAZE_BURN_UP)
            ),
            new SpellDefinition(
                    "frost_nova",
                    SpellSchool.FROST,
                    SpellType.CONTROL,
                    SpellRarity.COMMON,
                    SpellCastMode.INSTANT,
                    SpellVisibility.PUBLIC,
                    5,
                    1.0,
                    Map.of("damage", 4.0),
                    List.of(SharedEffectKey.FROST_SLOW)
            ),
            new SpellDefinition(
                    "storm_strike",
                    SpellSchool.STORM,
                    SpellType.OFFENSIVE,
                    SpellRarity.RARE,
                    SpellCastMode.INSTANT,
                    SpellVisibility.PUBLIC,
                    6,
                    1.15,
                    Map.of("damage", 7.0),
                    List.of(SharedEffectKey.STORM_CHARGE)
            ),
            new SpellDefinition(
                    "earth_guard",
                    SpellSchool.EARTH,
                    SpellType.SUPPORT,
                    SpellRarity.COMMON,
                    SpellCastMode.INSTANT,
                    SpellVisibility.PUBLIC,
                    4,
                    1.0,
                    Map.of("shield", 1.0),
                    List.of(SharedEffectKey.EARTH_FORTIFY)
            )
    );

    private static final Map<String, SpellDefinition> SPELLS_BY_ID = STARTER_SPELLS.stream()
            .collect(Collectors.toUnmodifiableMap(SpellDefinition::spellId, Function.identity()));

    private PlayableSpellCatalog() {
    }

    public static List<SpellDefinition> starterSpells() {
        return STARTER_SPELLS;
    }

    public static SpellDefinition resolve(String spellId) {
        SpellDefinition definition = SPELLS_BY_ID.get(spellId);
        if (definition == null) {
            throw new IllegalArgumentException("Unknown playable spell: " + spellId);
        }
        return definition;
    }
}
