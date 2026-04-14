package net.teamaurorisla.auroramagic.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.teamaurorisla.auroramagic.registry.AMBlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ArcanePedestalBlockEntity extends BlockEntity {
    private static final String INVENTORY_TAG = "Inventory";

    private final ItemStackHandler itemHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            sendUpdates();
        }

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }
    };

    private LazyOptional<IItemHandler> topItemHandler = LazyOptional.of(() -> this.itemHandler);

    public ArcanePedestalBlockEntity(BlockPos pos, BlockState blockState) {
        super(AMBlockEntity.ARCANE_PEDESTAL.get(), pos, blockState);
    }

    public ItemStack getDisplayItem() {
        return itemHandler.getStackInSlot(0);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put(INVENTORY_TAG, itemHandler.serializeNBT());
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains(INVENTORY_TAG)) {
            itemHandler.deserializeNBT(tag.getCompound(INVENTORY_TAG));
            return;
        }

        // Backward compatibility for old saves that stored a single DisplayItem tag.
        if (tag.contains("DisplayItem")) {
            itemHandler.setStackInSlot(0, ItemStack.of(tag.getCompound("DisplayItem")));
        }
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER && side == Direction.UP) {
            return topItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        topItemHandler.invalidate();
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        topItemHandler = LazyOptional.of(() -> itemHandler);
    }

    private void sendUpdates() {
        if (this.level != null && !this.level.isClientSide) {
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
        }
    }
}
