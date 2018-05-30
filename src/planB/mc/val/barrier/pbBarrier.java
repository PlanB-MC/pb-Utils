package planB.mc.val.barrier;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import planB.mc.val.Main;
import planB.mc.val.lightSense.pbLighter;
import planB.mc.val.pbUtils.pbUtils;

import java.util.ArrayList;

public class pbBarrier {
    public static Material prefTool = null;
    public static long effectTime = 20;
    private static onBarrierDetect onBarrierDetect;
    private static Main plugin;
    private boolean cooldown = false;

    public pbBarrier(Main main) {
        Main.pbConfigFile.addDefault("enablePlugin", true, "pbBarrier");
        if (!Main.pbConfigFile.getBoolean("enablePlugin", "pbBarrier")) {
            pbUtils.log('C', "[pbBarrier]");
            return;
        }
        plugin = main;
        onBarrierDetect = new onBarrierDetect(main);
        Main.pbListenersEars.addListener(onBarrierDetect);
        setupHeadVision();
        pbUtils.log('I', "[pbBarrier]");
    }

    public static void setEffectTime(int effectTimeSec) {
        effectTime = effectTimeSec;
        pbUtils.log("[pbBarrier]", "Barrier shown time set to: " + effectTimeSec);
    }

    public static void setPrefTool(CommandSender player) {
        if (prefTool != null) {
            pbUtils.log("[pbBarrier]", "Setting of PrefTool failed!");
            return;
        }
        player.sendMessage(ChatColor.AQUA + "A magic Air particle falls on your hand");
        prefTool = Material.AIR;
        pbUtils.log("[pbBarrier]", "Setting of PrefTool: AIR!");
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            prefTool = null;
            pbUtils.log("[pbBarrier]", "Setting of PrefTool: Null!");
            player.sendMessage(ChatColor.AQUA + "The particle falls to the ground");
        }, 100L);
    }

    public static ArrayList<Block> getBlocks(World world, Location loc, int radius) {
        ArrayList<Block> blockList = new ArrayList<>();
        for (int y = -1 * radius; y < radius; y++) {
            for (int x = -1 * radius; x < radius; x++) {
                for (int z = -1 * radius; z < radius; z++) {
                    Location curBlockLoc = new Location(world, loc.getX() + x, loc.getY() + y, loc.getZ() + z);
                    Block curBlock = Bukkit.getServer().getWorld(world.getName()).getBlockAt(curBlockLoc);
                    if (curBlock.getType().equals(Material.BARRIER))
                        blockList.add(curBlock);
                }
            }
        }
        return blockList;
    }

    private void setupHeadVision() {
        HeadDatabaseAPI api = new HeadDatabaseAPI();
        double r = 241d;
        double g = 0d;
        double b = 249d;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () ->
                Bukkit.getOnlinePlayers().forEach(p -> {
                    try {
                        if (cooldown) return;
                        ItemStack itemHead = p.getInventory().getHelmet();
                       // pbUtils.log(api.getItemID(itemHead));
                        if (api.getItemID(itemHead).equals("3229")) {
                            ArrayList<Block> blocks = getBlocks(p.getWorld(), p.getLocation(), 10);
                            if (blocks.isEmpty()) {
                                p.sendMessage(ChatColor.AQUA + "The Barrier Vision is resting");
                                cooldown = true;
                                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> cooldown = false, 200L);
                            } else
                                blocks.forEach(block -> {
                                    Location loc = block.getLocation();
                                    p.spawnParticle(
                                            Particle.REDSTONE,
                                            loc.getX() + 0.5, loc.getY(), loc.getZ() + 0.5
                                            , 0, r + 0.0001, g, b, 1
                                    );
                                });
                        }
                    } catch (NullPointerException e) {
                        //ignor
                    }
                }), 0, 13L);
    }
}