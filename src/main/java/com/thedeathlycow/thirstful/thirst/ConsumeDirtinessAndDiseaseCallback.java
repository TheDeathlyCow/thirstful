package com.thedeathlycow.thirstful.thirst;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.config.common.WaterPollutionConfig;
import com.thedeathlycow.thirstful.item.ConsumeItemCallback;
import com.thedeathlycow.thirstful.item.component.PollutantComponent;
import com.thedeathlycow.thirstful.registry.TDataComponentTypes;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class ConsumeDirtinessAndDiseaseCallback implements ConsumeItemCallback {
    @Override
    public void onConsume(LivingEntity entity, ItemStack stack) {
        FoodComponent foodComponent = stack.get(DataComponentTypes.FOOD);
        if (foodComponent != null && !foodComponent.effects().isEmpty()) {
            return;
        }

        PollutantComponent pollutantComponent = stack.get(TDataComponentTypes.POLLUTANTS);
        if (pollutantComponent != null) {
            pollutantComponent.applyEffects(entity);
        }
    }
}