package planB.mc.val.pbAdvancements;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.MemorySection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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
    public static String name = "Koh";
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
            //if (!Main.pbConfigFile.getBoolean("blockListSet", "pbAdvancements", "data")) {
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
            pbUtils.log("[pbAdvancements1]", String.valueOf(itemList.size()));
            pbUtils.log("[pbAdvancements]", "Printing Registered Blocks");
            // itemList.forEach((k, v) -> pbUtils.log("[pbAdvancements]", "----- Registered: " + k + ":" + v.toString()));
            pbUtils.log("[pbAdvancements]", "All blocks registered");
            blockList = new onBlockList(main);
            Main.pbListenersEars.addListener(blockList);
            //}
        } else {
            blocksDone = true;
        }
        advancementsShop = new onAdvancementsShop(main);
        Main.pbListenersEars.addListener(advancementsShop);
        pbUtils.log('I', "[pbAdvancements]");
    }

    public static boolean itemExistFound(String compare) {
        // itemList.forEach((key,value) -> System.out.println(key + ":" + value));
        if (itemList.containsKey(compare)) {
            if ((Boolean) itemList.get(compare).get("found"))
                return false;
            else
                return true;
        } else
            return false;
    }

    public static void put(String compare, Player pName) {
        addToList(compare, true, null, pName.getName());
        setPlayerScore(pName);
        count++;
        blockConfig.set("blocksFound", count, "data");
        if (count >= blockConfig.getInt("blockListSize", "data")) {
            blocksDone = true;
            Main.pbConfigFile.set("blocksDone", true, "pbAdvancements", "data");
            Bukkit.broadcastMessage(ChatColor.GOLD + name + " has revealed his stock!");
            /*Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),
                    "summon bat ~ ~1 ~ {CustomName:\"\\\"name\\\"\",CustomNameVisible:1,NoGravity:1b,Invulnerable:1,NoAI:1}");*/
            Main.pbListenersEars.removeListener(blockList);
        }
    }

    private static void addToList(String itemName, boolean found, String name, String playerName) {
        HashMap<String, Object> curItem = new HashMap<>();
        curItem.put("found", found);
        curItem.put("name", name == null ? itemList.get(itemName).get("name") : name);
        curItem.put("player", playerName == null ? "" : playerName);// can be just ""
        itemList.put(itemName, curItem);
        saveToConfig("blocks", itemName, found, null, playerName);
    }

    public static void print(CommandSender sender, String done) {
        Location curLoc = Bukkit.getPlayer(sender.getName()).getLocation().add(0.5, 0.5, 0.5);
        switch (done) {
            case "done": {
                itemList.forEach((k, v) -> {
                    if ((Boolean) v.get("found"))
                        curLoc.getWorld().dropItemNaturally(curLoc, new ItemStack(Material.getMaterial(k)));
                });
                break;
            }
            case "todo": {
                itemList.forEach((k, v) -> {
                    if (!(Boolean) v.get("found"))
                        curLoc.getWorld().dropItemNaturally(curLoc, new ItemStack(Material.getMaterial(k)));
                });
                break;
            }
            case "all": {
                itemList.forEach((k, v) -> curLoc.getWorld().dropItemNaturally(curLoc, new ItemStack(Material.getMaterial(k))));
                break;
            }
        }
    }

    private static void setPlayerScore(Player player) {
        String pUUID = player.getUniqueId().toString();
        MemorySection msPlayer = (MemorySection) blockConfig.get(pUUID, "players");
        ActivePlayer curPLayer = null;
        if (msPlayer != null)
            curPLayer = new ActivePlayer(msPlayer.getInt("found"), msPlayer.getString("name"), msPlayer.getString("uuid"));
        else
            curPLayer = new ActivePlayer(0, player.getName(), pUUID);

        blockConfig.set("name", curPLayer.name, "players", curPLayer.UUID);
        blockConfig.set("uuid", curPLayer.UUID, "players", curPLayer.UUID);
        blockConfig.set("found", curPLayer.found, "players", curPLayer.UUID);

    }

    private static void saveToConfig(String parent, String path, boolean found, String name, String player) {
        blockConfig.set("found", found, parent, path);
        blockConfig.set("name", name == null ? itemList.get(path).get("name") : name, parent, path);
        blockConfig.set("player", player == null ? "" : player, parent, path);
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

    static class ActivePlayer {
        int found;
        String name;
        String UUID;

        public ActivePlayer(int found, String name, String UUID) {
            System.out.println(found + " " + name + " " + UUID);
            this.found = found + 1;
            this.name = name;
            this.UUID = UUID;
        }
    }
}