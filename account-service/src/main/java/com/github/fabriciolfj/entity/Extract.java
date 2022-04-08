package com.github.fabriciolfj.entity;

import com.github.fabriciolfj.exceptions.DomainException;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class Extract {

    private String code;
    private String account;
    private BigDecimal debit;
    private BigDecimal credit;
    private BigDecimal balance;
    private LocalDateTime date;

    public BigDecimal getBalancePositive() {
        if (balance.compareTo(BigDecimal.ZERO) < 0) {
            return balance.multiply(BigDecimal.valueOf(-1));
        }

        return balance;
    }

    public static Extract execute(final BigDecimal value, final TypeOperation typeOperation, final BigDecimal balance, final String account) {
        return switch (typeOperation) {
            case CREDIT -> Extract.builder()
                    .code(UUID.randomUUID().toString())
                    .debit(BigDecimal.ZERO)
                    .credit(value)
                    .account(account)
                    .balance(balance.add(value))
                    .date(LocalDateTime.now())
                    .build();
            case DEBIT -> Extract.builder()
                    .code(UUID.randomUUID().toString())
                    .debit(value)
                    .account(account)
                    .credit(BigDecimal.ZERO)
                    .balance(balance.subtract(value))
                    .date(LocalDateTime.now())
                    .build();
            default -> throw new DomainException("Operation invalid: " + typeOperation.getOperation());
        };
    }
}
