package com.snipr.dmplugin;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.snipr.dmplugin.commands.MessageCommand;
import com.snipr.dmplugin.commands.ReplyCommand;
import com.snipr.dmplugin.commands.TestDMCommand;

import javax.annotation.Nonnull;

public class DMPlugin extends JavaPlugin {
    
    public DMPlugin(@Nonnull JavaPluginInit init) {
        super(init);
    }
    
    @Override
    protected void setup() {
        this.getCommandRegistry().registerCommand(new MessageCommand());
        
        this.getCommandRegistry().registerCommand(new ReplyCommand());
        
        // Test command for solo testing
        this.getCommandRegistry().registerCommand(new TestDMCommand());
    }
}
