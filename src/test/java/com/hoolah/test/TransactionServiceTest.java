package com.hoolah.test;

import com.hoolah.com.hoolah.service.TransactionService;
import com.hoolah.data.DataLayer;
import com.hoolah.model.TransactionAnalyzerReport;
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
        TransactionAnalyzerReport report = TransactionService.getTransactionSum("20/08/2018 12:00:00", "20/08/2018 13:00:00", "Kwik-E-Mart");
        Assert.assertEquals(59.99, report.getAverageTransactionValue(), 0);
        Assert.assertEquals(1, report.getTotalNumberOfTransactions());
    }

    @Test
    public void testWithValidUserNotAvailableInTransaction() {
        TransactionAnalyzerReport report = TransactionService.getTransactionSum("20/08/2018 12:00:00", "20/08/2018 13:00:00", "Kwik-E-Mart2");
        Assert.assertEquals(0d, report.getAverageTransactionValue(), 0);
        Assert.assertEquals(0, report.getTotalNumberOfTransactions());
    }

    @Test
    public void testWithValidUserOutOfRangeTransaction() {
        TransactionAnalyzerReport report = TransactionService.getTransactionSum("20/08/2019 12:00:00", "20/08/2019 13:00:00", "Kwik-E-Mart");
        Assert.assertEquals(0d, report.getAverageTransactionValue(), 0);
        Assert.assertEquals(0, report.getTotalNumberOfTransactions());
    }
}
