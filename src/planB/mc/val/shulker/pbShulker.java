package planB.mc.val.shulker;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Shulker;
import org.bukkit.event.entity.EntityDeathEvent;
import planB.mc.val.Main;
import planB.mc.val.pbUtils.pbUtils;

//https://www.spigotmc.org/threads/ender-signal-target.111834/
public class pbShulker {
    public static boolean isDone;
    private static int iShulksNeeded;
    private static int iShulksFound;
    private static onShulker onShulker;

    public pbShulker() {
        //registers configs
        Main.pbConfigFile.addDefault("enablePlugin", true, "pbShulker");
        Main.pbConfigFile.addDefault("need", 1000, "pbShulker", "shulkers");
        Main.pbConfigFile.addDefault("found", 0, "pbShulker", "shulkers");
        if (!Main.pbConfigFile.getBoolean("enablePlugin", "pbShulker")) {
            pbUtils.log('C', "[pbShulker]");
            return;
        }
        iShulksFound = (int) Main.pbConfigFile.get("found", "pbShulker", "shulkers");
        iShulksNeeded = (int) Main.pbConfigFile.get("need", "pbShulker", "shulkers");
        if (iShulksNeeded == iShulksFound)
            isDone = true;
        else
            isDone = false;
        onShulker = new onShulker();
        Main.pbListenersEars.addListener(onShulker);
        pbUtils.log('I', "[pbShulker]");
    }

    public static void inc(EntityDeathEvent event) {
        if (isDone)
            return;
        iShulksFound++;
        System.out.println(iShulksFound);
        Main.pbConfigFile.set("found", iShulksFound, "pbShulker", "shulkers");
        if (iShulksFound == iShulksNeeded) {
            isDone = true;
            pbUtils.log("[pbShulker]", "Shulker condition has been met.");
            Shulker mob = (Shulker) event.getEntity();
            Location loc = mob.getEyeLocation();
            World world = mob.getWorld();
            String stringWorld = world.getName();
            Block blockSpawner = Bukkit.getServer().getWorld(stringWorld).getBlockAt(loc);
            blockSpawner.setType(Material.MOB_SPAWNER);
            CreatureSpawner spawner = (CreatureSpawner) blockSpawner.getState();
            spawner.setSpawnedType(EntityType.SHULKER);
            spawner.update();
            pbUtils.log("[pbShulker]", "Shulker spawner has been placed!");
            Main.pbConfigFile.set("pluginEnabled", false, "pbShulker");
            Main.pbListenersEars.removeListener(onShulker);
        }
        return;
    }

    public static String getRemainder() {
        return String.format("You Found %s out of %s. %s%% done", iShulksFound, iShulksNeeded, (iShulksFound * 100.00) / iShulksNeeded);
    }

    public static void reset() {
        Main.pbConfigFile.set("pluginEnabled", true, "pbShulker");
        Main.pbConfigFile.set("need", 1000, "pbShulker", "shulkers");
        Main.pbConfigFile.set("found", 0, "pbShulker", "shulkers");
        iShulksFound = 0;
        iShulksNeeded = 400;
        try {
            Main.pbListenersEars.removeListener(onShulker);
        } catch (Exception e) {
            System.out.println("no listner to remove");
        }
        Main.pbListenersEars.addListener(onShulker);
        pbUtils.log('I', "[pbShulker]");
    }

    public static void setMax(int arg) {
        iShulksNeeded = arg;
        Main.pbConfigFile.set("need", iShulksNeeded, "pbShulker", "shulkers");
        pbUtils.log("[pbShulker]", "new 'Needed' value set!");
    }
}
