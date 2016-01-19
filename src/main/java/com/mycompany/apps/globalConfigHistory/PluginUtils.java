package com.mycompany.apps.globalConfigHistory;

import jenkins.model.Jenkins;

import java.io.File;

/**
 * Created by ehenkan on 1/19/16.
 */
final class PluginUtils {

    public static GlobalConfigHistory getPlugin(){
        return Jenkins.getInstance().getPlugin(GlobalConfigHistory.class);
    }

    public static FileHistoryDao getHistoryDao(GlobalConfigHistory plugin){
        return new FileHistoryDao(plugin.getRootDir(), new File(Jenkins.getInstance().root.getPath()));
    }
}
