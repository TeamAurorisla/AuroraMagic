package net.teamaurorisla.auroramagic.spell;

public class SpellResourceState {

    private static final int MAX_OVERLOAD_DELTA = 4;
    private static final int STABLE_MAX_REDUCTION_ON_OVERLOAD = 4;

    private int stableManaMax;
    private int stableManaCurrent;
    private int surgeManaMax;
    private int surgeManaCurrent;
    private int overloadPenalty;
    private boolean overloadLocked;
    private MomentumTier momentumTier;

    public SpellResourceState(int stableManaMax,
                              int stableManaCurrent,
                              int surgeManaMax,
                              int surgeManaCurrent,
                              MomentumTier momentumTier) {
        this.stableManaMax = Math.max(stableManaMax, 0);
        this.surgeManaMax = Math.max(surgeManaMax, 0);
        this.stableManaCurrent = clamp(stableManaCurrent, 0, this.stableManaMax);
        this.surgeManaCurrent = clamp(surgeManaCurrent, 0, this.surgeManaMax);
        this.overloadPenalty = 0;
        this.overloadLocked = false;
        this.momentumTier = momentumTier == null ? MomentumTier.LOW : momentumTier;
    }

    public SpellCastCostResult payCost(int baseManaCost) {
        if (baseManaCost < 0) {
            throw new IllegalArgumentException("baseManaCost must be >= 0");
        }
        int total = totalMana();
        if (baseManaCost <= total) {
            consume(baseManaCost);
            return SpellCastCostResult.NORMAL_CAST;
        }
        if (overloadLocked) {
            return SpellCastCostResult.BLOCKED_CONTINUOUS_OVERLOAD;
        }
        if (baseManaCost <= total + MAX_OVERLOAD_DELTA) {
            consume(total);
            applyOverloadPenalty();
            overloadLocked = true;
            return SpellCastCostResult.OVERLOAD_CAST;
        }
        return SpellCastCostResult.INSUFFICIENT_MANA;
    }

    public void recoverOverload() {
        if (overloadPenalty > 0) {
            stableManaMax += overloadPenalty;
            overloadPenalty = 0;
        }
        overloadLocked = false;
    }

    public void resetForTesting(int stableManaMax, int stableManaCurrent, int surgeManaMax, int surgeManaCurrent) {
        this.stableManaMax = Math.max(stableManaMax, 0);
        this.surgeManaMax = Math.max(surgeManaMax, 0);
        this.stableManaCurrent = clamp(stableManaCurrent, 0, this.stableManaMax);
        this.surgeManaCurrent = clamp(surgeManaCurrent, 0, this.surgeManaMax);
    }

    public int totalMana() {
        return stableManaCurrent + surgeManaCurrent;
    }

    public int stableManaMax() {
        return stableManaMax;
    }

    public int stableManaCurrent() {
        return stableManaCurrent;
    }

    public int surgeManaMax() {
        return surgeManaMax;
    }

    public int surgeManaCurrent() {
        return surgeManaCurrent;
    }

    public int overloadPenalty() {
        return overloadPenalty;
    }

    public boolean overloadLocked() {
        return overloadLocked;
    }

    public MomentumTier momentumTier() {
        return momentumTier;
    }

    public void setMomentumTier(MomentumTier momentumTier) {
        this.momentumTier = momentumTier == null ? MomentumTier.LOW : momentumTier;
    }

    private void applyOverloadPenalty() {
        if (overloadPenalty == 0) {
            overloadPenalty = STABLE_MAX_REDUCTION_ON_OVERLOAD;
            stableManaMax = Math.max(stableManaMax - STABLE_MAX_REDUCTION_ON_OVERLOAD, 0);
            stableManaCurrent = Math.min(stableManaCurrent, stableManaMax);
        }
    }

    private void consume(int amount) {
        int remaining = amount;
        int consumeSurge = Math.min(surgeManaCurrent, remaining);
        surgeManaCurrent -= consumeSurge;
        remaining -= consumeSurge;
        if (remaining > 0) {
            stableManaCurrent = Math.max(stableManaCurrent - remaining, 0);
        }
    }

    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(value, max));
    }
}
