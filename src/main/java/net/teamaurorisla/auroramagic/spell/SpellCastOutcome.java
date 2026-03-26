package net.teamaurorisla.auroramagic.spell;

public record SpellCastOutcome(SpellDefinition spell,
                               SpellCastCostResult costResult) {

    public boolean success() {
        return costResult == SpellCastCostResult.NORMAL_CAST || costResult == SpellCastCostResult.OVERLOAD_CAST;
    }

    public boolean overloadCast() {
        return costResult == SpellCastCostResult.OVERLOAD_CAST;
    }
}
