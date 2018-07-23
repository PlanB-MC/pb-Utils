package planB.mc.val.pbUtils.pbPlayerLogger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import planB.mc.val.Main;
import planB.mc.val.pbUtils.pbUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class pbGriefLogger {
    private static Main plugin;
    private static HashMap<UUID, GriefPlayer> players;
    private static int minutes;

    public pbGriefLogger(Main plugin) {
        this.plugin = plugin;
        Main.pbConfigFile.addDefault("time", 1, "pbGriefer", "data");
        Main.pbConfigFile.addDefault("enablePlugin", true, "pbGriefer", "data");
        if (!Main.pbConfigFile.getBoolean("enablePlugin", "pbGriefer", "data")) {
            pbUtils.log('C', "[pbGriefer]");
            return;
        }
        minutes = Main.pbConfigFile.getInt("time", "pbGriefer", "data");
        players = new HashMap<>();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            //System.out.println("Started inventory dump!");
            HashMap<Player, Set<String>> player = new HashMap<>();
            Bukkit.getOnlinePlayers().forEach(curPlayer -> {
                Set<String> items = new HashSet<>();
                items.add(curPlayer.getLocation().toString());
                curPlayer.getInventory().iterator().forEachRemaining(curItem -> {
                    if (curItem != null) items.add(curItem.toString());
                });
                player.put(curPlayer, items);
            });
            player.forEach((key, val) -> saveToFile(key.getName(), val));
            //System.out.println("Finished inventory dump!");
            //System.out.println("Started chest logging!");
            players.forEach((uuid, griefPlayer) -> {
                saveToFile(griefPlayer.name, griefPlayer.chests);
                griefPlayer.chests.clear();
            });
            //System.out.println("Finished chest logging!");
        }, 1200, 1200 * minutes);//24000 tick = 20min 1200
        Main.pbListenersEars.addListener(new onGriefer());
        pbUtils.log('I', "[pbGriefer]");
    }

    public static void saveToFile(String name, Set<String> data) {
        File dir = new File(plugin.getDataFolder(), "grief");
        if (!dir.exists()) dir.mkdir();
        File file = new File(plugin.getDataFolder(), "grief/" + name + ".txt");
        try {
            if (!file.exists()) file.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(file,true));
            bw.write(LocalTime.now().toString() + "\n");
            data.forEach(cur -> {
                try {
                    bw.write(cur);
                    bw.newLine();
                } catch (IOException e) {
                    System.out.println("unable write line \"" + cur + "\" for:" + name);
                    e.printStackTrace();
                }
            });
            bw.flush();
            bw.close();
        } catch (IOException e) {
            System.out.println("unable to create log file for :" + name);
            e.printStackTrace();
        }
    }

    public static void logChest(Player player, Location location) {
        GriefPlayer curPlayer = players.getOrDefault(player.getUniqueId(), new GriefPlayer(player.getName(), player.getUniqueId()));
        curPlayer.chests.add(location.toString());
        players.put(player.getUniqueId(), curPlayer);
    }

    public static void updateTime(String arg, CommandSender sender) {
        minutes = Integer.parseInt(arg);
        sender.sendMessage(ChatColor.AQUA + "time is set to " + arg + " minutes");
    }

    private static class GriefPlayer {
        String name;
        String UUID;
        Set<String> chests = new HashSet<>();

        public GriefPlayer(String name, UUID UUID) {
            this.name = name;
            this.UUID = UUID.toString();
        }

       /* @Override
        public String toString() {
            StringBuilder bob = new StringBuilder();
            bob.append(name + "\'" + UUID + "\'");
            chests.forEach(item -> bob.append("\n\t" + item));
            return bob.toString();
        }*/
    }
}
