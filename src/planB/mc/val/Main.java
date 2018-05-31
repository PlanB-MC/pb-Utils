package planB.mc.val;

import org.bukkit.plugin.java.JavaPlugin;
import planB.mc.val.barrier.pbBarrier;
import planB.mc.val.lightSense.pbLighter;
import planB.mc.val.pbAdvancements.Block;
import planB.mc.val.pbShowodmere.pbZHorse;
import planB.mc.val.pbUtils.*;
import planB.mc.val.shulker.pbShulker;

import java.security.SecureRandom;
import java.util.Random;

//version: 1.2.1.0
// Release.pluginCount.pluginsDone.permsDone
public class Main extends JavaPlugin {
    public static pbConfig pbConfigFile;
    public static pbListeners pbListenersEars;
    public static Random random;
    private pbUtils pbUtils;
    private planB.mc.val.pbUtils.pbCrafting pbCrafting;
    private pbShulker pbShulker;
    private pbBarrier pbBarier;
    private pbLighter pbLighter;
    private Block pbAdvancements;
    private pbZHorse pbZHorse;

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        //Register Commands
        this.getCommand("planb").setExecutor(new CommandList());
        this.getCommand("blame").setExecutor(new CommandList());

        //Setting up Internal Structure:
        pbUtils = new pbUtils(this);
        pbConfigFile = new pbConfig(this, "pbConfig.yml");
        pbConfigFile.addDefault("enablePlugin", true, "planB");
        if (!pbConfigFile.getBoolean("enablePlugin", "planB")) {
            pbUtils.log('C', "[PlanB-Utils]");
            return;
        }
        pbCrafting = new pbCrafting(this);
        pbListenersEars = new pbListeners(this);
        random = new SecureRandom();

        //Setup plugins
        pbShulker = new pbShulker();
        pbBarier = new pbBarrier(this);
        pbLighter = new pbLighter(this);
        pbAdvancements = new Block(this);
        pbZHorse = new pbZHorse(this);
    }
}
