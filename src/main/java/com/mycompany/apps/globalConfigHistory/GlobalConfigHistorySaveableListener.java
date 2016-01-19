package com.mycompany.apps.globalConfigHistory;

import hudson.Extension;
import hudson.XmlFile;
import hudson.model.Saveable;
import hudson.model.listeners.SaveableListener;
import jenkins.model.Jenkins;
import sun.rmi.runtime.Log;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ehenkan on 1/18/16.
 */
@Extension
public class GlobalConfigHistorySaveableListener extends SaveableListener{



    final GlobalConfigHistory plugin = getPlugin();
    final FileHistoryDao historyDao = getHistoryDao(plugin);

    private static final Logger LOG = Logger.getLogger(GlobalConfigHistorySaveableListener.class.getName());

    @Override
    public void onChange(final Saveable o, final XmlFile file){
        if(historyDao.saveItem(file)){
            final String fileName = file.getFile().getName();
            LOG.log(Level.FINEST, fileName + " has been changed");
        }
    }

    /**
     * for tests only
     *
     * @return plugin
     */
    GlobalConfigHistory getPlugin() {
        return PluginUtils.getPlugin();
    }

    /**
     * for tests only
     *
     * @return historyDao
     */
    FileHistoryDao getHistoryDao(GlobalConfigHistory plugin) {
        return PluginUtils.getHistoryDao(plugin);
    }
}