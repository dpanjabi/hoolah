package com.hoolah.test;

import com.hoolah.com.hoolah.service.TransactionService;
import com.hoolah.data.DataLayer;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.file.Paths;

public class TestTransactionService {
    @BeforeClass
    public static void setUp() {
        DataLayer.init("target/classes/transactions.csv");
    }

    @Test
    public void test() {
        System.out.println(TransactionService.getTransactionSum("20/08/2018 12:00:00", "20/08/2018 13:00:00", "Kwik-E-Mart"));
    }
}
