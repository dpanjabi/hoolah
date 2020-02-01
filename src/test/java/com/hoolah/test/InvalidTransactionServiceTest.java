package com.hoolah.test;

import com.hoolah.data.DataLayer;
import com.hoolah.util.DataLoadingException;
import org.junit.Test;

public class InvalidTransactionServiceTest {
    @Test(expected = DataLoadingException.class)
    public void testUnorderedTransactionLoading() {
        DataLayer.init("target/classes/invalid-transactions1.csv");
    }

    @Test(expected = DataLoadingException.class)
    public void testInvalidTransactionType() {
        DataLayer.init("target/classes/invalid-transactions2.csv");
    }

    @Test(expected = DataLoadingException.class)
    public void testInvalidDateFormatDateTransactionType() {
        DataLayer.init("target/classes/invalid-transactions3.csv");
    }
}
