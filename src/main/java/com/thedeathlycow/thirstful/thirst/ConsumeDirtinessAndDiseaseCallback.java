package com.thedeathlycow.thirstful.thirst;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.config.common.WaterPollutionConfig;
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

public class ConsumeDirtinessAndDiseaseCallback implements ConsumeItemCallback {
    @Override
    public void onConsume(ServerPlayerEntity player, ItemStack stack) {
        FoodComponent foodComponent = stack.get(DataComponentTypes.FOOD);
        if (foodComponent != null && !foodComponent.effects().isEmpty()) {
            return;
        }

        PollutantComponent pollutantComponent = stack.get(TDataComponentTypes.POLLUTANTS);
        if (pollutantComponent != null) {
            WaterPollutionConfig config = Thirstful.getConfig().common().waterPollution();

            this.applyDisease(player, pollutantComponent, config);
            this.applyDirtiness(player, pollutantComponent, config);
        }
    }

    private void applyDisease(ServerPlayerEntity player, PollutantComponent component, WaterPollutionConfig config) {
        if (player.getRandom().nextFloat() <= component.diseaseChance(config)) {
            player.addStatusEffect(new StatusEffectInstance(TStatusEffects.FEVER, 60 * 20));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 10 * 20));
        }
    }

    private void applyDirtiness(ServerPlayerEntity player, PollutantComponent component, WaterPollutionConfig config) {
        if (player.getRandom().nextFloat() <= component.dirtiness(config)) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 60 * 20));
        }
    }
}