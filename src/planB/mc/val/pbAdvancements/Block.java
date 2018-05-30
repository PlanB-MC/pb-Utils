package planB.mc.val.pbAdvancements;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import planB.mc.val.Main;
import planB.mc.val.pbUtils.pbConfig;
import planB.mc.val.pbUtils.pbUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class Block {
    public static boolean blocksDone;
    private static Main plugin;
    private static HashMap<String, HashMap<String, Object>> itemList;
    private static int count;
    private static pbConfig blockConfig;
    private static onBlockList blockList;
    private onAdvancementsShop advancementsShop;

    public Block(Main main) {
        Main.pbConfigFile.addDefault("blocksListSet", false, "pbAdvancements", "data");
        Main.pbConfigFile.addDefault("blocksDone", false, "pbAdvancements", "data");
        Main.pbConfigFile.addDefault("enablePlugin", true, "pbAdvancements", "data");
        if (!Main.pbConfigFile.getBoolean("enablePlugin", "pbAdvancements", "data")) {
            pbUtils.log('C', "[pbAdvancements]");
            return;
        }
        plugin = main;
        if (!Main.pbConfigFile.getBoolean("blocksDone", "pbAdvancements", "data")) {
            blocksDone = false;
            if (!Main.pbConfigFile.getBoolean("blockListSet", "pbAdvancements", "data")) {

                try {
                    itemList = new HashMap<>();
                    blockConfig = new pbConfig(main, "blockConfig.yml");
                    if (!Main.pbConfigFile.getBoolean("blocksListSet", "pbAdvancements", "data")) {
                        createConfig();
                        count = 0;
                    } else {
                        reloadConfig();
                        count = blockConfig.getInt("blocksFound", "data");
                    }
                } catch (FileNotFoundException e) {
                    pbUtils.log("[pbAdvancements]", "File Could not be load. THERE IS NO BLOCKS!!");
                }
                pbUtils.log("[pbAdvancements]", "Printing Registered Blocks");
                itemList.forEach((k, v) -> pbUtils.log("[pbAdvancements]", "----- Registered: " + k + ":" + v.toString()));
                pbUtils.log("[pbAdvancements]", "All blocks registered");
                blockList = new onBlockList(main);
                Main.pbListenersEars.addListener(blockList);
            }
        } else {
            blocksDone = true;
        }
        advancementsShop = new onAdvancementsShop(main);
        Main.pbListenersEars.addListener(advancementsShop);
        pbUtils.log('I', "[pbAdvancements]");
    }

    public static boolean itemExistFound(String compare) {
        if (itemList.containsKey(compare)) {
            if ((Boolean) itemList.get(compare).get("found"))
                return false;
            else
                return true;
        } else
            return false;
    }

    public static void put(String compare, boolean b, String pName) {
        addToList(compare, true, null, pName);
        try {
            blockConfig.set(pName, blockConfig.getInt(pName, "players") + 1, "players");
        } catch (NullPointerException e) {
            blockConfig.set(pName, 1, "players");
        }
        count++;
        blockConfig.set("blocksFound", count, "data");
        if (count >= blockConfig.getInt("blockListSize", "data")) {
            blocksDone = true;
            Main.pbConfigFile.set("blocksDone", true, "pbAdvancements", "data");
            Bukkit.broadcastMessage(ChatColor.GOLD + "Hubert has Arrived");
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),
                    "summon minecraft:llama 26 54 1 {NoAI:1b,Invulnerable:1,Passengers:" +
                            "[{id:\"minecraft:vindication_illager\",CustomName:Hubert,CustomNameVisible:1,NoAI:1b," +
                            "PersistenceRequired:1b,CanPickUpLoot:0b,Johnny:0b,Silent:1,Invulnerable:1}]}");
            Main.pbListenersEars.removeListener(blockList);
        }
    }

    private static void addToList(String itemName, boolean found, String name, String playerName) {
        HashMap<String, Object> curItem = new HashMap<>();
        curItem.put("found", found);
        curItem.put("name", name == null ? itemList.get(itemName).get("name") : name);
        curItem.put("player", playerName == null ? "" : playerName);// can be just ""
        itemList.put(itemName, curItem);
    }

    public static void print(CommandSender sender, String done) {
        switch (done) {
            case "done": {
                itemList.forEach((k, v) -> {
                    if ((Boolean) v.get("found")) sender.sendMessage(ChatColor.AQUA + "you have " + v.get("name"));
                });
                break;
            }
            case "todo": {
                itemList.forEach((k, v) -> {
                    if (!(Boolean) v.get("found")) sender.sendMessage(ChatColor.AQUA + "you need " + v.get("name"));
                });
                break;
            }
        }
    }

    private void reloadConfig() {
        pbUtils.log("[pbAdvancements]", "Loading Config file");
        Set<String> paths = blockConfig.getConfigurationSection("blocks").getKeys(false);
        paths.forEach(path -> addToList(
                path,
                (Boolean) blockConfig.get("blocks." + path + ".found"),
                (String) blockConfig.get("blocks." + path + ".name"),
                (String) blockConfig.get("blocks." + path + ".player")
        ));
    }

    private void createConfig() throws FileNotFoundException {
        File file = new File(plugin.getDataFolder(), "blocks.yml");
        Scanner input = new Scanner(file);
        pbUtils.log("[pbAdvancements]", "Creating " + file.getAbsolutePath());
        Main.pbConfigFile.set("enablePlugin", true, "pbAdvancements", "data");
        Main.pbConfigFile.set("blocksDone", false, "pbAdvancements", "data");
        Main.pbConfigFile.set("blocksListSet", false, "pbAdvancements", "data");
        blockConfig.set("blocks", null);
        blockConfig.addDefault("blockListSize", 0, "data");
        blockConfig.addDefault("blocksFound", 0, "data");
        while (input.hasNext()) {
            String line = input.nextLine();
            String[] entry = line.split(":");
            if (itemList.containsKey(entry[0]))
                continue;
            saveToConfig("blocks", entry[0], false, entry[1], null);
            addToList(entry[0], false, entry[1], null);
        }
        blockConfig.set("blockListSize", itemList.size(), "data");
        blockConfig.set("blocksFound", 0, "data");
        blockConfig.set("players", null);
        Main.pbConfigFile.set("blocksListSet", true, "pbAdvancements", "data");
        input.close();
    }

    private void saveToConfig(String parent, String path, boolean found, String name, String player) {
        blockConfig.set("found", found, parent, path);
        blockConfig.set("name", name == null ? itemList.get(path).get("name") : name, parent, path);
        blockConfig.set("player", player == null ? "" : player, parent, path);
    }
}