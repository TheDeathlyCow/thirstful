package com.thedeathlycow.thirstful.block.entity;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.block.PollutedWaterCauldronBlock;
import com.thedeathlycow.thirstful.item.component.PollutantComponent;
import com.thedeathlycow.thirstful.registry.TBlockEntityTypes;
import com.thedeathlycow.thirstful.registry.TDataComponentTypes;
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

public class PollutedWaterCauldronBlockEntity extends BlockEntity {
    private static final String POLLUTANTS_KEY = "pollutants";
    private PollutantComponent pollutants = PollutantComponent.DEFAULT;

    public PollutedWaterCauldronBlockEntity(BlockPos pos, BlockState state) {
        super(TBlockEntityTypes.POLLUTED_WATER_CAULDRON, pos, state);
    }

    public PollutantComponent getPollutants() {
        return pollutants;
    }

    public void mixContents(PollutantComponent pollutants) {
        this.setContents(pollutants);
    }

    public void setContents(PollutantComponent pollutants) {
        this.pollutants = this.pollutants.mixWith(pollutants);
        this.markDirty();
    }

    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
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
        if (nbt.contains(POLLUTANTS_KEY)) {
            PollutantComponent.CODEC
                    .parse(registryLookup.getOps(NbtOps.INSTANCE), nbt.get(POLLUTANTS_KEY))
                    .resultOrPartial(pollutants -> Thirstful.LOGGER.error("Failed to parse pollutants: '{}'", pollutants))
                    .ifPresent(pollutants -> this.pollutants = pollutants);
        }
    }

    @Override
    protected void readComponents(BlockEntity.ComponentsAccess components) {
        super.readComponents(components);
        this.pollutants = components.getOrDefault(TDataComponentTypes.POLLUTANTS, PollutantComponent.DEFAULT);
    }

    @Override
    protected void addComponents(ComponentMap.Builder componentMapBuilder) {
        super.addComponents(componentMapBuilder);
        componentMapBuilder.add(TDataComponentTypes.POLLUTANTS, this.pollutants);
    }
}