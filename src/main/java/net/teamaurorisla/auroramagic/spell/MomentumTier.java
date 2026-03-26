package net.teamaurorisla.auroramagic.spell;

public enum MomentumTier {
    LOW(1.0),
    MID(1.15),
    HIGH(1.3);

    private final double largeSpellMultiplier;

    MomentumTier(double largeSpellMultiplier) {
        this.largeSpellMultiplier = largeSpellMultiplier;
    }

    public double largeSpellMultiplier() {
        return largeSpellMultiplier;
    }
}
