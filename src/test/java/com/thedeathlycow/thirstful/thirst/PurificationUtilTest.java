package com.thedeathlycow.thirstful.thirst;

import com.thedeathlycow.thirstful.ThirstfulTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurificationUtilTest {
    @BeforeAll
    static void bootstrap() {
        ThirstfulTest.initialize();
    }

    @Test
    void pass() {
        Assertions.assertTrue(true);
    }
}