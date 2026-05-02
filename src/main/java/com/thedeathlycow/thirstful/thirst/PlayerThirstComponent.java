package com.thedeathlycow.thirstful.thirst;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.registry.TEntityComponents;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.ladysnake.cca.api.v3.component.Component;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public class PlayerThirstComponent implements Component, ServerTickingComponent {
    private static final String THIRST_TICKS_KEY = "thirst_ticks";

    // 2 in game days
    private static final int MAX_THIRST_TICKS = 24_000;

    private final Player provider;

    private int thirstTicks;


    public PlayerThirstComponent(Player provider) {
        this.provider = provider;
    }

    public static PlayerThirstComponent get(Player player) {
        return TEntityComponents.PLAYER_THIRST.get(player);
    }

    @Override
    public void readFromNbt(CompoundTag nbtCompound, HolderLookup.Provider wrapperLookup) {
        nbtCompound.putInt(THIRST_TICKS_KEY, thirstTicks);
    }

    @Override
    public void writeToNbt(CompoundTag nbtCompound, HolderLookup.Provider wrapperLookup) {
        this.thirstTicks = nbtCompound.getInt(THIRST_TICKS_KEY);
    }

    public void addThirstTicks(int ticks) {
        this.thirstTicks = Mth.clamp(this.thirstTicks + ticks, 0, this.getMaxThirstTicks());
    }

    public void removeThirstTicks(int ticks) {
        this.addThirstTicks(-ticks);
    }

    public int getThirstTicks() {
        return this.thirstTicks;
    }

    public double getThirstScale() {
        return ((double) this.thirstTicks) / this.getMaxThirstTicks();
    }

    public float getThirstScaleAsFloat() {
        return ((float) this.thirstTicks) / this.getMaxThirstTicks();
    }

    @Override
    public void serverTick() {
        this.addThirstTicks(1);

        if (isThirstDamageEnabled() && this.thirstTicks == this.getMaxThirstTicks()) {
            Level world = this.provider.level();
            this.provider.hurt(world.damageSources().generic(), 1.0f);
        }
    }

    public int getMaxThirstTicks() {
        return Thirstful.getConfig().thirst().maxThirstTicks();
    }

    public static boolean isThirstDamageEnabled() {
        return Thirstful.getConfig().thirst().enableThirstDamage();
    }
}