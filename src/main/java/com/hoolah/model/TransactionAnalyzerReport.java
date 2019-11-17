package com.hoolah.model;

public class TransactionAnalyzerReport {
    private final double averageTransactionValue;
    private final int totalNumberOfTransactions;

    public TransactionAnalyzerReport(double averageTransactionValue, int totalNumberOfTransactions) {
        this.averageTransactionValue = averageTransactionValue;
        this.totalNumberOfTransactions = totalNumberOfTransactions;
    }

    public double getAverageTransactionValue() {
        return averageTransactionValue;
    }

    public int getTotalNumberOfTransactions() {
        return totalNumberOfTransactions;
    }
}
