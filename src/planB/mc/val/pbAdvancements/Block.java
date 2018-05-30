package planB.mc.val.pbAdvancements;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import planB.mc.val.Main;
import planB.mc.val.pbUtils.pbUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;

public class Block {
    public static boolean blocksDone;
    private static HashMap<String, Boolean> items;
    private static Main plugin;
    private static File file;
    private static onBlockList blockList;
    private static onAdvancementsShop onAdvancementsShop;

    //used at startup

    public Block(Main main) {
        //set config
        Main.pbConfigFile.addDefault("enablePlugin", true, "pbAdvancements", "data");
        Main.pbConfigFile.addDefault("blocksListSet", false, "pbAdvancements", "data");
        Main.pbConfigFile.addDefault("blocksFound", 0, "pbAdvancements", "data");
        Main.pbConfigFile.addDefault("BlockListSize", 0, "pbAdvancements", "data");
        Main.pbConfigFile.addDefault("blocksDone", false, "pbAdvancements", "data");
        //checking sum stuff
        if (!Main.pbConfigFile.getBoolean("enablePlugin", "pbAdvancements", "data")) {
            pbUtils.log('C', "[pbAdvancements]");
            return;
        }
        onAdvancementsShop = new onAdvancementsShop(main);
        blocksDone = true;
        if (!Main.pbConfigFile.getBoolean("blocksDone", "pbAdvancements", "data")) {
            blocksDone = false;
            blockList = new onBlockList(plugin);
            items = new HashMap<>();
            plugin = main;
            //loading blocks
            file = new File(plugin.getDataFolder(), "log2.txt");
            try {
                Scanner input = new Scanner(file);
                if (!Main.pbConfigFile.getBoolean("blocksListSet", "pbAdvancements", "data")) {
                    //populates config file
                    pbUtils.log("[pbAdvancements]", "Creating " + file.getAbsoluteFile());
                    configReset(input);
                } else {
                    //just reads the config file
                    pbUtils.log("[pbAdvancements]", "Loading " + file.getAbsoluteFile());
                    configReLoad(input);
                }
                input.close();
            } catch (FileNotFoundException e) {
                pbUtils.log("[pbAdvancements]", "File Could not be load. THERE IS NO BLOCKS!!");
            }

            pbUtils.log("[pbAdvancements]", "Reading " + file.getAbsoluteFile());
            items.forEach((k, v) -> pbUtils.log("[pbAdvancements]", "----- Registered: " + k + ":" + v));
            pbUtils.log("[pbAdvancements]", "All blocks registered");
            Main.pbListenersEars.addListener(blockList);
        }
        Main.pbListenersEars.addListener(onAdvancementsShop);
        pbUtils.log('I', "[pbAdvancements]");
    }

    private static void configReset(Scanner input) {
        //restoring defualts
        Main.pbConfigFile.set("enablePlugin", true, "pbAdvancements", "data");
        Main.pbConfigFile.set("blocksFound", 0, "pbAdvancements", "data");
        Main.pbConfigFile.set("blocksDone", false, "pbAdvancements", "data");
        Main.pbConfigFile.set("player", null, "pbAdvancements");
        while (input.hasNext()) {
            String line = input.nextLine();
            if (items.containsKey(line))
                continue;
            Main.pbConfigFile.set(line, false, "pbAdvancements", "blocks");
            items.put(line, false);
        }
        //Main.pbConfigFile.set("BlockListSize", items.size(), "pbAdvancements", "data");
        Main.pbConfigFile.set("BlockListSize", 5, "pbAdvancements", "data");
        Main.pbConfigFile.set("blocksListSet", true, "pbAdvancements", "data");
    }

    //used at startup
    private static void configReLoad(Scanner input) {
        while (input.hasNext()) {
            String line = input.nextLine();
            if (items.containsKey(line))
                continue;
            items.put(line, Main.pbConfigFile.getBoolean(line, "pbAdvancements", "blocks"));
        }
    }

