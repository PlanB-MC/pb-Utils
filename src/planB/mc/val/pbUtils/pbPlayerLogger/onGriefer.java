package planB.mc.val.pbUtils.pbPlayerLogger;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class onGriefer implements Listener {
    @EventHandler
    public void checkChest(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getClickedBlock().getType().equals(Material.CHEST)) {
                pbGriefLogger.logChest(event.getPlayer(), event.getClickedBlock().getLocation());
            }
        }
    }

}
