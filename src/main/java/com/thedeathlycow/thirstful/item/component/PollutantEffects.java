package com.thedeathlycow.thirstful.item.component;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.config.common.WaterPollutionConfig;
import com.thedeathlycow.thirstful.item.consume.ApplyStatusEffectConsumeEffect;
import com.thedeathlycow.thirstful.item.consume.ConsumePollutionEffect;
import com.thedeathlycow.thirstful.registry.TDataComponentTypes;
import com.thedeathlycow.thirstful.registry.TStatusEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Consumer;

public final class PollutantEffects {
    private static final int LONG_EFFECT_TIME = 60 * 20;
    private static final int SHORT_EFFECT_TIME = 10 * 20;

    public static final List<ConsumePollutionEffect> DEFAULT_DIRTINESS = List.of(
            new ApplyStatusEffectConsumeEffect(
                    new StatusEffectInstance(StatusEffects.HUNGER, LONG_EFFECT_TIME),
                    0.5f
            )
    );

    public static final List<ConsumePollutionEffect> DEFAULT_DISEASE = List.of(
            new ApplyStatusEffectConsumeEffect(
                    new StatusEffectInstance(StatusEffects.POISON, SHORT_EFFECT_TIME),
                    0.5f
            ),
            new ApplyStatusEffectConsumeEffect(
                    new StatusEffectInstance(TStatusEffects.FEVER, LONG_EFFECT_TIME),
                    0.5f
            )
    );

    public static void onConsume(LivingEntity entity, ItemStack stack) {
        PollutantComponent pollutantComponent = stack.get(TDataComponentTypes.POLLUTANTS);
        World world = entity.getWorld();
        if (!world.isClient() && pollutantComponent != null) {
            WaterPollutionConfig config = Thirstful.getConfig().waterPollution();
            Consumer<ConsumePollutionEffect> effectApplier = effect -> effect.apply(world, entity, stack);

            if (pollutantComponent.checkedDirty(config)) {
                stack.getOrDefault(
                        TDataComponentTypes.DIRTINESS_EFFECTS,
                        DEFAULT_DIRTINESS
                ).forEach(effectApplier);
            }

            if (pollutantComponent.checkedContaminated(config)) {
                stack.getOrDefault(
                        TDataComponentTypes.DISEASE_EFFECTS,
                        DEFAULT_DISEASE
                ).forEach(effectApplier);
            }
        }
    }

    private PollutantEffects() {

    }
}