package com.mycompany.apps.globalConfigHistory;

import hudson.XmlFile;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.Matchers.*;
/**
 * Created by clara-kang on 18/01/16.
 */
public class FileHistoryDaoTest {

    private final String sep = File.pathSeparator;
    private final String FILE_PATH = "/com.mycompany.apps.globalConfigHistory/mockDir";
    private File jenkinsHome;
    private File historyRoot;
    private File testDirectory;
    private XmlFile testConfig;
    private File testHistory;
    private File dupDirectory;
    private File emptyDirectory;
    private FileHistoryDao dao;


    @Before
    public void setFields(){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        testDirectory = new File(getClass().getResource(FILE_PATH).getFile());
        jenkinsHome = testDirectory;
        historyRoot = new File(testDirectory, "my-config-history");
        testConfig = new XmlFile(new File(testDirectory, "first.middle.last.xml"));
        testHistory = new File(historyRoot,"first.middle.last");
        dupDirectory = new File(testDirectory, "contain-dup");
        emptyDirectory = new File(testDirectory, "empty");
        dao = new FileHistoryDao(historyRoot, jenkinsHome);
    }

    @Test
    public void testGetHistoryDir(){
        final File configFile = testConfig.getFile();
        File result = dao.getHistoryDir(configFile);
        assertEquals(testHistory,result);
        assertTrue(result.exists());
    }

    @Test
    public void testGetRootDir(){
        File result = dao.getRootDir(testConfig);
        assertEquals(testHistory,result);
        assertTrue(result.exists());
        assertThat(result.getPath(), containsString("my-config-history" + File.separator + "first.middle.last"));
    }

    @Test
    public void testCopyConfigFile() throws Exception{
        File currentConfig = testConfig.getFile();
        File tempDir = new File(jenkinsHome,"temp");
        tempDir.mkdir();
        FileHistoryDao.copyConfigFile(currentConfig, tempDir);
        final File copy = new File(tempDir, currentConfig.getName());
        assertTrue(copy.exists());
    }

    @Test
    public void testCheckForDuplicates(){
        XmlFile xmlFile = new XmlFile(new File(jenkinsHome,"first.middle.last.xml"));
        boolean result1 = dao.checkForDuplicate(xmlFile, dupDirectory);
        boolean result2 = dao.checkForDuplicate(xmlFile, emptyDirectory);
        assertEquals(false,result1);
        assertEquals(true,result2);
    }

    @Test
    public void testSaveItem(){
        dao.saveItem(testConfig);
        final File result = new File(testHistory, testConfig.getFile().getName());
        assertTrue(result.exists());
    }

}
