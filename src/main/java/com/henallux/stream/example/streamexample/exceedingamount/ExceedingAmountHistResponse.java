package com.henallux.stream.example.streamexample.exceedingamount;

import java.util.ArrayList;
import java.util.List;

public class ExceedingAmountHistResponse {
    private List<Transaction> transactionList = new ArrayList<>();

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }
}
