package com.thedeathlycow.thirstful.thirst;

import com.thedeathlycow.thirstful.item.ConsumeItemCallback;
import com.thedeathlycow.thirstful.item.component.PollutantComponent;
import com.thedeathlycow.thirstful.registry.TDataComponentTypes;
import com.thedeathlycow.thirstful.registry.TStatusEffects;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerConsumptionListener implements ConsumeItemCallback {
    @Override
    public void onConsume(ServerPlayerEntity player, ItemStack stack) {
        FoodComponent foodComponent = stack.get(DataComponentTypes.FOOD);
        if (foodComponent != null && !foodComponent.effects().isEmpty()) {
            return;
        }

        PollutantComponent pollutantComponent = stack.get(TDataComponentTypes.POLLUTANTS);
        if (pollutantComponent != null) {
            this.applyDisease(player, pollutantComponent);
            this.applyDirtiness(player, pollutantComponent);
        }
    }

    private void applyDisease(ServerPlayerEntity player, PollutantComponent component) {
        if (player.getRandom().nextFloat() < component.diseaseChance()) {
            player.addStatusEffect(new StatusEffectInstance(TStatusEffects.FEVER, 60 * 20));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 10 * 20));
        }
    }

    private void applyDirtiness(ServerPlayerEntity player, PollutantComponent component) {
        if (player.getRandom().nextFloat() < component.dirtiness()) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 60 * 20));
        }
    }
}