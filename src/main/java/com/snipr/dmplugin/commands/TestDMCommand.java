package com.snipr.dmplugin.commands;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;

import javax.annotation.Nonnull;
import java.awt.Color;

public class TestDMCommand extends CommandBase {
    
    @Nonnull
    private final RequiredArg<String> messageArg;
    
    public TestDMCommand() {
        super("dmtest", "Test the DM plugin formatting (shows both perspectives)", false);
        
        this.messageArg = this.withRequiredArg("message", "com.snipr.dmplugin.commands.dmtest.arg.message", ArgTypes.STRING);
        this.setAllowsExtraArguments(true);
    }
    
    @Override
    protected void executeSync(@Nonnull CommandContext context) {
        String message = "";
        String[] args = context.getInputString().trim().split("\\s+");
        
        if (args.length > 1) {
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                sb.append(args[i]);
                if (i < args.length - 1) {
                    sb.append(" ");
                }
            }
            message = sb.toString();
        } else {
             message = (String) context.get(this.messageArg);
        }

        String senderName = context.sender().getDisplayName();
        
        Message senderView = Message.join(
            Message.raw("[TEST] Sender sees: ").color(Color.CYAN),
            Message.raw("\n"),
            Message.raw("[DM] ").color(Color.MAGENTA),
            Message.raw("You").color(Color.GREEN),
            Message.raw(" → ").color(Color.GRAY),
            Message.raw("TestPlayer").color(Color.YELLOW),
            Message.raw(": ").color(Color.GRAY),
            Message.raw(message).color(Color.WHITE)
        );
        
        context.sendMessage(senderView);
        
        Message receiverView = Message.join(
            Message.raw("[TEST] Receiver sees: ").color(Color.CYAN),
            Message.raw("\n"),
            Message.raw("[DM] ").color(Color.MAGENTA),
            Message.raw(senderName).color(Color.YELLOW),
            Message.raw(" → ").color(Color.GRAY),
            Message.raw("You").color(Color.GREEN),
            Message.raw(": ").color(Color.GRAY),
            Message.raw(message).color(Color.WHITE)
        );
        
        context.sendMessage(receiverView);
        context.sendMessage(Message.raw("\n✓ DMPlugin is working! Commands: /msg, /reply").color(Color.GREEN));
    }
}
