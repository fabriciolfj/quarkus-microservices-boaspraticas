package com.github.fabriciolfj.entity;

import java.math.BigDecimal;

public class CreateExtract {

    private CreateExtract() { }

    public static Extract debit(final BigDecimal value, final Account account) {
        return Extract.execute(value, TypeOperation.DEBIT, account.findExtractFirst().getBalance(), account.getUuid());
    }
}
