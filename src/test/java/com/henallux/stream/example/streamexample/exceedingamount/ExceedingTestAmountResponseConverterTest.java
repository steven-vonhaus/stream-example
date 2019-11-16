package com.henallux.stream.example.streamexample.exceedingamount;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class ExceedingTestAmountResponseConverterTest {

    private ExceedingAmountResponseConverter converter;

    @BeforeEach
    public void setUp() {
        converter = new ExceedingAmountResponseConverter();
    }

    @Test
    public void exceedingAmount() {
        ExceedingAmountResponse response = buildViewtransHistResponse(
                new TestAmount("5EUR", 0),
                new TestAmount("12EUR", 1),
                new TestAmount("123EUR", 2));

        BigDecimal result = converter.exceedingAmount(response);

        assertThat(result).isEqualTo(new BigDecimal("7.43"));
    }

    @Test
    public void exceedingAmount_null() {
        ExceedingAmountResponse response = buildViewtransHistResponse();

        BigDecimal result = converter.exceedingAmount(response);

        assertThat(result).isEqualTo(new BigDecimal("0.00"));
    }

    @Test
    public void exceedingAmount_withRefund() {
        ExceedingAmountResponse response = buildViewtransHistResponse(
                new TestAmount("5EUR", 0),
                new TestAmount("12EUR", 1),
                new TestAmount("123EUR", 2));

        final TestAmount amount = new TestAmount("5EUR", 0);
        Transaction refundTransaction = buildTransaction(amount.getCurrencyAmount(), amount.getUnitDecimals());
        refundTransaction.setPreviousUTI("mpay01");
        response.getExceedingAmountHistResponse().getTransactionList().add(refundTransaction);

        BigDecimal result = converter.exceedingAmount(response);

        assertThat(result).isEqualTo(new BigDecimal("2.43"));
    }

    private ExceedingAmountResponse buildViewtransHistResponse(TestAmount... amounts) {
        ExceedingAmountResponse wrappedResponse = new ExceedingAmountResponse();
        ExceedingAmountHistResponse response = new ExceedingAmountHistResponse();

        List<Transaction> transactionList = Stream.of(amounts)
                .map(x -> buildTransaction(x.getCurrencyAmount(), x.getUnitDecimals()))
                .collect(Collectors.toList());

        Stream.of(amounts).forEach(x -> response.setTransactionList(transactionList));
        wrappedResponse.setExceedingAmountHistResponse(response);
        return wrappedResponse;
    }

    private Transaction buildTransaction(String currencyAmount, int unitDecimals) {
        Transaction transaction = new Transaction();
        com.henallux.stream.example.streamexample.exceedingamount.Amount amount = new com.henallux.stream.example.streamexample.exceedingamount.Amount();
        amount.setCurrencyAmount(currencyAmount);
        amount.setMinorUnitDecimals(unitDecimals);
        transaction.setAmount(amount);
        transaction.setTransactionStatus(TransactionStatus.SUCC);
        return transaction;
    }

    private static class TestAmount {
        private String currencyAmount;
        private int unitDecimals;

        private TestAmount(String currencyAmount, int unitDecimals) {
            this.currencyAmount = currencyAmount;
            this.unitDecimals = unitDecimals;
        }

        String getCurrencyAmount() {
            return currencyAmount;
        }

        int getUnitDecimals() {
            return unitDecimals;
        }
    }
}