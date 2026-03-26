package net.teamaurorisla.auroramagic.spell;

import java.util.Objects;

public record SpellGuiSnapshot(int equippedCount,
                               int candidateCount,
                               int currentTotalMana,
                               boolean overloadActive,
                               MomentumTier momentumTier,
                               long chantRemainingMillis) {

    public SpellGuiSnapshot {
        Objects.requireNonNull(momentumTier, "momentumTier");
    }

    public static SpellGuiSnapshot from(SpellCombatState combatState, SpellResourceState resourceState, long currentTime) {
        Objects.requireNonNull(combatState, "combatState");
        Objects.requireNonNull(resourceState, "resourceState");
        long chantRemainingMillis = combatState.chantingState()
                ? Math.max(combatState.chantingEndTime() - currentTime, 0L)
                : 0L;
        return new SpellGuiSnapshot(
                combatState.equippedSpellSlots().size(),
                combatState.candidateSpellSlots().size(),
                resourceState.totalMana(),
                resourceState.overloadLocked(),
                resourceState.momentumTier(),
                chantRemainingMillis
        );
    }
}
