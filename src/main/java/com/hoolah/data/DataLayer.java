package com.hoolah.data;

import com.hoolah.model.Transaction;
import com.hoolah.model.TransactionType;
import com.hoolah.util.DataLoadingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class DataLayer {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataLayer.class);

    private static NavigableMap<Long, List<Transaction>> timeLinedTransactions;
    private static boolean dataLoaded;

    public static void init(String csvFilePath) {
        if (dataLoaded) {
            LOGGER.warn("Data already loaded");
        }

        timeLinedTransactions = new TreeMap<>();

        Map<String, Transaction> idMap = new HashMap<>();

        try {
            Path transactionsFilePath = Paths.get(csvFilePath);

            Files.lines(transactionsFilePath)
                    .skip(1)
                    .map(line -> line.split(","))
                    .forEach(data ->
                            buildAndManageTransaction(idMap, data)
                    );
            dataLoaded = true;
        } catch (Exception e) {
            throw new DataLoadingException("Data loading failed!!", e);
        }
    }

    private static void buildAndManageTransaction(Map<String, Transaction> idMap, String[] data) {
        Transaction transaction = new Transaction.Builder()
                .id(data[0].trim())
                .transactionDate(data[1].trim())
                .amount(data[2].trim())
                .merchant(data[3].trim())
                .transactionType(data[4].trim())
                .relatedTransactionId(data.length > 5? data[5].trim(): null)
                .build();

        idMap.put(transaction.getId(), transaction);

        if (transaction.getTransactionType() == TransactionType.REVERSAL) {
            Transaction originalTransaction = idMap.get(transaction.getRelatedTransactionId());
            originalTransaction.setReversed(true);
        }

        List<Transaction> transactions = timeLinedTransactions.get(transaction.getTransactionDateEpoch());
        if (Objects.isNull(transactions)) {
            transactions = new ArrayList<>();
            timeLinedTransactions.put(transaction.getTransactionDateEpoch(), transactions);
        }
        transactions.add(transaction);
    }

    public static NavigableMap<Long, List<Transaction>> getTimeLinedTransactions() {
        if (!dataLoaded) {
            throw new DataLoadingException("Data is not loaded yet. Call the init method to load data!!!");
        }
        return timeLinedTransactions;
    }
}
