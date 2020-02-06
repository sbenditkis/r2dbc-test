package com.sapiens.tests.r2dbc;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class MemoryTest {
    Logger log = LoggerFactory.getLogger(MemoryTest.class);
    @Test
    public void testBigStrings() {
        Random rand = new Random();
        int size=4000000;
        byte []arr = new byte[size];
        for(int i=0; i<size; i++) {
            arr[i] = (byte)rand.nextInt(256);
        }

        for(int count=0;;count++) {
            byte [] arr2 = new byte[size];
            System.arraycopy(arr, 0, arr2, 0, size);
            if(count%10000 == 0) {
                log.debug(String.valueOf(count));
            }
        }
    }
}
