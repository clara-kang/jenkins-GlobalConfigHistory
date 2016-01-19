package com.mycompany.apps.globalConfigHistory;
import hudson.Plugin;
import hudson.model.*;
import jenkins.model.Jenkins;
import org.kohsuke.stapler.DataBoundConstructor;

import java.io.File;



public class GlobalConfigHistory extends Plugin {

    private static final String DEFAULT_HISTORY_DIR = "my-config-history";

    @DataBoundConstructor
    public GlobalConfigHistory(){

    }

    protected File getRootDir(){
        final File jenkinsHome = Jenkins.getInstance().root;
        File rootDir = new File(jenkinsHome, DEFAULT_HISTORY_DIR);
        return rootDir;
    }

}

