package com.hoolah.com.hoolah.service;

import com.hoolah.data.DataLayer;
import com.hoolah.model.Transaction;
import com.hoolah.util.Utility;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.NavigableMap;

public class TransactionService {
    public static double getTransactionSum(String startTime, String endTime, String merchantName) {
        long startEpoch = LocalDateTime.parse(startTime, Utility.DATE_TIME_FORMATTER)
                .toEpochSecond(ZoneOffset.UTC);
        long endEpoch = LocalDateTime.parse(endTime, Utility.DATE_TIME_FORMATTER)
                .toEpochSecond(ZoneOffset.UTC);

        NavigableMap<Long, List<Transaction>> selectedTransactions = DataLayer.getTimeLinedTransactions()
                .subMap(startEpoch, true, endEpoch, true);

        return selectedTransactions.values()
                .stream()
                .flatMap(transactions -> transactions.stream())
                .filter(transaction -> transaction.getMerchant().equalsIgnoreCase(merchantName))
                .filter(transaction -> !transaction.isReversed())
                .mapToDouble(Transaction::getAmount)
                .sum();
    }
}
