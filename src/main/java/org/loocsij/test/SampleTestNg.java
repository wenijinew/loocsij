package org.loocsij.test;

import org.apache.log4j.Logger;
import org.loocsij.logger.Log;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * Created by egugwen on 2017/6/29.
 */
public class SampleTestNg {
    Logger LOGGER = Logger.getLogger(SampleTestNg.class);

    @Test
    @Parameters({"message"})
    public void showMessage(String message){
        LOGGER.info(message);
    }
}
