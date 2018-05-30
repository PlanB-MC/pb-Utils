package planB.mc.val.pbShowodmere;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.spigotmc.event.entity.EntityDismountEvent;
import planB.mc.val.Main;
import planB.mc.val.pbUtils.pbUtils;

import java.util.HashSet;

public class onZHorse implements Listener {
    private boolean cooldown = false;
    private Main plugin;

    public onZHorse(Main plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        try {
            HeadDatabaseAPI api = new HeadDatabaseAPI();
            ItemStack itemHand = event.getPlayer().getInventory().getItemInMainHand();
            //pbUtils.log(api.getItemID(itemHand));
            if (api.getItemID(itemHand).equals("2913")) {
                event.setCancelled(true);
                if (cooldown) return;
                cooldown = true;
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    public void run() {
                        cooldown = false;
                    }
                }, 80L);
                HashSet<Material> spawnBlocks = new HashSet<>();
                spawnBlocks.add(Material.AIR);
                Location loc = event.getPlayer().getTargetBlock(spawnBlocks, 6).getLocation().add(0d, 1d, 0d);
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),
                        "summon zombie_horse " + loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ()
                                + " {Tame:1, SaddleItem:{id:saddle,Count:1},CustomName:Shadowmere}");
                pbUtils.log("[onZHorse]", event.getPlayer().getName() + " spawned Shadowmere.");
            }
        } catch (NullPointerException e) {
        }
    }

    @EventHandler
    public void onEntityDismountEvent(EntityDismountEvent event) {
        if (event.getDismounted().getCustomName().equals("Shadowmere"))
            event.getDismounted().remove();
    }
}
