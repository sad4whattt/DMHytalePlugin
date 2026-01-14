package com.snipr.dmplugin.commands;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
import com.hypixel.hytale.server.core.universe.PlayerRef;

import javax.annotation.Nonnull;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * Direct message command - Send private messages to other players.
 * Usage: /msg <player> <message>
 * Aliases: /dm, /tell, /whisper
 */
public class MessageCommand extends CommandBase {
    
    @Nonnull
    private final RequiredArg<PlayerRef> playerArg;
    
    @Nonnull
    private final RequiredArg<String> messageArg;
    
    public static final Map<String, String> lastMessaged = new HashMap<>();
    
    public MessageCommand() {
        super("msg", "Send a private message to a player", false);
        
        this.playerArg = this.withRequiredArg("player", "com.snipr.dmplugin.commands.msg.arg.player", ArgTypes.PLAYER_REF);
        this.messageArg = this.withRequiredArg("message", "com.snipr.dmplugin.commands.msg.arg.message", ArgTypes.STRING);
        
        this.addAliases("dm", "tell", "whisper", "w");
        this.setAllowsExtraArguments(true);
    }
    
    @Override
    protected void executeSync(@Nonnull CommandContext context) {
        PlayerRef target = (PlayerRef) context.get(this.playerArg);
        
        String msg = "";
        String[] args = context.getInputString().trim().split("\\s+");
        
        if (args.length > 2) {
            StringBuilder sb = new StringBuilder();
            for (int i = 2; i < args.length; i++) {
                sb.append(args[i]);
                if (i < args.length - 1) {
                    sb.append(" ");
                }
            }
            msg = sb.toString();
        } else {
            msg = (String) context.get(this.messageArg);
        }
        
        final String message = msg;
        String senderName = context.sender().getDisplayName();
        
        if (target.getUsername().equals(senderName)) {
            context.sendMessage(Message.raw("You cannot message yourself!").color(Color.RED));
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
        
        lastMessaged.put(target.getUsername(), senderName);
        lastMessaged.put(senderName, target.getUsername());
    }
}
