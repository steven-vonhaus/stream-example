package com.henallux.stream.example.streamexample.exceedingamount;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
public class ExceedingAmountResponseConverter {

    private static final int EUR_SIZE = 3;

    public BigDecimal exceedingAmount(ExceedingAmountResponse exceedingAmountResponse) {

        BigDecimal totalExceedingAmount = BigDecimal.ZERO;
        BigDecimal totalRefundAmount = BigDecimal.ZERO;

        if (exceedingAmountResponse != null) {
            ExceedingAmountHistResponse exceedingAmountHistResponse = exceedingAmountResponse.getExceedingAmountHistResponse();
            if (exceedingAmountHistResponse != null) {
                List<Transaction> transactions = exceedingAmountHistResponse.getTransactionList();
                if (transactions != null) {
                    for (Transaction transaction : transactions) {
                        if (transaction.getTransactionStatus() == TransactionStatus.SUCC) {
                            if (StringUtils.isEmpty(transaction.getPreviousUTI()) || transaction.getTimeOfCapture() != null) {
                                Amount amountType = transaction.getAmount();
                                BigDecimal amount = BigDecimal.ZERO;
                                if (amountType != null) {
                                    String currencyAmount = amountType.getCurrencyAmount();
                                    if (currencyAmount != null) {
                                        String amountText = currencyAmount.substring(0, currencyAmount.length() - EUR_SIZE);
                                        amount = new BigDecimal(amountText);
                                        amount = amount.movePointLeft(amountType.getMinorUnitDecimals());
                                    }
                                }
                                BigDecimal exceedingAmount = amount;
                                totalExceedingAmount = totalExceedingAmount.add(exceedingAmount);
                            } else {
                                Amount amountType = transaction.getAmount();
                                BigDecimal amount = BigDecimal.ZERO;
                                if (amountType != null) {
                                    String currencyAmount = amountType.getCurrencyAmount();
                                    if (currencyAmount != null) {
                                        String amountText = currencyAmount.substring(0, currencyAmount.length() - EUR_SIZE);
                                        amount = new BigDecimal(amountText);
                                        amount = amount.movePointLeft(amountType.getMinorUnitDecimals());
                                    }
                                }
                                BigDecimal refundAmount = amount;
                                totalRefundAmount = totalRefundAmount.add(refundAmount);
                            }
                        }
                    }
                }
            }
        }

        totalExceedingAmount = totalExceedingAmount.subtract(totalRefundAmount);
        return totalExceedingAmount.setScale(2, RoundingMode.HALF_UP);
    }
}