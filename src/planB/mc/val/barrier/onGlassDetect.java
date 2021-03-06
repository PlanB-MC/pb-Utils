package planB.mc.val.barrier;

import org.bukkit.configuration.MemorySection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import planB.mc.val.Main;

public class onGlassDetect implements Listener {
    @EventHandler
    public void onGlassPlaced(BlockPlaceEvent event) {
        if (event.getBlockPlaced().getType().toString().contains("GLASS")) {
            int amount = Main.pbConfigFile.getInt("amount", "pbBarrier") + 1;
            Main.pbConfigFile.set("amount", amount, "pbBarrier");
            String pUUID = event.getPlayer().getUniqueId().toString();
            MemorySection player = (MemorySection) Main.pbConfigFile.get(pUUID, "pbBarrier", "players");
            ActivePlayer curPLayer = null;
            if (player != null)
                curPLayer = new ActivePlayer(player.getInt("score"), player.getString("name"), player.getString("uuid"));
            else
                curPLayer = new ActivePlayer(0, event.getPlayer().getName(), pUUID);

            Main.pbConfigFile.set("name", curPLayer.name, "pbBarrier", "players", curPLayer.UUID);
            Main.pbConfigFile.set("uuid", curPLayer.UUID, "pbBarrier", "players", curPLayer.UUID);
            Main.pbConfigFile.set("score", curPLayer.score, "pbBarrier", "players", curPLayer.UUID);
        }
    }

    class ActivePlayer {
        int score;
        String name;
        String UUID;

        public ActivePlayer(int score, String name, String UUID) {
            //System.out.println(score + " " + name + " " + UUID);
            this.score = score + 1;
            this.name = name;
            this.UUID = UUID;
        }
    }
}
