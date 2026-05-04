package com.thedeathlycow.thirstful.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.thedeathlycow.thirstful.registry.TDataComponentTypes;
import com.thedeathlycow.thirstful.registry.tag.TItemTags;
import com.thedeathlycow.thirstful.thirst.PlayerThirstComponent;
import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.function.Consumer;

public record DrinkComponent(
        double water
) implements TooltipProvider {
    public static final Codec<DrinkComponent> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.DOUBLE
                            .fieldOf("water")
                            .forGetter(DrinkComponent::water)
            ).apply(instance, DrinkComponent::new)
    );

    public static final StreamCodec<ByteBuf, DrinkComponent> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.DOUBLE,
            DrinkComponent::water,
            DrinkComponent::new
    );

    private static final DrinkComponent BOTTLE = new DrinkComponent(5.0);
    private static final DrinkComponent BUCKET = new DrinkComponent(10.0);
    private static final DrinkComponent DEFAULT = new DrinkComponent(0.0);

    public static DrinkComponent getOrTag(ItemStack stack) {
        return getOrTag(stack, DEFAULT);
    }

    public static DrinkComponent getOrTag(ItemStack stack, DrinkComponent defaultValue) {
        DrinkComponent component = stack.get(TDataComponentTypes.DRINK);

        if (component != null) {
            return component;
        }

        if (stack.is(TItemTags.RESTORES_THIRST)) {
            if (stack.is(ConventionalItemTags.DRINK_CONTAINING_BUCKET)) {
                return BUCKET;
            }

            return BOTTLE;
        }

        return defaultValue;
    }

    public void drink(Player player) {
        PlayerThirstComponent component = PlayerThirstComponent.get(player);
        component.addThirstLevel(this.water);
    }

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> tooltipAdder, TooltipFlag tooltipFlag) {
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            tooltipAdder.accept(Component.literal("[DEV ONLY] Water: %.2f".formatted(this.water)).withStyle(ChatFormatting.BLUE));
        }
    }
}