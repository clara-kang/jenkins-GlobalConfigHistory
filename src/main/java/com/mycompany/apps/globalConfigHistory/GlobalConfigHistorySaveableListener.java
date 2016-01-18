package com.mycompany.apps.globalConfigHistory;

import hudson.Extension;
import hudson.XmlFile;
import hudson.model.Saveable;
import hudson.model.listeners.SaveableListener;
import jenkins.model.Jenkins;

import java.io.File;

/**
 * Created by ehenkan on 1/18/16.
 */
@Extension
public class GlobalConfigHistorySaveableListener extends SaveableListener{

    final GlobalConfigHistory plugin = Jenkins.getInstance().getPlugin(GlobalConfigHistory.class);

    @Override
    public void onChange(final Saveable o, final XmlFile file){
        if(plugin.isSaveable(o)){
            final FileHistoryDao configHistoryListenerHelper = getHistoryDao();
            configHistoryListenerHelper.saveItem(file);
        }
    }

    FileHistoryDao getHistoryDao(){
        return new FileHistoryDao(plugin.getRootDir(), new File(Jenkins.getInstance().root.getPath()));
    }
}
