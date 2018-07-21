package planB.mc.val.ExtraOutputs;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import planB.mc.val.Main;
import planB.mc.val.pbUtils.pbUtils;

public class onPoopable {
    public onPoopable() {
        Main.pbConfigFile.addDefault("enablePlugin", true, "pbExtraOutput", "glassPlaced");
        Main.pbConfigFile.addDefault("amount", 0, "pbExtraOutput", "glassPlaced");
        if (Main.pbConfigFile.getBoolean("enablePlugin", "pbExtraOutput", "glassPlaced")) {
            pbUtils.log('I', "[poopable]", "pbExtraOutput", "glassPlaced");
            Main.pbListenersEars.addListener(new events());
        }

    }

    class events implements Listener {
        @EventHandler
        public void onGlassPlaced(BlockPlaceEvent event) {
            if (event.getBlockPlaced().getType().toString().contains("GLASS")) {
                int amount = Main.pbConfigFile.getInt("amount", "pbExtraOutput", "glassPlaced") +1;
                Main.pbConfigFile.set("amount", amount, "pbExtraOutput", "glassPlaced");
            }
        }
    }

}
