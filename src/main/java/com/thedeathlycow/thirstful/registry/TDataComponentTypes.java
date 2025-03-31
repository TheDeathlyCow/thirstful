package com.thedeathlycow.thirstful.registry;

import com.mojang.serialization.Codec;
import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.config.common.WaterPollutionConfig;
import com.thedeathlycow.thirstful.item.ItemStackCreationCallback;
import com.thedeathlycow.thirstful.item.component.DehydratingConsumableComponent;
import com.thedeathlycow.thirstful.item.component.PollutantComponent;
import com.thedeathlycow.thirstful.item.consume.ConsumePollutionEffect;
import com.thedeathlycow.thirstful.registry.tag.TItemTags;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.List;
import java.util.function.UnaryOperator;

public final class TDataComponentTypes {
    public static final ComponentType<PollutantComponent> POLLUTANTS = register(
            "pollutants",
            builder -> builder
                    .codec(PollutantComponent.CODEC)
                    .packetCodec(PollutantComponent.PACKET_CODEC)
                    .cache()
    );

    public static final ComponentType<DehydratingConsumableComponent> DEHYDRATION_CONSUMABLE = register(
            "dehydrating_consumable",
            builder -> builder
                    .codec(DehydratingConsumableComponent.CODEC)
                    .packetCodec(DehydratingConsumableComponent.PACKET_CODEC)
                    .cache()
    );

    public static final ComponentType<List<ConsumePollutionEffect>> DIRTINESS_EFFECTS = register(
            "dirtiness_effects",
            builder -> builder
                    .codec(Codec.list(ConsumePollutionEffect.ELEMENT_CODEC))
                    .packetCodec(ConsumePollutionEffect.PACKET_CODEC.collect(PacketCodecs.toList()))
                    .cache()
    );

    public static final ComponentType<List<ConsumePollutionEffect>> DISEASE_EFFECTS = register(
            "disease_effects",
            builder -> builder
                    .codec(Codec.list(ConsumePollutionEffect.ELEMENT_CODEC))
                    .packetCodec(ConsumePollutionEffect.PACKET_CODEC.collect(PacketCodecs.toList()))
                    .cache()
    );

    public static void initialize() {
        Thirstful.LOGGER.debug("Initialized Thirstful item components");

        ItemStackCreationCallback.EVENT.register(stack -> {
            WaterPollutionConfig config = Thirstful.getConfig().common().waterPollution();
            if (stack.isIn(TItemTags.CAN_BE_POLLUTED) && !stack.contains(TDataComponentTypes.POLLUTANTS)) {
                var component = new PollutantComponent(
                        stack.isIn(TItemTags.DIRTY_BY_DEFAULT) ? config.defaultWaterDirtiness() : 0,
                        stack.isIn(TItemTags.CONTAMINATED_BY_DEFAULT) ? config.defaultWaterDiseaseChance() : 0,
                        stack.isIn(TItemTags.SALTY_BY_DEFAULT)
                );
                stack.set(TDataComponentTypes.POLLUTANTS, component);
            }
        });
    }

    private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builder) {
        return Registry.register(
                Registries.DATA_COMPONENT_TYPE,
                Thirstful.id(id),
                builder.apply(ComponentType.builder()).build()
        );
    }

    private TDataComponentTypes() {

    }
}