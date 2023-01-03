package fr.ludovicans.lanslib;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

@SuppressWarnings("unused")
public final class LansLib extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getServer().getLogger().log(Level.INFO, "LansLib loaded.");
    }
}
