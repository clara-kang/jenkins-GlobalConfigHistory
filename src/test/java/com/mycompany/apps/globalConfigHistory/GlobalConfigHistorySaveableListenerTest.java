package com.mycompany.apps.globalConfigHistory;

import hudson.XmlFile;
import hudson.model.Saveable;
import org.junit.Test;

import java.io.File;

import static org.mockito.Mockito.*;


/**
 * Created by clara-kang on 19/01/16.
 */
public class GlobalConfigHistorySaveableListenerTest {

    private final FileHistoryDao mockedDao = mock(FileHistoryDao.class);
    private final GlobalConfigHistory mockedPlugin = mock(GlobalConfigHistory.class);

    @Test
    public void testOnChange(){
        GlobalConfigHistorySaveableListener sut = new GlobalConfigHistorySaveableListenerImpl();
        sut.onChange(null,null);
        verify(mockedDao).saveItem(any(XmlFile.class));
    }

    private class GlobalConfigHistorySaveableListenerImpl extends GlobalConfigHistorySaveableListener {

        @Override
        GlobalConfigHistory getPlugin(){
            return mockedPlugin;
        }

        @Override
        FileHistoryDao getHistoryDao(GlobalConfigHistory plugin) {
            return mockedDao;
        }
    }
}
