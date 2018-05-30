package planB.mc.val.pbAdvancements;

import org.bukkit.Bukkit;
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
        if (event.getRightClicked().getCustomName().equals("Hubert")) {
            if (Block.blocksDone) {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "hdb open " + event.getPlayer().getName());
            } else {
                if (cooldown.contains(event.getPlayer().getName()))
                    return;
                printError();
                cooldown.add(event.getPlayer().getName());
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    public void run() {
                        cooldown.remove(event.getPlayer().getName());
                    }
                }, 80L);
            }
        }
    }

    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent event){
        if (event.getEntity().getCustomName().equals("Hubert"))
            printViolence();
    }

    private void printViolence(){
        switch (rand.nextInt(12)) {
            case 0: {
                Bukkit.broadcastMessage("Whoa what do you think your doing?");
                break;
            }
            case 1: {
                Bukkit.broadcastMessage("The price stays the same buddy!");
                break;
            }
            case 2: {
                Bukkit.broadcastMessage("Ok, ok, I get it but resorting to violence isn't the answer!");
                break;
            }
            case 3: {
                Bukkit.broadcastMessage("If you're trying to get me to talk there is no way I will now <insert insult here>");
                break;
            }
            case 4: {
                Bukkit.broadcastMessage("I once took an arrow to the knee. This is nothing...");
                break;
            }
            case 5: {
                Bukkit.broadcastMessage("Keep trying...");
                break;
            }
            case 6: {
                Bukkit.broadcastMessage("COPS! COOOOPS! SOMEONE CALL 911!");
                break;
            }
            case 7: {
                Bukkit.broadcastMessage("The price just went up!");
                break;
            }
            case 8: {
                Bukkit.broadcastMessage("Soft hands.");
                break;
            }
			case 9: {
                Bukkit.broadcastMessage("Blame Matt.");
                break;
            }
			case 10: {
                Bukkit.broadcastMessage("Blame Nick.");
                break;
            }
			case 11: {
                Bukkit.broadcastMessage("Did Val crash the server again?");
                break;
            }
        }
    }

    private void printError() {
        switch (rand.nextInt(10)) {
            case 0: {
                Bukkit.broadcastMessage("You dare wake me! Be gone Puny human.");
                break;
            }
            case 1: {
                Bukkit.broadcastMessage("Wha whaa what is this celestial form?");
                break;
            }
            case 2: {
                Bukkit.broadcastMessage("Ik weet het niet; No sé; Nem tudom.");
                break;
            }
            case 3: {
                Bukkit.broadcastMessage("YOU SHALL NOT PASS!");
                break;
            }
            case 4: {
                Bukkit.broadcastMessage("The number you have dialed does not exist.");
                break;
            }
            case 5: {
                Bukkit.broadcastMessage("Come talk to me when you have something worth talking about.");
                break;
            }
            case 6: {
                Bukkit.broadcastMessage("Sorry, I'm not really in the mood...");
                break;
            }
            case 7: {
                Bukkit.broadcastMessage("My time is not yet here. Be patient Young one.");
                break;
            }
			case 8: {
                Bukkit.broadcastMessage("Blame Matt.");
                break;
            }
			case 9: {
                Bukkit.broadcastMessage("Blame Nick.");
                break;
            }
        }
    }
}
