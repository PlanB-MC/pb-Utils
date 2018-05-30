package planB.mc.val.pbUtils;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import planB.mc.val.Main;

public class pbListeners {
    private static Main plugin;

    public pbListeners(Main main) {
        plugin = main;
    }

    public void addListener(Listener newEvent) {
        plugin.getServer().getPluginManager().registerEvents(newEvent, plugin);
        pbUtils.log("[pbListener]", "Event Added!", newEvent.toString());
    }

    public void removeListener(Listener newEvent) {
        HandlerList.unregisterAll(newEvent);
        pbUtils.log("[pbListener]", "Event Removed!", newEvent.toString());
    }

    public void removeAllListener() {
        HandlerList.unregisterAll();
        pbUtils.log("[pbListener]", "All Events Removed!");
    }
}
