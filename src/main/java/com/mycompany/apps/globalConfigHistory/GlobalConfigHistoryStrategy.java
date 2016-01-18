package com.mycompany.apps.globalConfigHistory;

import hudson.ExtensionPoint;
import hudson.model.Describable;
import hudson.model.Descriptor;
import jenkins.model.Jenkins;
import org.kohsuke.stapler.DataBoundConstructor;

import java.io.File;

/**
 * Created by ehenkan on 1/18/16.
 */
public abstract class GlobalConfigHistoryStrategy  implements ExtensionPoint, Describable<FileHistoryDao> {

    public GlobalConfigHistoryStrategy(){

    }

    @Override
    public Descriptor<FileHistoryDao> getDescriptor() {
        return Jenkins.getInstance().getDescriptorOrDie(getClass());
    }
}
