package planB.mc.val.lightSense.HeadsLights;

import planB.mc.val.Main;
import planB.mc.val.pbUtils.pbUtils;

public class HeadLight {

    public HeadLight(Main plugin) {
        Main.pbConfigFile.addDefault("enablePlugin", true, "pbHeadLights");
        if (!Main.pbConfigFile.getBoolean("enablePlugin", "pbHeadLights")) {
            pbUtils.log('C', "[pbHeadLights]");
            return;
        }
       Main.pbListenersEars.addListener(new onHeadLights());
    }

}