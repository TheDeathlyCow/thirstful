package com.thedeathlycow.thirstful.block.entity;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.item.component.PollutantComponent;
import com.thedeathlycow.thirstful.registry.TBlockEntityTypes;
import com.thedeathlycow.thirstful.registry.TDataComponentTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class PotionCauldronBlockEntity extends BlockEntity {
    private static final String POTION_CONTENTS_KEY = "potion_contents";
    private static final String POLLUTANTS_KEY = "pollutants";
    private PotionContentsComponent potionContents = PotionContentsComponent.DEFAULT;
    private PollutantComponent pollutants = PollutantComponent.DEFAULT;

    public PotionCauldronBlockEntity(BlockPos pos, BlockState state) {
        super(TBlockEntityTypes.POTION_CAULDRON, pos, state);
    }

    public ItemStack createFilledPotion(Item itemType) {
        var stack = new ItemStack(itemType);

        stack.set(DataComponentTypes.POTION_CONTENTS, this.potionContents);
        stack.set(TDataComponentTypes.POLLUTANTS, this.pollutants);

        return stack;
    }

    @Override
    @Nullable
    public Object getRenderData() {
        return this.potionContents.getColor();
    }

    public PotionContentsComponent getPotionContents() {
        return potionContents;
    }

    public void setContents(PotionContentsComponent potionContents, PollutantComponent pollutants) {
        this.potionContents = potionContents;
        this.pollutants = pollutants;
        this.updateListeners();
    }

    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        var nbt = new NbtCompound();
        nbt.put(
                POTION_CONTENTS_KEY,
                PotionContentsComponent.CODEC
                        .encodeStart(registryLookup.getOps(NbtOps.INSTANCE), this.potionContents)
                        .getOrThrow()
        );
        return nbt;
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        if (!this.potionContents.equals(PotionContentsComponent.DEFAULT)) {
            nbt.put(
                    POTION_CONTENTS_KEY,
                    PotionContentsComponent.CODEC
                            .encodeStart(registryLookup.getOps(NbtOps.INSTANCE), this.potionContents)
                            .getOrThrow()
            );
        }

        if (!this.pollutants.equals(PollutantComponent.DEFAULT)) {
            nbt.put(
                    POLLUTANTS_KEY,
                    PollutantComponent.CODEC
                            .encodeStart(registryLookup.getOps(NbtOps.INSTANCE), this.pollutants)
                            .getOrThrow()
            );
        }
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        if (nbt.contains(POTION_CONTENTS_KEY)) {
            PotionContentsComponent.CODEC
                    .parse(registryLookup.getOps(NbtOps.INSTANCE), nbt.get(POTION_CONTENTS_KEY))
                    .resultOrPartial(potionContents -> Thirstful.LOGGER.error("Failed to parse potion contents: '{}'", potionContents))
                    .ifPresent(potionContents -> this.potionContents = potionContents);
        }

        if (nbt.contains(POLLUTANTS_KEY)) {
            PollutantComponent.CODEC
                    .parse(registryLookup.getOps(NbtOps.INSTANCE), nbt.get(POLLUTANTS_KEY))
                    .resultOrPartial(pollutants -> Thirstful.LOGGER.error("Failed to parse pollutants: '{}'", pollutants))
                    .ifPresent(pollutants -> this.pollutants = pollutants);
        }

        this.updateListeners();
    }

    @Override
    protected void readComponents(BlockEntity.ComponentsAccess components) {
        super.readComponents(components);
        this.potionContents = components.getOrDefault(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT);
        this.pollutants = components.getOrDefault(TDataComponentTypes.POLLUTANTS, PollutantComponent.DEFAULT);
    }

    @Override
    protected void addComponents(ComponentMap.Builder componentMapBuilder) {
        super.addComponents(componentMapBuilder);
        componentMapBuilder.add(DataComponentTypes.POTION_CONTENTS, this.potionContents);
        componentMapBuilder.add(TDataComponentTypes.POLLUTANTS, this.pollutants);
    }

    private void updateListeners() {
        if (this.world != null) {
            this.markDirty();
            this.world.updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), Block.NOTIFY_ALL);
        }
    }
}