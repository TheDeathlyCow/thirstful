package com.thedeathlycow.thirstful.thirst;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.registry.TCardinalComponents;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.ladysnake.cca.api.v3.component.Component;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public class PlayerThirstComponent implements Component, ServerTickingComponent, AutoSyncedComponent {
    private static final String THIRST_TICKS_KEY = "thirst_ticks";

    private final Player provider;

    private int thirstTicks;

    public PlayerThirstComponent(Player provider) {
        this.provider = provider;
    }

    public static PlayerThirstComponent get(Player player) {
        return TCardinalComponents.PLAYER_THIRST.get(player);
    }

    @Override
    public void readFromNbt(CompoundTag nbtCompound, HolderLookup.Provider wrapperLookup) {
        this.thirstTicks = nbtCompound.getInt(THIRST_TICKS_KEY);
    }

    @Override
    public void writeToNbt(CompoundTag nbtCompound, HolderLookup.Provider wrapperLookup) {
        nbtCompound.putInt(THIRST_TICKS_KEY, thirstTicks);
    }

    @Override
    public void writeSyncPacket(RegistryFriendlyByteBuf buf, ServerPlayer recipient) {
        buf.writeVarInt(this.thirstTicks);
    }

    @Override
    public void applySyncPacket(RegistryFriendlyByteBuf buf) {
        this.thirstTicks = buf.readVarInt();
    }

    @Override
    public boolean shouldSyncWith(ServerPlayer player) {
        return this.provider == player;
    }

    @Override
    public void serverTick() {
        this.addThirstTicks(1);

        if (isThirstDamageEnabled() && this.getThirstTicks() >= this.getMaxThirstTicks()) {
            Level world = this.provider.level();
            this.provider.hurt(world.damageSources().generic(), 1.0f);
        }
    }

    public void addThirstTicks(int ticks) {
        this.setThirstTicks(this.thirstTicks + ticks);
    }

    public void removeThirstTicks(int ticks) {
        this.addThirstTicks(-ticks);
    }

    public int getThirstTicks() {
        return this.thirstTicks;
    }

    private void setThirstTicks(int value) {
        value = Mth.clamp(value, 0, this.getMaxThirstTicks());

        if (this.thirstTicks != value) {
            this.thirstTicks = value;
            TCardinalComponents.PLAYER_THIRST.sync(this.provider);
        }
    }

    public double getThirstScale() {
        return ((double) this.thirstTicks) / this.getMaxThirstTicks();
    }

    public float getThirstScaleAsFloat() {
        return ((float) this.thirstTicks) / this.getMaxThirstTicks();
    }

    public int getMaxThirstTicks() {
        return Thirstful.getConfig().thirst().maxThirstTicks();
    }

    public static boolean isThirstDamageEnabled() {
        return Thirstful.getConfig().thirst().enableThirstDamage();
    }
}