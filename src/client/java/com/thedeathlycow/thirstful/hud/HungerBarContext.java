package com.thedeathlycow.thirstful.hud;

import org.joml.Vector2i;

import java.util.SequencedCollection;

public record HungerBarContext(SequencedCollection<Vector2i> positions) {
}
