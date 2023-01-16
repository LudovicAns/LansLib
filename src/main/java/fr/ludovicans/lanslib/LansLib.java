package fr.ludovicans.lanslib;

import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Level;

@SuppressWarnings("unused")
public final class LansLib extends JavaPlugin {

    private static LansLib INSTANCE;

    private @Nullable LuckPerms luckPermsAPI = null;

    @Override
    public void onEnable() {
        INSTANCE = this;

        final @Nullable RegisteredServiceProvider<LuckPerms> registration = Bukkit
                .getServicesManager()
                .getRegistration(LuckPerms.class);

        if (registration != null) {
            this.luckPermsAPI = registration.getProvider();
        }

        this.getLogger().log(Level.INFO, "LansLib loaded.");
    }

    public static LansLib getINSTANCE() {
        return INSTANCE;
    }

    public @Nullable LuckPerms getLuckPermsAPI() {
        return luckPermsAPI;
    }
}
