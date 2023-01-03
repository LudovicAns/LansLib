package fr.ludovicans.lanslib.configuration;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;

@SuppressWarnings("unused")
public class ConfigurationManager {

    private final Plugin plugin;
    private final Map<File, FileConfiguration> filesMap = new HashMap<>();
    private Level loggingLevel;


    /**
     * @param plugin instance.
     */
    public ConfigurationManager(@NotNull Plugin plugin) {
        this.plugin = plugin;
        this.loggingLevel = Level.WARNING;
    }

    /**
     * @param plugin instance.
     * @param loggingLevel of the manager.
     */
    public ConfigurationManager(@NotNull Plugin plugin, @NotNull Level loggingLevel) {
        this(plugin);
        this.loggingLevel = loggingLevel;
    }

    /**
     * Create a data folder for your plugin.
     * You can ignore this method, simply call
     * {@link ConfigurationManager#initNewFile(String directory, String fileName, String fileContent)}.
     */
    @Deprecated public void setupDataFolder() {
        if (!plugin.getDataFolder().exists()) {
            if (plugin.getDataFolder().mkdir()) {
                log(Level.INFO, "✅ DataFolder has been created.");
            } else {
                log(Level.SEVERE, "⚠️  DataFolder can't be created.");
            }
        } else {
            log(Level.INFO, "✅ DataFolder loaded successfully.");
        }
    }


    /**
     * Init new configuration file. This method will create the file if it doesn't exist and add it to the manager.<br />
     * <strong>Note that you need to call this method even if file is already created.</strong>
     *
     * @param directory   the directory to put the new file. If the directory does not exist it will be
     *                    automatically create. Just put "." if you want to add it directly inside the data-folder.
     * @param fileName    the name of your new file.
     * @param fileContent the content of the file.
     */
    public void initNewFile(String directory, String fileName, String fileContent) {
        char sep = File.separatorChar;
        File directoryFile = new File(plugin.getDataFolder().getAbsolutePath() + sep + directory);
        if (directoryFile.mkdirs()) {
            log(Level.INFO, "✅ The directory " + directory + " has been created successfully.");
        }


        File file = new File(directoryFile.getAbsolutePath() + sep + fileName);
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    log(Level.INFO, "✅ The configuration file " + fileName + " has been created successfully.");
                }
            } catch (IOException ignored) { }

            try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
                writer.write(fileContent);
                writer.flush();
            } catch (IOException ignored) { }
        } else {
            log(Level.INFO, "✅ The configuration file " + fileName + " has been loaded successfully.");
        }

        YamlConfiguration fileCFG = YamlConfiguration.loadConfiguration(file);
        filesMap.put(file, fileCFG);
    }

    /**
     * Save {@link FileConfiguration} to {@link File}.
     */
    public void saveFiles() {
        filesMap.forEach((file, fileConfiguration) -> {
            try {
                fileConfiguration.save(file);
            } catch (IOException ignored) {
            }
        });
    }

    /**
     * Reload specific file.
     *
     * @param name of the file.
     */
    public void reloadFile(String name) {
        if (getConfigurationFile(name) == null) return;
        filesMap.forEach((file, fileConfiguration) -> {
            if (file.getName().equalsIgnoreCase(name)) {
                try {
                    fileConfiguration.load(file);
                } catch (IOException | InvalidConfigurationException ignored) {
                }
            }
        });
    }

    /**
     * Get specific configuration file by name of the file.
     *
     * @param name of the configuration file.
     * @return the fileconfiguration or null if {@link ConfigurationManager#filesMap} doesn't contain a file with that name.
     */
    @Nullable public FileConfiguration getConfigurationFile(String name) {
        AtomicReference<FileConfiguration> fileCFG = new AtomicReference<>(null);
        filesMap.forEach((file, fileConfiguration) -> {
            if (file.getName().equalsIgnoreCase(name)) {
                fileCFG.set(fileConfiguration);
            }
        });
        return fileCFG.get();
    }

    /**
     * Use this method of the classic {@link java.util.logging.Logger#log(Level, String)} to log depending on
     * loggingLevel.
     *
     * @param level of the message.
     * @param message to log.
     */
    private void log(Level level, String message) {
        if (level.intValue() >= loggingLevel.intValue()) {
            plugin.getLogger().log(level, "[LansLib] " + message);
        }
    }

    /**
     * Get map of {@link File} with its {@link FileConfiguration}.
     *
     * @return map of File with its FileConfiguration.
     */
    @NotNull public Map<File, FileConfiguration> getFilesMap() {
        return filesMap;
    }

    /**
     * This will load again (reload..) each file in {@link ConfigurationManager#filesMap} to update all {@link FileConfiguration}.
     */
    public void reloadFiles() {
        filesMap.forEach((file, fileConfiguration) -> {
            try {
                fileConfiguration.load(file);
            } catch (IOException | InvalidConfigurationException ignored) {
            }
        });
    }
}