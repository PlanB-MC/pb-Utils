package planB.mc.val;

import org.bukkit.plugin.java.JavaPlugin;
import planB.mc.val.barrier.pbBarrier;
import planB.mc.val.pbAdvancements.Block;
import planB.mc.val.pbLights.lightSense.pbLighter;
import planB.mc.val.pbLights.pbHeadLights.HeadLight;
import planB.mc.val.pbShowodmere.pbZHorse;
import planB.mc.val.pbUtils.*;
import planB.mc.val.shulker.pbShulker;

import java.security.SecureRandom;
import java.util.Random;

//version: 1.13.1.0
public class Main extends JavaPlugin {
    public static pbConfig pbConfigFile;
    public static pbListeners pbListenersEars;
    public static Random random;
    private pbUtils pbUtils;
    private planB.mc.val.pbUtils.pbCrafting pbCrafting;

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

        pbShulker pbShulker = new pbShulker();
        pbBarrier pbBarier = new pbBarrier(this);
        pbLighter pbLighter = new pbLighter(this);
        Block pbAdvancements = new Block(this);
        pbZHorse pbZHorse = new pbZHorse(this);
        HeadLight headLight = new HeadLight(this);
    }
}
