package net.teamaurorisla.auroramagic.spell;

import java.util.Objects;

public record SpellCombatResolution(SpellDefinition castSpell,
                                    SpellCombatState nextState) {

    public SpellCombatResolution {
        Objects.requireNonNull(castSpell, "castSpell");
        Objects.requireNonNull(nextState, "nextState");
    }
}
