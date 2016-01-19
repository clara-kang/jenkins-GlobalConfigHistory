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

    private final String FILE_PATH = "/com.mycompany.apps.globalConfigHistory/mockDir";

    private final FileHistoryDao mockedDao = mock(FileHistoryDao.class);
    private final GlobalConfigHistory mockedPlugin = mock(GlobalConfigHistory.class);

    private final Saveable obj = mock(Saveable.class);
    private final File testDirectory = new File(getClass().getResource(FILE_PATH).getFile());
    private final XmlFile file = new XmlFile(new File(testDirectory, "first.middle.last.xml"));

/*
    @Test
    public void testOnChange(){
        GlobalConfigHistorySaveableListener sut = new GlobalConfigHistorySaveableListener();
        sut.onChange(obj,file);
        verify(mockedDao).saveItem(any(XmlFile.class));
    }*/
}