    public static void reloadConfig() {
        try {
            items.clear();
            pbUtils.log("[pbAdvancements]", "cleared list!");
            pbUtils.log("[pbAdvancements]", "Loading " + file.getAbsoluteFile());
            Scanner input = new Scanner(file);
            while (input.hasNext()) {
                String line = input.nextLine();
                if (items.containsKey(line))
                    continue;
                items.put(line, (Boolean) Main.pbConfigFile.get(line, "pbAdvancements", "blocks"));
            }
            input.close();
            pbUtils.log("[pbAdvancements]", "Reading " + file.getAbsoluteFile());
            items.forEach((k, v) -> plugin.getLogger().log(Level.INFO, "----- Registered: " + k + ":" + v));
            pbUtils.log("[pbAdvancements]", "All blocks registered");
        } catch (FileNotFoundException e) {
            pbUtils.log("[pbAdvancements]", "File Could not be load. THERE IS NO BLOCKS!!");
        }
    }

    public static void resetConfig() {
        try {
            pbUtils.log("[pbAdvancements]", "Resetting Block config");
            items.clear();
            Scanner input = new Scanner(file);
            configReset(input);
            input.close();
            pbUtils.log("[pbAdvancements]", "Reading " + file.getAbsoluteFile());
            items.forEach((k, v) -> pbUtils.log("[pbAdvancements]", "----- Registered: " + k + ":" + v));
            pbUtils.log("[pbAdvancements]", "All blocks registered");
        } catch (FileNotFoundException e) {
            pbUtils.log("[pbAdvancements]", "File Could not be load. THERE IS NO BLOCKS!!");
        }
    }

    public static Boolean get(String key) {
        return items.get(key);
    }

    public static Boolean put(String key, Boolean value, String pName) {
        int count = Main.pbConfigFile.getInt("blocksFound", "pbAdvancements", "data") + 1;
        Main.pbConfigFile.set("blocksFound", count, "pbAdvancements", "data");
        Main.pbConfigFile.set(key, true, "pbAdvancements", "blocks");
        try {
            int pAmount = Main.pbConfigFile.getInt(pName, "pbAdvancements", "player") + 1;
            Main.pbConfigFile.set(pName, pAmount, "pbAdvancements", "player");
        } catch (NullPointerException e) {
            Main.pbConfigFile.set(pName, 1, "pbAdvancements", "player");
        }
        if (count >= Main.pbConfigFile.getInt("BlockListSize", "pbAdvancements", "data")) {
            if (blocksDone) items.put(key, value);
            Main.pbConfigFile.set("blocksDone", true, "pbAdvancements", "data");
            blocksDone = true;
            Bukkit.broadcastMessage(ChatColor.GOLD + "Hubert has Arrived");
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),
                    "summon minecraft:llama 26 54 1 {NoAI:1b,Invulnerable:1,Passengers:" +
                            "[{id:\"minecraft:vindication_illager\",CustomName:Hubert,CustomNameVisible:1,NoAI:1b," +
                            "PersistenceRequired:1b,CanPickUpLoot:0b,Johnny:0b,Silent:1,Invulnerable:1}]}");
        }
        return items.put(key, value);
    }

    public static void toggle(boolean b) {
        System.out.println("#1the value of b is " + String.valueOf(b));
        if (b)
            setListener(false);
        else {
            System.out.println("#2the value of b is " + String.valueOf(b));
            Main.pbListenersEars.removeListener(blockList);
            Main.pbListenersEars.removeListener(onAdvancementsShop);
            pbUtils.log('D', "[pbAdvancements]");
        }
    }

    public static void setListener(boolean positive) {
        if (!Main.pbConfigFile.getBoolean("pbAdvancements", "enablePlugin") || positive) {
            if (!Main.pbConfigFile.getBoolean("pbAdvancements", "blocksDone"))
                Main.pbListenersEars.addListener(blockList);
            Main.pbListenersEars.addListener(onAdvancementsShop);
            Main.pbConfigFile.set("enablePlugin", true, "pbAdvancements", "data");
            pbUtils.log('E', "[pbAdvancements]");
        }
    }

    public static void print(CommandSender sender, String done) {
        switch (done) {
            case "done": {
                items.forEach((k, v) -> {
                    if (v) sender.sendMessage(ChatColor.AQUA + "you have " + k);
                });
                break;
            }
            case "todo": {
                items.forEach((k, v) -> {
                    if (!v) sender.sendMessage(ChatColor.AQUA + "you need " + k);
                });
                break;
            }
        }
    }
}