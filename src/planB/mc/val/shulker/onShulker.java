package planB.mc.val.shulker;

import org.bukkit.entity.Player;
import org.bukkit.entity.Shulker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class onShulker implements Listener {

    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        if (event.getEntity() instanceof Shulker && event.getEntity().getKiller() instanceof Player) {
            pbShulker.inc(event);
        }
    }


}

