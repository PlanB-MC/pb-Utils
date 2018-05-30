package planB.mc.val.pbAdvancements;

import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import planB.mc.val.Main;

import java.util.logging.Level;

public class onBlockList implements Listener {
    private Main plugin;

    public onBlockList(Main instance) {
        plugin = instance;
    }

    @EventHandler
    public void onEntityPickupItemEvent(EntityPickupItemEvent event) {
        try {
            Player player = (Player) event.getEntity();
            Item item = event.getItem();
            //Do the main thingy
            String[] blockNameList = item.getName().split(".");
            String compare = blockNameList[blockNameList.length - 1];

            if (!Block.itemExistFound(compare)) {
                Block.put(compare, true, player.getName());
                //Show title with subtitle of discovery
                Bukkit.getServer().broadcastMessage(player.getDisplayName() + " has discovered a new Block!");
                String title = "New Discovery!";
                String subTitle = compare;
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),
                        "title @a title [{\"text\":\"" + title + "\"}]");
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),
                        "title @a subtitle [{\"text\":\"" + subTitle + "\",\"bold\":true}]");
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),
                        "playsound entity.player.levelup ambient @a 0 0 0 1 1 1");
            }
        } catch (NullPointerException e) {
            plugin.getLogger().log(Level.WARNING, "Block is not registered!");
        }

    }
}
