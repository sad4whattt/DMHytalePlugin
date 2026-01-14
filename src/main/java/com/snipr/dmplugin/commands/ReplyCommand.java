package com.snipr.dmplugin.commands;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;

import javax.annotation.Nonnull;
import java.awt.Color;

/**
 * Quick reply command - Reply to the last person who messaged you.
 * Usage: /reply <message>
 * Aliases: /r
 */
public class ReplyCommand extends CommandBase {
    
    @Nonnull
    private final RequiredArg<String> messageArg;
    
    public ReplyCommand() {
        super("reply", "Reply to the last person who messaged you", false);
        
        this.messageArg = this.withRequiredArg("message", "com.snipr.dmplugin.commands.reply.arg.message", ArgTypes.STRING);
        this.addAliases("r");
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
        String lastMessengerName = MessageCommand.lastMessaged.get(senderName);
        
        if (lastMessengerName == null) {
            context.sendMessage(Message.raw("No one to reply to! Send a message first with /msg").color(Color.RED));
            return;
        }
        
        PlayerRef target = null;
        for (PlayerRef player : Universe.get().getDefaultWorld().getPlayerRefs()) {
            if (player.getUsername().equals(lastMessengerName)) {
                target = player;
                break;
            }
        }
        
        if (target == null) {
            context.sendMessage(Message.raw(lastMessengerName + " is no longer online!").color(Color.RED));
            return;
        }
        
        Message toRecipient = Message.join(
            Message.raw("[DM] ").color(Color.MAGENTA),
            Message.raw(senderName).color(Color.YELLOW),
            Message.raw("->").color(Color.GRAY),
            Message.raw("You").color(Color.GREEN),
            Message.raw(": ").color(Color.GRAY),
            Message.raw(message).color(Color.WHITE)
        );
        
        Message toSender = Message.join(
            Message.raw("[DM] ").color(Color.MAGENTA),
            Message.raw("You").color(Color.GREEN),
            Message.raw("->").color(Color.GRAY),
            Message.raw(target.getUsername()).color(Color.YELLOW),
            Message.raw(": ").color(Color.GRAY),
            Message.raw(message).color(Color.WHITE)
        );
        
        target.sendMessage(toRecipient);
        context.sendMessage(toSender);
        
        MessageCommand.lastMessaged.put(target.getUsername(), senderName);
        MessageCommand.lastMessaged.put(senderName, target.getUsername());
    }
}
