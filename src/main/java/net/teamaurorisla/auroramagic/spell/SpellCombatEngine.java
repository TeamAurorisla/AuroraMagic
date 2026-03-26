package net.teamaurorisla.auroramagic.spell;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

public final class SpellCombatEngine {

    private static final int EQUIPPED_SLOTS = 8;
    private static final int CANDIDATE_SLOTS = 4;
    private static final long CHANT_DURATION_MILLIS = 1_000L;

    private SpellCombatEngine() {
    }

    public static SpellCombatState startBattle(List<SpellDefinition> equippedSpells, Random random) {
        Objects.requireNonNull(equippedSpells, "equippedSpells");
        Objects.requireNonNull(random, "random");
        validateEquippedSpells(equippedSpells);

        List<SpellDefinition> equippedCopy = List.copyOf(equippedSpells);
        List<SpellDefinition> candidates = drawDistinctCandidates(equippedCopy, List.of(), random, CANDIDATE_SLOTS);
        return new SpellCombatState(equippedCopy, candidates, Optional.empty(), false, 0L, 0L, random);
    }

    public static SpellCombatState beginSelection(SpellCombatState state, String selectedSpellId, long selectionStartTime) {
        Objects.requireNonNull(state, "state");
        if (selectedSpellId == null || selectedSpellId.isBlank()) {
            throw new IllegalArgumentException("selectedSpellId must not be blank");
        }
        boolean inCandidates = state.candidateSpellSlots().stream().anyMatch(spell -> spell.spellId().equals(selectedSpellId));
        if (!inCandidates) {
            throw new IllegalArgumentException("selected spell must exist in candidate slots");
        }
        return new SpellCombatState(
                state.equippedSpellSlots(),
                state.candidateSpellSlots(),
                Optional.of(selectedSpellId),
                true,
                selectionStartTime,
                selectionStartTime + CHANT_DURATION_MILLIS,
                state.randomSource()
        );
    }

    public static Optional<SpellCombatResolution> tryResolve(SpellCombatState state, long currentTime) {
        Objects.requireNonNull(state, "state");
        if (!state.chantingState() || state.selectedSpellId().isEmpty() || currentTime < state.chantingEndTime()) {
            return Optional.empty();
        }

        SpellDefinition selectedSpell = state.candidateSpellSlots().stream()
                .filter(spell -> spell.spellId().equals(state.selectedSpellId().orElseThrow()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("selected spell missing from candidate slots"));

        List<SpellDefinition> nextCandidates = new ArrayList<>(state.candidateSpellSlots());
        nextCandidates.removeIf(spell -> spell.spellId().equals(selectedSpell.spellId()));

        List<SpellDefinition> replenished = drawDistinctCandidates(
                state.equippedSpellSlots(),
                nextCandidates,
                state.randomSource(),
                1
        );
        nextCandidates.addAll(replenished);

        SpellCombatState nextState = new SpellCombatState(
                state.equippedSpellSlots(),
                nextCandidates,
                Optional.empty(),
                false,
                0L,
                0L,
                state.randomSource()
        );
        return Optional.of(new SpellCombatResolution(selectedSpell, nextState));
    }

    private static List<SpellDefinition> drawDistinctCandidates(List<SpellDefinition> equippedSpells,
                                                                List<SpellDefinition> existingCandidates,
                                                                Random random,
                                                                int count) {
        List<SpellDefinition> available = new ArrayList<>(equippedSpells);
        Set<String> excludedIds = new HashSet<>();
        for (SpellDefinition spell : existingCandidates) {
            excludedIds.add(spell.spellId());
        }
        available.removeIf(spell -> excludedIds.contains(spell.spellId()));

        List<SpellDefinition> drawn = new ArrayList<>();
        while (drawn.size() < count && !available.isEmpty()) {
            int index = random.nextInt(available.size());
            drawn.add(available.remove(index));
        }
        return drawn;
    }

    private static void validateEquippedSpells(List<SpellDefinition> equippedSpells) {
        if (equippedSpells.size() != EQUIPPED_SLOTS) {
            throw new IllegalArgumentException("phase one requires exactly 8 equipped spells");
        }

        Set<String> spellIds = new HashSet<>();
        for (SpellDefinition spell : equippedSpells) {
            if (spell == null) {
                throw new IllegalArgumentException("equipped spell must not be null");
            }
            if (!spell.canEquipInSpellSlot()) {
                throw new IllegalArgumentException("legendary spells cannot be equipped in spell slots");
            }
            if (!spellIds.add(spell.spellId())) {
                throw new IllegalArgumentException("equipped spells must be unique");
            }
        }
    }
}
