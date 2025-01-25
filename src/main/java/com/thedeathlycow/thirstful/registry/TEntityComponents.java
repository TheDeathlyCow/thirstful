package com.thedeathlycow.thirstful.registry;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.thirst.PlayerThirstComponent;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;

public final class TEntityComponents implements EntityComponentInitializer {
    public static final ComponentKey<PlayerThirstComponent> PLAYER_THIRST = ComponentRegistry.getOrCreate(
            Thirstful.id("player_thirst"),
            PlayerThirstComponent.class
    );

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(
                PLAYER_THIRST,
                PlayerThirstComponent::new,
                RespawnCopyStrategy.LOSSLESS_ONLY
        );
    }

    private TEntityComponents() {}
}