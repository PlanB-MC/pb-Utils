package planB.mc.val.unreleased.pbChests;

import planB.mc.val.Main;
import planB.mc.val.pbUtils.pbUtils;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Portable Crafting Bench
 * Big Chest
 * Make Crafting bench save content
 * Better Ender chest
 */

public class pbChests {
    // private static HashMap<Location, lilChest> chests;
    private static int lastID;
    //public static Inventory myInventory;
    private static Main plugin;
    private static ObjectInputStream ois;
    private static ObjectOutputStream oos;

    public pbChests(Main main) {
        Main.pbConfigFile.addDefault("enablePlugin", false, "pbChests");
        if (!Main.pbConfigFile.getBoolean("enablePlugin", "pbChests")) {
            pbUtils.log('C', "[pbChests]");
            return;
        }
        //   plugin = main;
        //    chests = new HashMap<>();

        // loadChests();

        Main.pbListenersEars.addListener(new onChests());
        pbUtils.log('I', "[pbChests]");
    }

/*
    public static boolean isChest(Location loc) {
        return chests.containsKey(loc);
    }

    public static void addChest(Block block, Player player) {
        lilChest newChest = new lilChest(player, block, getNewID());
        chests.put(block.getLocation(), newChest);
        lastID++;
    }

    private static int getNewID() {
        return lastID + 1;
    }

    public static Inventory open(Location loc) {
        return chests.get(loc).getInv();
    }

    public static void loadChests() {
        pbUtils.log("[pbChests]", "Loading chests");
        chests.clear();
        try {
            File dir = new File(plugin.getDataFolder(), "/chests");
            if (!dir.exists()) dir.mkdir();
            pbUtils.log("[pbChests]", dir.getAbsolutePath());
            for (final File fileEntry : dir.listFiles()) {
                FileInputStream fis = new FileInputStream(fileEntry);
                ois = new ObjectInputStream(fis);
                lilChest newChest = (lilChest) ois.readObject();
                chests.put(newChest.getLoc(), newChest);
                pbUtils.log("[pbChests]", "added chest!", fileEntry.getName(), String.valueOf(chests.size()));
                lastID = newChest.getID() > lastID ? newChest.getID() : lastID;
            }
            ois.close();
        } catch (FileNotFoundException e) {
            pbUtils.log("[pbChests]", "failed to create object");
        } catch (IOException e) {
            pbUtils.log("[pbChests]", "failed to close saver");
        } catch (ClassNotFoundException e) {
            pbUtils.log("[pbChests]", "Couldn't find associated class");
        }

    }

    public static void saveChests() {
        pbUtils.log("[pbChests]", "Saving chests");
        File dir = new File(plugin.getDataFolder(), "/chests");
        if (!dir.exists()) dir.mkdir();
        pbUtils.log("[pbChests]", dir.getAbsolutePath());

        try {
            chests.forEach((key, chest) -> {
                try {
                    if (chest.getChanged() == 0) return;
                    if (chest.getChanged() == 2) {
                        pbUtils.log("[pbChests]", dir.getAbsolutePath(), chest.getID() + ".pbChest");
                        if (new File(dir.getAbsolutePath() + chest.getID() + ".pbChest").delete()) {
                            pbUtils.log("[pbChests]", "Deleting", "File deleted successfully");
                        } else {
                            pbUtils.log("[pbChests]", "Deleting", "Failed to delete the file");
                        }
                        return;
                    }
                    pbUtils.log("[pbChests]", dir.getAbsoluteFile() + "/" + chest.getID() + ".pbChest");
                    FileOutputStream fos = new FileOutputStream(dir.getAbsoluteFile() + "/" + chest.getID() + ".pbChest");
                    oos = new ObjectOutputStream(fos);
                    oos.writeObject(chest);
                } catch (FileNotFoundException e) {
                    pbUtils.log("[pbChests]", "failed to create object");
                } catch (IOException e) {
                    pbUtils.log("[pbChests]", "failed to make new object");
                    e.printStackTrace();
                }
            });
            oos.close();
        } catch (IOException e) {
            pbUtils.log("[pbChests]", "failed to close saver");
            e.printStackTrace();
        }
    }
*//*


}

class lilChest implements Serializable {
    private int changed;// 0: not changed; 1: changed; 2: deleted;
    private int chestID;

    private String World;
    private double x, y, z;
    private float yaw, pitch;
    private Vector direction;


    private Inventory inv;


    public lilChest(Player owner, Block block, int ID) {
        Location locTemp = block.getLocation();
        World = block.getWorld().getName();
        x = locTemp.getX();
        y = locTemp.getY();
        z = locTemp.getZ();
        yaw = locTemp.getYaw();
        direction = locTemp.getDirection();
        pitch = locTemp.getPitch();

        this.chestID = ID;
        changed = 1;
        inv = Bukkit.createInventory(owner, 54, ChatColor.AQUA + owner.getName() + "'s little box");
    }*/
}
