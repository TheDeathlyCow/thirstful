package com.thedeathlycow.thirstful.thirst;

import com.thedeathlycow.thirstful.ThirstfulTest;
import com.thedeathlycow.thirstful.item.component.PollutantComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

class PurificationUtilTest {
    @BeforeAll
    static void bootstrap() {
        ThirstfulTest.initialize();
    }

    @Test
    void emptyListHasZeroProbability() {
        float probabilityOfAny = PurificationUtil.probabilityOfAnyEffect(Collections.emptyList());
        Assertions.assertEquals(0f, probabilityOfAny);
    }

    @Test
    void singletonListHasIdenticalProbability() {
        float probabilityOfAny =  PurificationUtil.probabilityOfAnyEffect(
                Collections.singletonList(
                        new PollutantComponent.StatusEffectEntry(
                                new StatusEffectInstance(StatusEffects.REGENERATION),
                                0.5f
                        )
                )
        );
        Assertions.assertEquals(0.5f, probabilityOfAny);
    }

    @Test
    void doubleListHas62pcProbability() {
        float probabilityOfAny =  PurificationUtil.probabilityOfAnyEffect(
                List.of(
                        new PollutantComponent.StatusEffectEntry(
                                new StatusEffectInstance(StatusEffects.REGENERATION),
                                0.5f
                        ),
                        new PollutantComponent.StatusEffectEntry(
                                new StatusEffectInstance(StatusEffects.POISON),
                                0.25f
                        )
                )
        );
        Assertions.assertEquals(0.625f, probabilityOfAny);
    }

    @Test
    void listOfZeroPercentHasZeroProbability() {
        float probabilityOfAny =  PurificationUtil.probabilityOfAnyEffect(
                List.of(
                        new PollutantComponent.StatusEffectEntry(
                                new StatusEffectInstance(StatusEffects.REGENERATION),
                                0
                        ),
                        new PollutantComponent.StatusEffectEntry(
                                new StatusEffectInstance(StatusEffects.POISON),
                                0
                        )
                )
        );
        Assertions.assertEquals(0f, probabilityOfAny);
    }

    @Test
    void listOfOneHundredPercentHas100pcProbability() {
        float probabilityOfAny =  PurificationUtil.probabilityOfAnyEffect(
                List.of(
                        new PollutantComponent.StatusEffectEntry(
                                new StatusEffectInstance(StatusEffects.REGENERATION),
                                1f
                        ),
                        new PollutantComponent.StatusEffectEntry(
                                new StatusEffectInstance(StatusEffects.POISON),
                                1f
                        )
                )
        );
        Assertions.assertEquals(1f, probabilityOfAny);
    }

    @Test
    void listOfOneHundredPercentAndZeroHas100pcProbability() {
        float probabilityOfAny =  PurificationUtil.probabilityOfAnyEffect(
                List.of(
                        new PollutantComponent.StatusEffectEntry(
                                new StatusEffectInstance(StatusEffects.REGENERATION),
                                0f
                        ),
                        new PollutantComponent.StatusEffectEntry(
                                new StatusEffectInstance(StatusEffects.POISON),
                                1f
                        )
                )
        );
        Assertions.assertEquals(1f, probabilityOfAny);
    }
}