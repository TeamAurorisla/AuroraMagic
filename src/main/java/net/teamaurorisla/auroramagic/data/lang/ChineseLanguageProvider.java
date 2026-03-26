package net.teamaurorisla.auroramagic.data.lang;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import net.teamaurorisla.auroramagic.AuroraMagic;
import net.teamaurorisla.auroramagic.registry.AMItem;
import net.teamaurorisla.auroramagic.spell.SpellSchool;

public class ChineseLanguageProvider extends LanguageProvider {

    public ChineseLanguageProvider(PackOutput output) {
        super(output, AuroraMagic.MODID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        this.add("block.auroramagic.arcane_pedestal", "基座");
        this.add(AMItem.BLAZE_FOCUS.get(), "炽焰焦点");
        this.add(AMItem.FROST_FOCUS.get(), "寒冰焦点");
        this.add(AMItem.STORM_FOCUS.get(), "风暴焦点");
        this.add(AMItem.EARTH_FOCUS.get(), "磐石焦点");

        this.add(SpellSchool.BLAZE.translationKey(), "炽焰");
        this.add(SpellSchool.FROST.translationKey(), "寒冰");
        this.add(SpellSchool.STORM.translationKey(), "风暴");
        this.add(SpellSchool.EARTH.translationKey(), "磐石");
        this.add(SpellSchool.DIVINE.translationKey(), "神圣");

        this.add("message.auroramagic.cast.blaze_burst", "炽焰爆发已释放！");
        this.add("message.auroramagic.cast.frost_nova", "寒冰新星已释放！");
        this.add("message.auroramagic.cast.storm_strike", "风暴打击已释放！");
        this.add("message.auroramagic.cast.earth_guard", "磐石护体已释放！");
        this.add("message.auroramagic.cast.overload", "超载施法：%1$s");
        this.add("message.auroramagic.cast.blocked_overload", "你正处于超载状态，暂时无法再次超载施法！");
        this.add("message.auroramagic.cast.insufficient_mana", "魔力不足，无法释放该法术。");
        this.add("message.auroramagic.mana.overload_recovered", "你的魔力超载已经恢复。");

        this.add("attribute.auroramagic.mana_efficiency", "魔导效能");
        this.add("attribute.auroramagic.spell_power", "法术强度");
        this.add("attribute.auroramagic.frost_affinity", "寒冰亲和");
        this.add("attribute.auroramagic.storm_affinity", "风暴亲和");
        this.add("attribute.auroramagic.blaze_affinity", "炽焰亲和");
        this.add("attribute.auroramagic.divine_affinity", "神圣亲和");
        this.add("attribute.auroramagic.earth_affinity", "磐石亲和");
        this.add("attribute.auroramagic.spell_resistance", "法术抗性");
        this.add("attribute.auroramagic.blaze_resistance", "炽焰抗性");
        this.add("attribute.auroramagic.frost_resistance", "寒冰抗性");
        this.add("attribute.auroramagic.earth_resistance", "磐石抗性");
        this.add("attribute.auroramagic.storm_resistance", "风暴抗性");
        this.add("attribute.auroramagic.divine_resistance", "神圣抗性");
        this.add("attribute.auroramagic.max_stable_mana", "最大稳态魔力");
        this.add("attribute.auroramagic.max_surge_mana", "最大涌态魔力");

        this.add("death.attack.auroramagic.damage_type.blaze_spell", "%1$s 被炽焰法术灼烧了");
        this.add("death.attack.auroramagic.damage_type.blaze_spell.player", "%1$s 在与 %2$s 的战斗中被炽焰法术灼烧了");
        this.add("death.attack.auroramagic.damage_type.blaze_spell_magical", "%1$s 被炽焰法术魔法伤害击杀");
        this.add("death.attack.auroramagic.damage_type.blaze_spell_magical.player", "%1$s 被 %2$s 的炽焰法术魔法伤害击杀");
        this.add("death.attack.auroramagic.damage_type.frost_spell", "%1$s 被寒冰法术伤害击杀");
        this.add("death.attack.auroramagic.damage_type.frost_spell.player", "%1$s 被 %2$s 的寒冰法术伤害击杀");
        this.add("death.attack.auroramagic.damage_type.frost_spell_magical", "%1$s 被寒冰法术魔法伤害击杀");
        this.add("death.attack.auroramagic.damage_type.frost_spell_magical.player", "%1$s 被 %2$s 的寒冰法术魔法伤害击杀");
        this.add("death.attack.auroramagic.damage_type.earth_spell", "%1$s 被磐石法术伤害击杀");
        this.add("death.attack.auroramagic.damage_type.earth_spell.player", "%1$s 被 %2$s 的磐石法术伤害击杀");
        this.add("death.attack.auroramagic.damage_type.earth_spell_magical", "%1$s 被磐石法术魔法伤害击杀");
        this.add("death.attack.auroramagic.damage_type.earth_spell_magical.player", "%1$s 被 %2$s 的磐石法术魔法伤害击杀");
        this.add("death.attack.auroramagic.damage_type.storm_spell", "%1$s 被风暴法术伤害击杀");
        this.add("death.attack.auroramagic.damage_type.storm_spell.player", "%1$s 被 %2$s 的风暴法术伤害击杀");
        this.add("death.attack.auroramagic.damage_type.storm_spell_magical", "%1$s 被风暴法术魔法伤害击杀");
        this.add("death.attack.auroramagic.damage_type.storm_spell_magical.player", "%1$s 被 %2$s 的风暴法术魔法伤害击杀");
        this.add("death.attack.auroramagic.damage_type.divine_spell", "%1$s 被神圣法术伤害击杀");
        this.add("death.attack.auroramagic.damage_type.divine_spell.player", "%1$s 被 %2$s 的神圣法术伤害击杀");
        this.add("death.attack.auroramagic.damage_type.divine_spell_magical", "%1$s 被神圣法术魔法伤害击杀");
        this.add("death.attack.auroramagic.damage_type.divine_spell_magical.player", "%1$s 被 %2$s 的神圣法术魔法伤害击杀");
    }
}
