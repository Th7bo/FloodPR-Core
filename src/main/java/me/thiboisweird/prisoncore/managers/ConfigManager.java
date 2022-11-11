package me.thiboisweird.prisoncore.managers;

import me.thiboisweird.prisoncore.PrisonCore;

public class ConfigManager {

    public static Object getValue(String path){
        if (PrisonCore.getInstance().getConfig().get(path) != null){
            return PrisonCore.getInstance().getConfig().get(path);
        }
        return null;
    }


}
