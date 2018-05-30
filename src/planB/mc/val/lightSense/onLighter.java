package planB.mc.val.lightSense;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import planB.mc.val.Main;

import java.util.ArrayList;

public class onLighter implements Listener {
    private static int radius = 10;
    private static ArrayList<Block> listAirBlocks;
    private boolean cooldown = false;
    private Main plugin;
    private long refreshDelay;

    public onLighter(Main plugin) {
        this.plugin = plugin;
    }

    public static void setRadius(int radius) {
        onLighter.radius = radius;
        listAirBlocks = new ArrayList<>();
    }

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event) {

        //System.out.println("test 1");
        if (!pbLighter.isTorched)
            return;

        try {
            //System.out.println(event.getPlayer().getName());
            if (!pbLighter.playerEnabled.containsKey(event.getPlayer().getName())) {
                return;
            }
            if (!pbLighter.playerEnabled.get(event.getPlayer().getName())) {
                return;
            }
        } catch (NullPointerException e) {
            System.out.println("lights error");
        }

        if (cooldown) return;
        cooldown = true;
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            public void run() {
                cooldown = false;
            }
        }, pbLighter.refreshDelay);

        //System.out.println("test 2");
        Location locFrom = event.getFrom();
        Location locTo = event.getTo();
        if (!locFrom.getBlock().getLocation().equals(locTo.getBlock().getLocation())) {
            //System.out.println("test 3");
            World world = event.getTo().getWorld();
            Location locPlayerN = new Location(world, locTo.getBlockX(), locTo.getBlockY(), locTo.getBlockZ());
            listAirBlocks = new ArrayList<>();
            for (int y = -1 * (radius / 2); y < (radius / 2); y++) {
                for (int x = -1 * radius; x < radius; x++) {
                    for (int z = -1 * radius; z < radius; z++) {
                        Location curBlockLoc = new Location(world, locPlayerN.getBlockX() + x, locPlayerN.getBlockY() + y, locPlayerN.getBlockZ() + z);
                        Block curBlock = Bukkit.getServer().getWorld(world.getName()).getBlockAt(curBlockLoc);
                        if (curBlock.getType().equals(Material.AIR)) {
                            Block blockBelow = Bukkit.getServer().getWorld(world.getName()).getBlockAt(curBlockLoc.subtract(0, 1d, 0));
                            if (!blockBelow.getType().equals(Material.AIR))
                                listAirBlocks.add(curBlock);
                        }
                    }
                }
            }
        }
        try {
            if (listAirBlocks.size() == 0)
                return;
        } catch (NullPointerException e) {
        }
        listAirBlocks.forEach(b -> pbLighter.showParticle(event.getPlayer(), b));
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        HeadDatabaseAPI api = new HeadDatabaseAPI();
        ItemStack itemHead = event.getItemInHand();
        // pbUtils.log(api.getItemID(itemHead));
        try {
            if (api.getItemID(itemHead).equals("6868"))
                event.getBlockPlaced().setType(Material.GLOWSTONE);
        } catch (NullPointerException e) {
            //ignor
        }
    }
}