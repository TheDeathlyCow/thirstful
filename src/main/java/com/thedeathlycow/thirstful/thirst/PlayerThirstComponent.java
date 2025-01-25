package com.thedeathlycow.thirstful.thirst;

import com.thedeathlycow.thirstful.registry.TEntityComponents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.MathHelper;
import org.ladysnake.cca.api.v3.component.Component;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public class PlayerThirstComponent implements Component, ServerTickingComponent {
    private static final String THIRST_TICKS_KEY = "thirst_ticks";

    // 2 in game days
    private static final int MAX_THIRST_TICKS = 24_000;

    private final PlayerEntity player;

    private int thirstTicks;


    public PlayerThirstComponent(PlayerEntity player) {
        this.player = player;
    }

    public static PlayerThirstComponent get(PlayerEntity player) {
        return TEntityComponents.PLAYER_THIRST.get(player);
    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putInt(THIRST_TICKS_KEY, thirstTicks);
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.thirstTicks = nbtCompound.getInt(THIRST_TICKS_KEY);
    }

    public double getThirstScale() {
        return ((double) this.thirstTicks) / this.getMaxThirstTicks();
    }

    @Override
    public void serverTick() {
        this.increaseThirst();
    }

    public int getMaxThirstTicks() {
        return MAX_THIRST_TICKS;
    }

    private void increaseThirst() {
        this.thirstTicks++;
        this.thirstTicks = MathHelper.clamp(this.thirstTicks, 0, this.getMaxThirstTicks());
    }
}