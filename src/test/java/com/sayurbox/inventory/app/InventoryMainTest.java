package com.sayurbox.inventory.app;

import org.apache.log4j.Logger;
import org.junit.*;

import java.io.File;

/**
 * @author mrhezqhez@gmail.com
 *
 *  This is unit test to cover 2 scenario :
 *  selecting and ordering
 */
public class InventoryMainTest {

    final static org.apache.log4j.Logger log = Logger.getLogger(InventoryMainTest.class);

    public InventoryMainTest() {}

    @BeforeClass
    public static void setUpClass() {}

    @AfterClass
    public static void tearDownClass() {}

    @Before
    public void setUp() {
        log.debug("populate sample data");
    }

    @After
    public void tearDown() {}

    @Test
    public void selectingScenario() throws Exception {
        try {
            log.debug("selecting scenario");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void orderingScenario() throws Exception {
        try {
            log.debug("ordering scenario");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private File getCwd() {
        return new File("").getAbsoluteFile();
    }
}
