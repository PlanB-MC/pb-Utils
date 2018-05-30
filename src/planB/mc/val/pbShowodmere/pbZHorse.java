package planB.mc.val.pbShowodmere;

import planB.mc.val.Main;
import planB.mc.val.pbUtils.pbUtils;

public class pbZHorse {
    //
    private static onZHorse onZHorse;

    public pbZHorse(Main plugin) {
        Main.pbConfigFile.addDefault("enablePlugin",true,"pbZHorse");
        if (!Main.pbConfigFile.getBoolean("enablePlugin","pbZHorse")){
            pbUtils.log('C',"[pbZHorse]");
            return;
        }
        onZHorse = new onZHorse(plugin);
        Main.pbListenersEars.addListener(onZHorse);
        pbUtils.log('I',"[pbZHorse]");
    }
}
