package com.hoolah.test;

import com.hoolah.com.hoolah.service.TransactionService;
import com.hoolah.data.DataLayer;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionServiceTest {
    @BeforeClass
    public static void setUp() {
        DataLayer.init("target/classes/transactions.csv");
    }

    @Test
    public void testWithValidUserAndReversedTransaction() {
        Assert.assertEquals(59.99, TransactionService.getTransactionSum("20/08/2018 12:00:00", "20/08/2018 13:00:00", "Kwik-E-Mart"), 0);
    }

    @Test
    public void testWithValidUserNotAvailableInTransaction() {
        Assert.assertEquals(0, TransactionService.getTransactionSum("20/08/2018 12:00:00", "20/08/2018 13:00:00", "Kwik-E-Mart2"), 0);
    }

    @Test
    public void testWithValidUserOutOfRangeTransaction() {
        Assert.assertEquals(0, TransactionService.getTransactionSum("20/08/2019 12:00:00", "20/08/2019 13:00:00", "Kwik-E-Mart2"), 0);
    }
}
