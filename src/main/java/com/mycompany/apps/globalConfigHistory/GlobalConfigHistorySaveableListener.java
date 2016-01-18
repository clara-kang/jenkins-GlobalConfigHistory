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

    final GlobalConfigHistory plugin = Jenkins.getInstance().getPlugin(GlobalConfigHistory.class);
    FileHistoryDao historyDao = new FileHistoryDao(plugin.getRootDir(), new File(Jenkins.getInstance().root.getPath()));
    private final File jenkinsHome = new File(Jenkins.getInstance().root.getPath());

    private static final Logger LOG = Logger.getLogger(GlobalConfigHistory.class.getName());

    @Override
    public void onChange(final Saveable o, final XmlFile file){
        historyDao.saveItem(file);
        /*final File configFile = file.getFile();
        final String configRootDir = configFile.getParent();
        final String hudsonRootDir = jenkinsHome.getPath();

        if(configRootDir.equals(hudsonRootDir)){
            final String fileName = configFile.getName();
            LOG.log(Level.FINEST, fileName + " has been changed");
        }*/

    }

}
