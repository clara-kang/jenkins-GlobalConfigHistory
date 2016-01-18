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

/**
 * methods taken from jobConfigHistory plugin.
 */
public class FileHistoryDao extends GlobalConfigHistoryStrategy{

    private final File historyRootDir;
    private final File jenkinsHome;

    public FileHistoryDao(){
        this(null,null);
    }

    FileHistoryDao(final File historyRootDir, final File jenkinsHome){
        this.historyRootDir = historyRootDir;
        this.jenkinsHome = jenkinsHome;
    }

  static File createNewHistoryDir(final File itemHistoryDir, final AtomicReference<Calendar> timestampHolder) {
        Calendar timestamp;
        File f;
        timestamp = new GregorianCalendar();
        f = new File(itemHistoryDir, new SimpleDateFormat("yyyy-MM-DD_HH-mm-ss").format(timestamp.getTime()));
        timestampHolder.set(timestamp);

        if (!(f.mkdirs() || f.exists())) {
            throw new RuntimeException("Could not create rootDir " + f);
        }
        return f;
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

    File getRootDir(final XmlFile xmlFile, final AtomicReference<Calendar> timestampHolder){
        final File configFile = xmlFile.getFile();
        final File itemHistoryDir = getHistoryDir(configFile);
        return createNewHistoryDir(itemHistoryDir, timestampHolder);
    }

    static void copyConfigFile(final File currentConfig, final File timestampedDir) throws FileNotFoundException,
            IOException {
        final BufferedOutputStream configCopy = new BufferedOutputStream(
                new FileOutputStream(new File(timestampedDir, currentConfig.getName())));
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

    public void saveItem(final XmlFile file){
        try{
            final AtomicReference<Calendar> timestampHolder = new AtomicReference<Calendar>();
            final File timestampedDir = getRootDir(file, timestampHolder);
            copyConfigFile(file.getFile(), timestampedDir);
        }catch (IOException e){
            throw new RuntimeException("Unable to copy" + file, e);
        }


    }



}
