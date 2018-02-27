package com.sayurbox.inventory.app;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * @author mrhezqhez@gmail.com
 */
public class TestRunner {

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(InventoryMainTest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println("Result : "+result.wasSuccessful());
    }

}
