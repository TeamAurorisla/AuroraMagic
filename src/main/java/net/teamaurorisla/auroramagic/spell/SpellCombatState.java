package net.teamaurorisla.auroramagic.spell;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

public record SpellCombatState(List<SpellDefinition> equippedSpellSlots,
                               List<SpellDefinition> candidateSpellSlots,
                               Optional<String> selectedSpellId,
                               boolean chantingState,
                               long chantingStartTime,
                               long chantingEndTime,
                               Random randomSource) {

    public SpellCombatState {
        equippedSpellSlots = List.copyOf(Objects.requireNonNull(equippedSpellSlots, "equippedSpellSlots"));
        candidateSpellSlots = List.copyOf(Objects.requireNonNull(candidateSpellSlots, "candidateSpellSlots"));
        selectedSpellId = selectedSpellId == null ? Optional.empty() : selectedSpellId;
        randomSource = Objects.requireNonNull(randomSource, "randomSource");
    }
}
