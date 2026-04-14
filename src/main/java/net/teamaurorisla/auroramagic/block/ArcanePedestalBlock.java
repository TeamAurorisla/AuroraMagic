package net.teamaurorisla.auroramagic.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.teamaurorisla.auroramagic.block.entity.ArcanePedestalBlockEntity;
import org.jetbrains.annotations.Nullable;

public class ArcanePedestalBlock extends Block implements EntityBlock {

    public ArcanePedestalBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (!(blockEntity instanceof ArcanePedestalBlockEntity pedestal)) {
            return InteractionResult.PASS;
        }

        IItemHandler itemHandler = pedestal.getCapability(ForgeCapabilities.ITEM_HANDLER, Direction.UP).resolve().orElse(null);
        if (itemHandler == null) {
            return InteractionResult.PASS;
        }

        ItemStack itemInHand = player.getItemInHand(hand);

        // Sneak forces extraction even with an item in hand; empty hand can also extract.
        if (itemInHand.isEmpty() || player.isShiftKeyDown()) {
            ItemStack extracted = itemHandler.extractItem(0, 1, false);
            if (extracted.isEmpty()) {
                return InteractionResult.PASS;
            }
            if (!player.addItem(extracted)) {
                player.drop(extracted, false);
            }
            return InteractionResult.SUCCESS;
        }

        ItemStack toInsert = itemInHand.copy();
        toInsert.setCount(1);
        ItemStack remaining = itemHandler.insertItem(0, toInsert, false);
        if (remaining.isEmpty()) {
            itemInHand.shrink(1);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ArcanePedestalBlockEntity(pos, state);
    }

}
