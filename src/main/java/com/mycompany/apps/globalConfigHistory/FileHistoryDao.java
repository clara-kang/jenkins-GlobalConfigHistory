package com.mycompany.apps.globalConfigHistory;

import hudson.ExtensionPoint;
import hudson.Util;
import hudson.XmlFile;
import hudson.model.Describable;
import hudson.model.Descriptor;
import jenkins.model.Jenkins;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * methods taken from jobConfigHistory plugin.
 */
public class FileHistoryDao extends GlobalConfigHistoryStrategy{

    private static final Logger LOG = Logger.getLogger(FileHistoryDao.class.getName());
    private final File historyRootDir;
    private final File jenkinsHome;

    public FileHistoryDao(){
        this(null,null);
    }

    FileHistoryDao(final File historyRootDir, final File jenkinsHome){
        this.historyRootDir = historyRootDir;
        this.jenkinsHome = jenkinsHome;
    }

    File getHistoryDir(final File configFile){
        final String configRootDir = configFile.getParent();
        final String hudsonRootDir = jenkinsHome.getPath();
        if(!configRootDir.equals(hudsonRootDir)){
            throw new IllegalArgumentException("No need to save this config file "+configFile);
        }
        final String fileName = configFile.getName();
        String underRootDir = configFile.getName().substring(0,fileName.lastIndexOf('.'));
        final File historyDir = new File(historyRootDir,underRootDir);
        return historyDir;
    }

    File getRootDir(final XmlFile xmlFile){
        final File configFile = xmlFile.getFile();
        final File itemHistoryDir = getHistoryDir(configFile);

        if (!(itemHistoryDir.mkdirs() || itemHistoryDir.exists())) {
            throw new RuntimeException("Could not create rootDir ");
        }

        return itemHistoryDir;
    }

    static void copyConfigFile(final File currentConfig, final File directory) throws FileNotFoundException,
            IOException {
        final BufferedOutputStream configCopy = new BufferedOutputStream(
                new FileOutputStream(new File(directory, currentConfig.getName())));
        try {
            final FileInputStream configOriginal = new FileInputStream(currentConfig);
            try {
                Util.copyStream(configOriginal, configCopy);
            } finally {
                configOriginal.close();
            }
        } finally {
            configCopy.close();
        }
    }

    boolean checkForDuplicate(final XmlFile currentConfig, final File directory){

        if(directory.listFiles() == null){
            return true;
        }
        else if(directory.listFiles().length == 1){
            final File old = directory.listFiles()[0];
            final XmlFile oldXml = new XmlFile(old);
            try{
                return !oldXml.asString().equals(currentConfig.asString());
            }catch (IOException e){
                LOG.log(Level.WARNING, "unable to check for duplicate");
            }
        }
        else{
            LOG.log(Level.WARNING, "only 1 file may exist in a directory");
        }
        return false;
    }

    public boolean saveItem(final XmlFile file){
        try{
            final File dir = getRootDir(file);
            if(checkForDuplicate(file,dir)){
                copyConfigFile(file.getFile(), dir);
                return true;
            }
            else{
                return false;
            }
        }catch (IOException e){
            throw new RuntimeException("Unable to copy" + file, e);
        }
    }

}
