package planB.mc.val.pbLights.pbHeadLights;

import org.bukkit.plugin.java.JavaPlugin;
import planB.mc.val.Main;
import planB.mc.val.pbUtils.pbListeners;

import java.util.HashSet;

public class HeadLight {
    private static HashSet<String> candles;

    public HeadLight(Main plugin) {
        /*Main.pbConfigFile.addDefault("enablePlugin", true, "pbHeadLights");
        if (!Main.pbConfigFile.getBoolean("enablePlugin", "pbHeadLights")) {
            pbUtils.log('C', "[pbHeadLights]");
            return;
        }*/
        candles = new HashSet<>();
        LIT();
        Main.pbListenersEars.addListener(new onHeadLights());
    }

    public static Boolean contains(String id){
        return candles.contains(id);
    }

    private void LIT() {
        candles.add("2609");
        candles.add("2616");
        candles.add("2604");
        candles.add("2608");
        candles.add("2621");
        candles.add("2622");
        candles.add("2614");
        candles.add("2623");
        candles.add("2624");
        candles.add("2610");
        candles.add("2628");
        candles.add("2627");
        candles.add("2406");
        candles.add("2619");
        candles.add("1776");
        candles.add("2407");
        candles.add("2626");
        candles.add("1778");
        candles.add("1777");
        candles.add("2612");
        candles.add("2603");
        candles.add("2615");
        candles.add("2602");
        candles.add("1779");
        candles.add("2620");
        candles.add("2618");
        candles.add("2611");
        candles.add("2629");
        candles.add("2617");
        candles.add("239");
        candles.add("268");
        candles.add("2606");
        candles.add("238");
        candles.add("4324");
        candles.add("2613");
        candles.add("2607");
        candles.add("4582");
        candles.add("250");
        candles.add("2625");
        candles.add("2601");
        candles.add("2605");
    }


}
