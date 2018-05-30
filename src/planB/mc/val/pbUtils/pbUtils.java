package planB.mc.val.pbUtils;

import org.bukkit.Bukkit;
import planB.mc.val.Main;

import java.util.logging.Level;

public class pbUtils {
    private static Main plugin;

    public pbUtils(Main main) {
        plugin = main;
    }

    public static void doBlame(String name) {
        try {
            String playerName = name.toUpperCase();
            switch (Main.random.nextInt(6)) {
                case 0: {
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),
                            "title @a title [{\"text\":\"Come on " + playerName + "\"}]");
                    break;
                }
                case 1: {
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),
                            "title @a title [{\"text\":\"Seriously " + name + "...\"}]");
                    break;
                }
                case 2: {
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),
                            "title @a title [{\"text\":\"This ain't working " + name + "\"}]");
                    break;
                }
                case 3: {
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),
                            "title @a title [{\"text\":\"Dude... " + name + "??\"}]");
                    break;
                }
                case 4: {
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),
                            "title @a title [{\"text\":\"#GivingUpOn " + name + "\"}]");
                    break;
                }
                case 5: {
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),
                            "title @a title [{\"text\":\"" + name + ".exe has Stopped Responding\"}]");
                    break;
                }
            }
            switch (Main.random.nextInt(5)) {
                case 0: {
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),
                            "title @a subtitle [{\"text\":\"Get it together\",\"bold\":true}]");
                    break;
                }
                case 1: {
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),
                            "title @a subtitle [{\"text\":\"Wana try that again?\",\"bold\":true}]");
                    break;
                }
                case 2: {
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),
                            "title @a subtitle [{\"text\":\"Please... Stop...\",\"bold\":true}]");
                    break;
                }
                case 3: {
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),
                            "title @a subtitle [{\"text\":\"the server is on :fire::bangbang:\",\"bold\":true}]");
                    break;
                }
                case 4: {
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),
                            "title @a subtitle [{\"text\":\"Go sit in the corner...\",\"bold\":true}]");
                    break;
                }
            }
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),
                    "tellraw @a {\"text\":\"\",\"extra\":[{\"text\":\"Please note that \",\"color\":\"gold\"},\n" +
                            "{\"text\":\"" + playerName + "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"Damn it " + playerName + "\"}}," +
                            "{\"text\":\" is to blame for this!\",\"color\":\"gold\"}]}");
        } catch (Exception e) {
        }
    }

    public static void log(String... msg){
        String finalMsg = "";
        for (String cur: msg){
            finalMsg += cur + ": ";
        }
        plugin.getLogger().log(Level.INFO,finalMsg);
    }

    public static void log(char selector,String... msg){
        String finalMsg = "";
        for (String cur: msg){
            finalMsg += cur + " ";
        }
        switch (selector){
            case 'I':{//initializing
                finalMsg += ": Initializing successful!";
                break;
            }
            case 'E':{//Enabled
                finalMsg += ": Functionality has been Enabled!";
                break;
            }
            case 'D':{//Disabled
                finalMsg += ": Functionality has been Disabled!";
                break;
            }
            case 'C':{//Canceled
                finalMsg += ": Functionality Startup has been Canceled: DISABLED!";
                break;
            }
        }
        plugin.getLogger().log(Level.INFO,finalMsg);
    }

}
