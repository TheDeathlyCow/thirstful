package com.thedeathlycow.thirstful.thirst;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.registry.TEntityComponents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.ladysnake.cca.api.v3.component.Component;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public class PlayerThirstComponent implements Component, ServerTickingComponent {
    private static final String THIRST_TICKS_KEY = "thirst_ticks";

    // 2 in game days
    private static final int MAX_THIRST_TICKS = 24_000;

    private final PlayerEntity provider;

    private int thirstTicks;


    public PlayerThirstComponent(PlayerEntity provider) {
        this.provider = provider;
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

    public void addThirstTicks(int ticks) {
        this.thirstTicks = MathHelper.clamp(this.thirstTicks + ticks, 0, this.getMaxThirstTicks());
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
            World world = this.provider.getWorld();
            this.provider.damage(world.getDamageSources().generic(), 1.0f);
        }
    }

    public int getMaxThirstTicks() {
        return Thirstful.getConfig().thirst().maxThirstTicks();
    }

    public static boolean isThirstDamageEnabled() {
        return Thirstful.getConfig().thirst().enableThirstDamage();
    }
}