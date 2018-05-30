package planB.mc.val.barrier;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import planB.mc.val.Main;

import java.util.ArrayList;

public class onBarrierDetect implements Listener {
    private static int radius = 10;
    private Main main;
    private long effectTime;

    public onBarrierDetect(Main main) {
        this.main = main;
    }



    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        try {
            if (event.getClickedBlock().getType().equals(Material.BARRIER)) {
                if (event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.DIAMOND_PICKAXE)) {
                    event.getClickedBlock().setType(Material.AIR);
                } else if (event.getPlayer().getInventory().getItemInMainHand().getType().equals(pbBarrier.prefTool)) {
                    event.getClickedBlock().setType(Material.AIR);
                }
            }
        } catch (NullPointerException e) {
        }
    }

    @EventHandler
    public void onPotionSplashEvent(PotionSplashEvent event) {
        ThrownPotion potion = event.getPotion();
        for (PotionEffect potE : potion.getEffects()) {
            if (potE.getType().getName().equals(PotionEffectType.NIGHT_VISION.getName())) {
                Location loc = event.getEntity().getLocation();
                //getting the blocks
                ArrayList<Block> blockList = pbBarrier.getBlocks(event.getEntity().getWorld(), loc,radius);
                //set glass
                blockList.forEach(curBlock -> curBlock.setType(Material.GLASS));
                //set delayRevert
                BukkitTask revert = new BukkitRunnable() {
                    @Override
                    public void run() {
                        blockList.forEach(curBlock -> {
                            if (curBlock.getType().equals(Material.GLASS))
                                curBlock.setType(Material.BARRIER);
                        });
                    }
                }.runTaskLater(main, pbBarrier.effectTime * 20L);
                break;
            }
        }
    }
}
