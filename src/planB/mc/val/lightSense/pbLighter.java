package planB.mc.val.lightSense;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import planB.mc.val.Main;
import planB.mc.val.pbUtils.pbUtils;

import java.util.HashMap;
import java.util.Set;

public class pbLighter {
    public static boolean isTorched;
    public static long refreshDelay;
    public static Main plugin;
    public static HashMap<String, Boolean> playerEnabled = new HashMap<>();
    private static onLighter onLighter;
    private static double r, g, b;
    private static EternalLightAPI EAPI;

    public pbLighter(Main main) {
        Main.pbConfigFile.addDefault("enablePlugin",true,"pbLighter");
        Main.pbConfigFile.addDefault("cooldown",5,"pbLighter");
        if (!Main.pbConfigFile.getBoolean("enablePlugin","pbLighter")){
            pbUtils.log('C',"[pbLighter]");
            return;
        }
        setCooldown(Main.pbConfigFile.getInt("cooldown","pbLighter"));
        isTorched = true;
        plugin = main;
        onLighter = new onLighter(plugin);
        b = 0d;
        EAPI = new EternalLightAPI();
        Main.pbListenersEars.addListener(onLighter);
        pbUtils.log('I',"[pbLighter]");
    }


    public static void showParticle(Player player, Block block) {
        //check block validity
        //System.out.println("test 1");
        Block blockBelow = Bukkit.getServer().getWorld(block.getWorld().getName()).getBlockAt(block.getLocation().subtract(0, 1d, 0));
        if (blockBelow.getType() == Material.AIR) return;
        int opacity = getBlockOpacity(blockBelow.getType());
        if (opacity > 0) return;

        if (blockBelow.getType().name().contains("STAIR"))
            return;
        if (blockBelow.getType().name().contains("SLAB"))
            return;
        if (getBlockHeight(blockBelow) == 0.5) return;
        //System.out.println(blockBelow.getType());
        //check light
        Location loc = block.getLocation();
        if (block.getLightFromSky() > 7) {
            if (block.getLightFromBlocks() > 7) {
                r = 0d;
                g = 1d;
            } else {
                r = 1d;
                g = 0.55d;
            }
        } else {
            if (block.getLightFromBlocks() > 7) {
                r = 0d;
                g = 1d;
            } else {
                r = 1d;
                g = 0d;
            }
        }
        player.spawnParticle(
                Particle.REDSTONE,
                loc.getX() + 0.5, loc.getY(), loc.getZ() + 0.5
                , 0, r + 0.0001, g, b, 1
        );
    }

    private static int getBlockOpacity(Material material) {
        String mat = material.name().toLowerCase();
        Set<ListItem> blocks = EAPI.getBlocks();
        for (ListItem s : blocks) {
            if (mat.contains(s.getTag().toLowerCase())) {
                return s.getLevel();
            }
        }
        return -1;
    }

    private static double getBlockHeight(Block block) {
        Material material = block.getType();
        String n = material.name().toLowerCase();
        byte data = block.getData();
        if (!n.contains("double") && (n.contains("step") || n.contains("slab"))) {
            if (data < 8) return .5;
        }
        return 1;
    }

    public static void toggle(CommandSender sender, boolean b) {
        playerEnabled.put(sender.getName(), b);
        pbUtils.log("[pbLighter]",sender.getName() + " has set their lights to " + b);
    }

    public static void printAll() {
        Bukkit.broadcastMessage("Listing all the Players that are consuming power");
        playerEnabled.forEach((k, v) -> Bukkit.broadcastMessage(k + " : " + v));
    }

    public static void setCooldown(int time){
        refreshDelay = time;
        Main.pbConfigFile.set("cooldown",time,"pbLighter");
        pbUtils.log("[pbLighter]","Changed cooldown time to ", String.valueOf(time));
    }
}
