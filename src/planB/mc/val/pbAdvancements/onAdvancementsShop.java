package planB.mc.val.pbAdvancements;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import planB.mc.val.Main;

import java.util.HashSet;
import java.util.Random;

public class onAdvancementsShop implements Listener {
    private static HashSet<String> cooldown = new HashSet<>();
    private Main plugin;
    private Random rand = new Random();

    public onAdvancementsShop(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractAtEntityEvent event) {
        try {
            if (event.getRightClicked().getCustomName().equals(Block.name)) {
                if (Block.blocksDone) {
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "hdb open " + event.getPlayer().getName());
                    event.setCancelled(true);
                } else {
                    if (cooldown.contains(event.getPlayer().getName()))
                        return;
                    printError();
                    cooldown.add(event.getPlayer().getName());
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(
                            plugin,
                            () -> cooldown.remove(event.getPlayer().getName()),
                            80L
                    );
                }
            }
        } catch (NullPointerException e) {
            //ignore
        }

    }

    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent event) {
        try {
            if (event.getEntity().getCustomName().equals(Block.name))
                printViolence();
        } catch (NullPointerException e) {
            //ignore
        }
    }

    private void printViolence() {
        String output = "";
        switch (rand.nextInt(12)) {
            case 0: {
                output = "Whoa what do you think your doing?";
                break;
            }
            case 1: {
                output = "The price stays the same buddy!";
                break;
            }
            case 2: {
                output = "Ok, ok, I get it but resorting to violence isn't the answer!";
                break;
            }
            case 3: {
                output = "If you're trying to get me to talk there is no way I will now <insert insult here>";
                break;
            }
            case 4: {
                output = "I once took an arrow to the knee. This is nothing...";
                break;
            }
            case 5: {
                output = "Keep trying...";
                break;
            }
            case 6: {
                output = "COPS! COOOOPS! SOMEONE CALL 911!";
                break;
            }
            case 7: {
                output = "The price just went up!";
                break;
            }
            case 8: {
                output = "Soft hands.";
                break;
            }
            case 9: {
                output = "Blame Matt.";
                break;
            }
            case 10: {
                output = "Blame Nick.";
                break;
            }
            case 11: {
                output = "Did Val crash the server again?";
                break;
            }
        }
        Bukkit.broadcastMessage("<" + ChatColor.RED + Block.name + ChatColor.WHITE + "> " + output);
    }

    private void printError() {
        String output = "";
        switch (rand.nextInt(10)) {
            case 0: {
                output = "You dare wake me! Be gone Puny human.";
                break;
            }
            case 1: {
                output = "Wha whaa what is this celestial form?";
                break;
            }
            case 2: {
                output = "Ik weet het niet; No sé; Nem tudom.";
                break;
            }
            case 3: {
                output = "YOU SHALL NOT PASS!";
                break;
            }
            case 4: {
                output = "The number you have dialed does not exist.";
                break;
            }
            case 5: {
                output = "Come talk to me when you have something worth talking about.";
                break;
            }
            case 6: {
                output = "Sorry, I'm not really in the mood...";
                break;
            }
            case 7: {
                output = "My time is not yet here. Be patient Young one.";
                break;
            }
            case 8: {
                output = "Blame Matt.";
                break;
            }
            case 9: {
                output = "Blame Nick.";
                break;
            }
        }
        Bukkit.broadcastMessage("<" + ChatColor.RED + Block.name + ChatColor.WHITE + "> " + output);
    }
}
