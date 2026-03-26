package net.teamaurorisla.auroramagic.spell;

public enum SpellSchool {
    BLAZE("blaze"),
    FROST("frost"),
    STORM("storm"),
    EARTH("earth"),
    DIVINE("divine");

    private final String id;

    SpellSchool(String id) {
        this.id = id;
    }

    public String translationKey() {
        return "spell_school.auroramagic." + id;
    }
}
