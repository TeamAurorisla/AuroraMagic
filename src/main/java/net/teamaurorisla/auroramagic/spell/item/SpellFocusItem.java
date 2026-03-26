package net.teamaurorisla.auroramagic.spell.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.teamaurorisla.auroramagic.spell.PlayableSpellCatalog;
import net.teamaurorisla.auroramagic.spell.SpellCastCostResult;
import net.teamaurorisla.auroramagic.spell.SpellCastOutcome;
import net.teamaurorisla.auroramagic.spell.SpellCastingService;
import net.teamaurorisla.auroramagic.spell.SpellDefinition;

public class SpellFocusItem extends Item {

    private final String spellId;

    public SpellFocusItem(String spellId, Properties properties) {
        super(properties);
        this.spellId = spellId;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (level.isClientSide()) {
            return InteractionResultHolder.sidedSuccess(itemStack, true);
        }
        if (!(player instanceof ServerPlayer serverPlayer)) {
            return InteractionResultHolder.pass(itemStack);
        }

        SpellDefinition spell = PlayableSpellCatalog.resolve(spellId);
        SpellCastOutcome outcome = SpellCastingService.cast((net.minecraft.server.level.ServerLevel) level, serverPlayer, spell);
        if (outcome.success()) {
            serverPlayer.getCooldowns().addCooldown(this, 12);
        }
        player.swing(hand, true);
        sendFeedback(serverPlayer, outcome);

        return outcome.success()
                ? InteractionResultHolder.consume(itemStack)
                : InteractionResultHolder.fail(itemStack);
    }

    private static void sendFeedback(ServerPlayer player, SpellCastOutcome outcome) {
        SpellCastCostResult costResult = outcome.costResult();
        if (costResult == SpellCastCostResult.NORMAL_CAST) {
            player.sendSystemMessage(Component.translatable("message.auroramagic.cast." + outcome.spell().spellId()).withStyle(ChatFormatting.GREEN));
            return;
        }
        if (costResult == SpellCastCostResult.OVERLOAD_CAST) {
            player.sendSystemMessage(Component.translatable("message.auroramagic.cast.overload", Component.translatable("message.auroramagic.cast." + outcome.spell().spellId())).withStyle(ChatFormatting.GOLD));
            return;
        }
        if (costResult == SpellCastCostResult.BLOCKED_CONTINUOUS_OVERLOAD) {
            player.sendSystemMessage(Component.translatable("message.auroramagic.cast.blocked_overload").withStyle(ChatFormatting.RED));
            return;
        }
        player.sendSystemMessage(Component.translatable("message.auroramagic.cast.insufficient_mana").withStyle(ChatFormatting.RED));
    }
}
