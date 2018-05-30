package planB.mc.val.pbUtils;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class pbConfig extends YamlConfiguration {
    private File file;
    private String defaults;
    private JavaPlugin plugin;

    /**
     * Creates new BlockConfig, without defaults
     *
     * @param plugin   - Your plugin
     * @param fileName - Name of the file
     */
    public pbConfig(JavaPlugin plugin, String fileName) {
        this(plugin, fileName, null);
    }

    /**
     * Creates new BlockConfig, with defaults
     *
     * @param plugin       - Your plugin
     * @param fileName     - Name of the file
     * @param defaultsName - Name of the defaults
     */
    public pbConfig(JavaPlugin plugin, String fileName, String defaultsName) {
        this.plugin = plugin;
        this.defaults = defaultsName;
        this.file = new File(plugin.getDataFolder(), fileName);
        reload();
    }

    /**
     * Reload configuration
     */
    public void reload() {
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException exception) {
                exception.printStackTrace();
                plugin.getLogger().severe("Error while creating file " + file.getName());
            }
        }

        try {
            load(file);
            if (defaults != null) {
                InputStreamReader reader = new InputStreamReader(plugin.getResource(defaults));
                FileConfiguration defaultsConfig = YamlConfiguration.loadConfiguration(reader);

                setDefaults(defaultsConfig);
                options().copyDefaults(true);

                reader.close();
                save();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
            plugin.getLogger().severe("Error while loading file " + file.getName());
        } catch (InvalidConfigurationException exception) {
            exception.printStackTrace();
            plugin.getLogger().severe("Error while loading file " + file.getName());
        }
    }

    /**
     * Save configuration
     */
    public void save() {
        try {
            options().indent(2);
            save(file);
        } catch (IOException exception) {
            exception.printStackTrace();
            plugin.getLogger().severe("Error while saving file " + file.getName());
        }
    }

    public void addDefault(String key, Object value, String... parents) {
        String path = makePath(parents) + key;
        addDefault(path, value);
        options().copyDefaults(true);
        save();
    }

    public Object get(String path, String parent) {
        return super.get(parent + "." + path);
    }

    public void set(String key, Object value, String... parents) {
        String path = makePath(parents) + key;
        super.set(path, value);
        save();
    }

    public Object get(String key, String... parents) {
        String path = makePath(parents) + key;
        return super.get(path);
    }

    public boolean getBoolean(String key, String... parents) {
        String path = makePath(parents) + key;
        return super.getBoolean(path);
    }

    public int getInt(String key, String... parents) {
        String path = makePath(parents) + key;
        return super.getInt(path);
    }

    private String makePath(String... parents){
        String path = "";
        for (String cur : parents){
            path += cur + ".";
        }
        return path;
    }

}