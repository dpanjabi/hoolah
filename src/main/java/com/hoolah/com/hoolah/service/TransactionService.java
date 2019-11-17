package com.hoolah.com.hoolah.service;

import com.hoolah.data.DataLayer;
import com.hoolah.model.Transaction;
import com.hoolah.model.TransactionAnalyzerReport;
import com.hoolah.util.Utility;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.NavigableMap;
import java.util.concurrent.atomic.AtomicInteger;

public class TransactionService {
    public static TransactionAnalyzerReport getTransactionSum(String startTime, String endTime, String merchantName) {
        long startEpoch = LocalDateTime.parse(startTime, Utility.DATE_TIME_FORMATTER)
                .toEpochSecond(ZoneOffset.UTC);
        long endEpoch = LocalDateTime.parse(endTime, Utility.DATE_TIME_FORMATTER)
                .toEpochSecond(ZoneOffset.UTC);

        NavigableMap<Long, List<Transaction>> selectedTransactions = DataLayer.getTimeLinedTransactions()
                .subMap(startEpoch, true, endEpoch, true);

        AtomicInteger transactionCounts = new AtomicInteger();

        double trnsactionSum = selectedTransactions.values()
                .stream()
                .flatMap(transactions -> transactions.stream())
                .filter(transaction -> transaction.getMerchant().equalsIgnoreCase(merchantName))
                .filter(transaction -> !transaction.isReversed())
                .mapToDouble(transaction ->  {
                    transactionCounts.incrementAndGet();
                    return  transaction.getAmount();
                })
                .sum();

        int transactionCount = transactionCounts.intValue();
        transactionCount = transactionCount == 0? 1: transactionCount;
        double transactionAverage = trnsactionSum /  transactionCount;

        return new TransactionAnalyzerReport(transactionAverage, transactionCounts.intValue());
    }
}
