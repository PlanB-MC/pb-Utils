package planB.mc.val.pbAdvancements;

import planB.mc.val.Main;
import planB.mc.val.pbUtils.pbConfig;
import planB.mc.val.pbUtils.pbUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Block {
    public static boolean blocksDone;
    private static Main plugin;
    private static HashMap<String, Boolean> itemList;
    private onBlockList blockList;
    private onAdvancementsShop advancementsShop;
    private static pbConfig blockConfig;

    public Block(Main main) {
        Main.pbConfigFile.addDefault("blocksListSet", false, "pbAdvancements", "data");
        Main.pbConfigFile.addDefault("blocksDone", false, "pbAdvancements", "data");
        Main.pbConfigFile.addDefault("enablePlugin", true, "pbAdvancements", "data");
        if (!Main.pbConfigFile.getBoolean("enablePlugin", "pbAdvancements", "data")) {
            pbUtils.log('C', "[pbAdvancements]");
            return;
        }
        plugin = main;
        //check too doo
        if (!Main.pbConfigFile.getBoolean("blocksDone", "pbAdvancements", "data")) {
            blocksDone = true;
            if (!Main.pbConfigFile.getBoolean("blockListSet", "pbAdvancements", "data")) {
                itemList = new HashMap<>();
                File file = new File(plugin.getDataFolder(), "blocks.yml");
                try {
                    Scanner input = new Scanner(file);
                    if (!Main.pbConfigFile.getBoolean("blocksListSet", "pbAdvancements", "data")) {
                        //populates config file
                        pbUtils.log("[pbAdvancements]", "Creating " + file.getAbsoluteFile());
                        createConfig(file, input);
                    } else {
                        //just reads the config file
                        pbUtils.log("[pbAdvancements]", "Loading " + file.getAbsoluteFile());
                        reloadConfig(file, input);
                    }
                    input.close();
                } catch (FileNotFoundException e) {
                    pbUtils.log("[pbAdvancements]", "File Could not be load. THERE IS NO BLOCKS!!");
                }
                pbUtils.log("[pbAdvancements]", "Reading " + file.getAbsoluteFile());
                itemList.forEach((k, v) -> pbUtils.log("[pbAdvancements]", "----- Registered: " + k + ":" + v));
                pbUtils.log("[pbAdvancements]", "All blocks registered");
                blockList = new onBlockList(main);
                Main.pbListenersEars.addListener(blockList);
            }
        } else {
            blocksDone = false;
        }
        advancementsShop = new onAdvancementsShop(main);
        Main.pbListenersEars.addListener(advancementsShop);
        pbUtils.log('I', "[pbAdvancements]");
    }

    private void reloadConfig(File file, Scanner input) {
    }

    private void createConfig(File file, Scanner input) {
        Main.pbConfigFile.set("enablePlugin", true, "pbAdvancements", "data");
        Main.pbConfigFile.set("blocksDone", false, "pbAdvancements", "data");
        Main.pbConfigFile.set("blocksListSet", false, "pbAdvancements", "data");
        blockConfig.set("blocks",null);
        while (input.hasNext()) {
            String line = input.nextLine();
            String[] entry = line.split(":");
            if (itemList.containsKey(entry[0]))
                continue;
            blockConfig.set("found", false, "pbAdvancements", "blocks", entry[0]);
            blockConfig.set("name", entry[1], "pbAdvancements", "blocks", entry[0]);
            blockConfig.set("player", "", "pbAdvancements", "blocks", entry[0]);
            itemList.put(entry[0], false);
        }
        Main.pbConfigFile.set("BlockListSize", itemList.size(), "pbAdvancements", "data");
        Main.pbConfigFile.set("blocksListSet", true, "pbAdvancements", "data");
        Main.pbConfigFile.set("players", null, "pbAdvancements");

    }
}