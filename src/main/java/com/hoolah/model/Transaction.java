package com.hoolah.model;

import com.hoolah.util.DataLoadingException;
import com.hoolah.util.Utility;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private final String id;
    private final String merchant;
    private final LocalDateTime transactionDate;
    private boolean reversed;
    private final TransactionType transactionType;
    private final String relatedTransactionId;
    private final double amount;
    private final long transactionDateEpoch;

    private Transaction(String id, String merchant, LocalDateTime transactionDate, TransactionType transactionType, String relatedTransactionId, double amount, long transactionDateEpoch) {
        this.id = id;
        this.merchant = merchant;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.relatedTransactionId = relatedTransactionId;
        this.amount = amount;
        this.transactionDateEpoch = transactionDateEpoch;
    }

    public String getId() {
        return id;
    }

    public String getMerchant() {
        return merchant;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public boolean isReversed() {
        return reversed;
    }

    public void setReversed(boolean reversed) {
        this.reversed = reversed;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public String getRelatedTransactionId() {
        return relatedTransactionId;
    }

    public double getAmount() {
        return amount;
    }

    public long getTransactionDateEpoch() { return transactionDateEpoch; }

    public static class Builder {
        private String id;
        private String merchant;
        private String transactionDate;
        private String transactionType;
        private String relatedTransactionId;
        private String amount;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder merchant(String merchant) {
            this.merchant = merchant;
            return this;
        }

        public Builder transactionDate(String transactionDate) {
            this.transactionDate = transactionDate;
            return this;
        }

        public Builder transactionType(String transactionType) {
            this.transactionType = transactionType;
            return this;
        }

        public Builder relatedTransactionId(String relatedTransactionId) {
            this.relatedTransactionId = relatedTransactionId;
            return this;
        }

        public Builder amount(String amount) {
            this.amount = amount;
            return this;
        }

        public Transaction build() throws DataLoadingException {
            try {
                double trxAmt = Double.parseDouble(amount);
                TransactionType trxType = TransactionType.valueOf(transactionType);
                LocalDateTime trxDate = LocalDateTime.parse(transactionDate, Utility.DATE_TIME_FORMATTER);
                long trxEpoch = trxDate.toEpochSecond(ZoneOffset.UTC);
                return new Transaction(id, merchant, trxDate, trxType, relatedTransactionId, trxAmt, trxEpoch);
            } catch(Exception e) {
                throw new DataLoadingException("Encountered issue while loading data", e);
            }
        }
    }
}
