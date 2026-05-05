package com.thedeathlycow.thirstful.thirst;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.registry.TCardinalComponents;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.level.Level;
import org.ladysnake.cca.api.v3.component.Component;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public class PlayerThirstComponent implements Component, ServerTickingComponent, AutoSyncedComponent {
    private static final String THIRST_TICKS_KEY = "thirst_ticks";
    private static final String THIRST_LEVEL_KEY = "thirst_level";
    private static final double MAX_THIRST = 10.0;

    private final Player provider;

    private double thirstLevel = MAX_THIRST;

    public PlayerThirstComponent(Player provider) {
        this.provider = provider;
    }

    public static PlayerThirstComponent get(Player player) {
        return TCardinalComponents.PLAYER_THIRST.get(player);
    }

    @Override
    public void readFromNbt(CompoundTag nbtCompound, HolderLookup.Provider wrapperLookup) {
        this.thirstLevel = nbtCompound.getInt(THIRST_LEVEL_KEY);
    }

    @Override
    public void writeToNbt(CompoundTag nbtCompound, HolderLookup.Provider wrapperLookup) {
        nbtCompound.putDouble(THIRST_LEVEL_KEY, thirstLevel);
    }

    @Override
    public void writeSyncPacket(RegistryFriendlyByteBuf buf, ServerPlayer recipient) {
        buf.writeDouble(this.thirstLevel);
    }

    @Override
    public void applySyncPacket(RegistryFriendlyByteBuf buf) {
        this.thirstLevel = buf.readDouble();
    }

    @Override
    public boolean shouldSyncWith(ServerPlayer player) {
        return this.provider == player;
    }

    @Override
    public void serverTick() {
        if (!this.canBeThirsty()) {
            return;
        }

        if (isThirstDamageEnabled() && this.isDehydrated()) {
            Level world = this.provider.level();
            this.provider.hurt(world.damageSources().generic(), 1.0f);
        }
    }

    public void addThirstLevel(double value) {
        this.setThirstLevel(this.thirstLevel + value);
    }

    public void removeThirstLevel(double value) {
        this.setThirstLevel(this.thirstLevel - value);
    }

    public double getThirstLevel() {
        return this.thirstLevel;
    }

    public boolean canBeThirsty() {
        return !this.provider.isCreative();
    }

    public boolean isDehydrated() {
        return this.thirstLevel <= 0;
    }

    private void setThirstLevel(double value) {
        value = Math.clamp(value, 0, this.getMaxThirstTicks());

        if (this.thirstLevel != value) {
            this.thirstLevel = value;
            TCardinalComponents.PLAYER_THIRST.sync(this.provider);
        }
    }

    private void reduceHunger(int amount) {
        Difficulty difficulty = this.provider.level().getDifficulty();
        FoodData food = this.provider.getFoodData();

        if (food.getSaturationLevel() > 0.0f) {
            food.setSaturation(Math.max(food.getSaturationLevel() - amount, 0.0f));
        } else if (difficulty != Difficulty.PEACEFUL) {
            food.setFoodLevel(Math.max(food.getFoodLevel() - amount, 0));
        }
    }

    public double getThirstScale() {
        return this.thirstLevel / this.getMaxThirstTicks();
    }

    public double getMaxThirstTicks() {
        return MAX_THIRST;
    }

    public static boolean isThirstDamageEnabled() {
        return Thirstful.getConfig().thirst().enableThirstDamage();
    }
}