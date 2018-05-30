package planB.mc.val.unreleased.pbChests;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import planB.mc.val.pbUtils.pbUtils;

public class onChests implements Listener {

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        try {
           /* if (pbChests.isChest(event.getClickedBlock().getLocation())) {
                event.setCancelled(true);
                event.getPlayer().getWorld().playSound(event.getClickedBlock().getLocation(), Sound.BLOCK_CHEST_OPEN,1F,1F);
                event.getPlayer().openInventory(pbChests.open(event.getClickedBlock().getLocation()));
            }*/
        } catch (NullPointerException e) {
            pbUtils.log("could not find the head you were looking for");
        }
        /*try {
            HeadDatabaseAPI api = new HeadDatabaseAPI();
            ItemStack itemHand = event.getPlayer().getInventory().getItemInMainHand();
            pbUtils.log(api.getItemID(itemHand));
            if (api.getItemID(itemHand).equals("6082")) {
                if (event.getAction().compareTo(Action.RIGHT_CLICK_BLOCK) == 0 || event.getAction().compareTo(Action.RIGHT_CLICK_AIR) == 0) {
                    event.setCancelled(true);
                    event.getPlayer().openInventory(pbChests.myInventory);
                }
            }
        } catch (NullPointerException e) {
            pbUtils.log("could not find the head you were looking for");
        }*/
    }

    @EventHandler
    public void onHeadBreak(BlockBreakEvent event) {
        HeadDatabaseAPI api = new HeadDatabaseAPI();
        Location loc = event.getBlock().getLocation();
        if (event.getBlock().getType().equals(Material.SKULL)) {
            //delete entry. pop chest
        }
    }

    @EventHandler
    public void onHeadPlace(BlockPlaceEvent event) {
        HeadDatabaseAPI api = new HeadDatabaseAPI();
        ItemStack itemHead = event.getItemInHand();
        pbUtils.log("[onHeadLights]", api.getItemID(itemHead));
        try {
            if (api.getItemID(itemHead).equals("1193")) {
               // pbChests.addChest(event.getBlockPlaced(),event.getPlayer());
            }
        } catch (NullPointerException e) {
            pbUtils.log("could not find the head you were looking for");
        }
    }
}
