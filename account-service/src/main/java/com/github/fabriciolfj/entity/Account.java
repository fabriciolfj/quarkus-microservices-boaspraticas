package com.github.fabriciolfj.entity;

import com.github.fabriciolfj.exceptions.DomainException;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {

    @EqualsAndHashCode.Include
    private String uuid;
    private String customer;
    private LocalDateTime createDate;
    private BigDecimal overdraft;
    private List<Extract> extracts;

    public Extract findExtractFirst() {
        if (Objects.nonNull(extracts) && !extracts.isEmpty()) {
            var extract = extracts.stream().findFirst();

            if (!extract.isPresent()) {
                throw new DomainException("Extract not found to account: " + uuid);
            }

            return extract.get();
        }

        throw new DomainException("Extracts is null to account: " + uuid);
    }
}
