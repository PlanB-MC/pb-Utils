package planB.mc.val.pbLights.pbHeadLights;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.inventory.ItemStack;
import planB.mc.val.pbUtils.pbUtils;

public class onHeadLights implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        //Checking if the item held in has is the correct head
        HeadDatabaseAPI api = new HeadDatabaseAPI();
        ItemStack itemHead = event.getItemInHand();
        try {
            String id = api.getItemID(itemHead);
            pbUtils.log("[onHeadLights]", id);
            if (HeadLight.contains(id)) {
                Location loc = event.getBlockPlaced().getLocation();
                if (!loc.subtract(0, 1, 0).getBlock().getType().isSolid()) {
                    pbUtils.log("[onHeadLights]", "Cant place Candle on wall or what ever this is");
                    return;
                }
                ArmorStand as = loc.getWorld().spawn(loc.subtract(-0.5, 0.5, -0.5), ArmorStand.class);
                as.setBasePlate(false);
                as.setArms(false);
                as.setVisible(false);
                as.setInvulnerable(false);
                as.setCanPickupItems(false);
                as.setGravity(false);
                as.setSmall(false);
                as.setHelmet(api.getItemHead(id));
                pbUtils.log("[onHeadsLights]", as.toString());

                //pbUtils.log("[onHeadsLights]", api.getItemHead("1776").getItemMeta().toString());
                event.getBlockPlaced().setType(Material.TORCH);
                event.getBlockPlaced().getState().update();
                //event.setCancelled(true);
                pbUtils.log("[onHeadsLights]", "light placed");
            }
        } catch (NullPointerException e) {
            pbUtils.log("[onHeadLights]", "2");
        } catch (Exception e) {
            //pbUtils.log("[onHeadLights]", "2");
        }
    }

    @EventHandler
    public void onPlayerArmorStandManipulateEvent(PlayerArmorStandManipulateEvent event) {
        final ArmorStand damaged = event.getRightClicked();
        HeadDatabaseAPI api = new HeadDatabaseAPI();
        if (!HeadLight.contains(api.getItemID(damaged.getHelmet())))
            return;
        String ID = api.getItemID(damaged.getHelmet());
        damaged.getLocation().add(0, 1.5, 0).getBlock().setType(Material.AIR);
        damaged.remove();
        damaged.getWorld().dropItem(damaged.getLocation().add(0,2,0), api.getItemHead(ID));
        pbUtils.log("[onHeadsLights]", "light removed");
        event.setCancelled(true);
    }
}
