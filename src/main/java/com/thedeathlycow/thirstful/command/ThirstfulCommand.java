/*
 * Thirstful: A new approach to thirst and food contamination
 * Copyright (C) 2025 TheDeathlyCow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.thedeathlycow.thirstful.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.thedeathlycow.thirstful.Thirstful;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

public class ThirstfulCommand implements CommandRegistrationCallback {
    public static final String ABOUT_DESC_ID = Thirstful.MODID + ".commands.about";

    @Override
    public void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext registryAccess, Commands.CommandSelection environment) {
        final var thirstfulCommand = Commands.literal(Thirstful.MODID);

        final var about = Commands.literal("about")
                .executes(ctx -> executeAbout(ctx.getSource()));
        thirstfulCommand.then(about);

        dispatcher.register(thirstfulCommand);
    }

    private static int executeAbout(CommandSourceStack source) throws CommandSyntaxException {
        String sourceLink = Thirstful.getSourceLink();

        source.sendSystemMessage(Component.translatable(ABOUT_DESC_ID));

        var linkStyle = Style.EMPTY
                .applyFormats(ChatFormatting.BLUE, ChatFormatting.UNDERLINE)
                .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, sourceLink));
        source.sendSystemMessage(Component.literal(sourceLink).withStyle(linkStyle));

        return Command.SINGLE_SUCCESS;
    }
}