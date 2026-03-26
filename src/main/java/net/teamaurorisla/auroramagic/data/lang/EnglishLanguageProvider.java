package net.teamaurorisla.auroramagic.data.lang;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import net.teamaurorisla.auroramagic.AuroraMagic;
import net.teamaurorisla.auroramagic.registry.AMBlock;
import net.teamaurorisla.auroramagic.registry.AMItem;
import net.teamaurorisla.auroramagic.spell.SpellSchool;

public class EnglishLanguageProvider extends LanguageProvider {

    public EnglishLanguageProvider(PackOutput output) {
        super(output, AuroraMagic.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        this.add(AMBlock.ARCANE_PEDESTAL.get(), "Arcane Pedestal");
        this.add(AMItem.BLAZE_FOCUS.get(), "Blaze Focus");
        this.add(AMItem.FROST_FOCUS.get(), "Frost Focus");
        this.add(AMItem.STORM_FOCUS.get(), "Storm Focus");
        this.add(AMItem.EARTH_FOCUS.get(), "Earth Focus");

        this.add(SpellSchool.BLAZE.translationKey(), "Blaze");
        this.add(SpellSchool.FROST.translationKey(), "Frost");
        this.add(SpellSchool.STORM.translationKey(), "Storm");
        this.add(SpellSchool.EARTH.translationKey(), "Earth");
        this.add(SpellSchool.DIVINE.translationKey(), "Divine");

        this.add("message.auroramagic.cast.blaze_burst", "Blaze Burst released!");
        this.add("message.auroramagic.cast.frost_nova", "Frost Nova released!");
        this.add("message.auroramagic.cast.storm_strike", "Storm Strike released!");
        this.add("message.auroramagic.cast.earth_guard", "Earth Guard released!");
        this.add("message.auroramagic.cast.overload", "Overload cast: %1$s");
        this.add("message.auroramagic.cast.blocked_overload", "You are overloaded and cannot overload-cast again yet!");
        this.add("message.auroramagic.cast.insufficient_mana", "Not enough mana to cast this spell.");
        this.add("message.auroramagic.mana.overload_recovered", "Your mana overload has recovered.");


        this.add("attribute.auroramagic.mana_efficiency", "Mana Efficiency");
        this.add("attribute.auroramagic.spell_power", "Spell Power");
        this.add("attribute.auroramagic.frost_affinity", "Frost Affinity");
        this.add("attribute.auroramagic.storm_affinity", "Storm Affinity");
        this.add("attribute.auroramagic.blaze_affinity", "Blaze Affinity");
        this.add("attribute.auroramagic.divine_affinity", "Divine Affinity");
        this.add("attribute.auroramagic.earth_affinity", "Earth Affinity");
        this.add("attribute.auroramagic.spell_resistance", "Spell Resistance");
        this.add("attribute.auroramagic.blaze_resistance", "Blaze Resistance");
        this.add("attribute.auroramagic.frost_resistance", "Frost Resistance");
        this.add("attribute.auroramagic.earth_resistance", "Earth Resistance");
        this.add("attribute.auroramagic.storm_resistance", "Storm Resistance");
        this.add("attribute.auroramagic.divine_resistance", "Divine Resistance");
        this.add("attribute.auroramagic.max_stable_mana", "Max Stable Mana");
        this.add("attribute.auroramagic.max_surge_mana", "Max Surge Mana");

        this.add("death.attack.auroramagic.damage_type.blaze_spell", "%1$s was killed by blaze spell damage");
        this.add("death.attack.auroramagic.damage_type.blaze_spell.player", "%1$s was killed by %2$s using blaze spell damage");
        this.add("death.attack.auroramagic.damage_type.blaze_spell_magical", "%1$s was killed by blaze spell magical damage");
        this.add("death.attack.auroramagic.damage_type.blaze_spell_magical.player", "%1$s was killed by %2$s using blaze spell magical damage");
        this.add("death.attack.auroramagic.damage_type.frost_spell", "%1$s was killed by frost spell damage");
        this.add("death.attack.auroramagic.damage_type.frost_spell.player", "%1$s was killed by %2$s using frost spell damage");
        this.add("death.attack.auroramagic.damage_type.frost_spell_magical", "%1$s was killed by frost spell magical damage");
        this.add("death.attack.auroramagic.damage_type.frost_spell_magical.player", "%1$s was killed by %2$s using frost spell magical damage");
        this.add("death.attack.auroramagic.damage_type.earth_spell", "%1$s was killed by earth spell damage");
        this.add("death.attack.auroramagic.damage_type.earth_spell.player", "%1$s was killed by %2$s using earth spell damage");
        this.add("death.attack.auroramagic.damage_type.earth_spell_magical", "%1$s was killed by earth spell magical damage");
        this.add("death.attack.auroramagic.damage_type.earth_spell_magical.player", "%1$s was killed by %2$s using earth spell magical damage");
        this.add("death.attack.auroramagic.damage_type.storm_spell", "%1$s was killed by storm spell damage");
        this.add("death.attack.auroramagic.damage_type.storm_spell.player", "%1$s was killed by %2$s using storm spell damage");
        this.add("death.attack.auroramagic.damage_type.storm_spell_magical", "%1$s was killed by storm spell magical damage");
        this.add("death.attack.auroramagic.damage_type.storm_spell_magical.player", "%1$s was killed by %2$s using storm spell magical damage");
        this.add("death.attack.auroramagic.damage_type.divine_spell", "%1$s was killed by divine spell damage");
        this.add("death.attack.auroramagic.damage_type.divine_spell.player", "%1$s was killed by %2$s using divine spell damage");
        this.add("death.attack.auroramagic.damage_type.divine_spell_magical", "%1$s was killed by divine spell magical damage");
        this.add("death.attack.auroramagic.damage_type.divine_spell_magical.player", "%1$s was killed by %2$s using divine spell magical damage");

    }
}
