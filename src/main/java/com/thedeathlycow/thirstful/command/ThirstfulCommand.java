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