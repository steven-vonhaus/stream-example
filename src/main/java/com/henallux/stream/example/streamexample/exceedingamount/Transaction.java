package com.henallux.stream.example.streamexample.exceedingamount;

import java.time.LocalDateTime;

public class Transaction {
    private TransactionStatus transactionStatus;
    private String previousUTI;
    private LocalDateTime timeOfCapture;
    private Amount amount;

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getPreviousUTI() {
        return previousUTI;
    }

    public void setPreviousUTI(String previousUTI) {
        this.previousUTI = previousUTI;
    }

    public LocalDateTime getTimeOfCapture() {
        return timeOfCapture;
    }

    public void setTimeOfCapture(LocalDateTime timeOfCapture) {
        this.timeOfCapture = timeOfCapture;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }
}
