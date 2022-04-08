package com.github.fabriciolfj.business.usecase;

import com.github.fabriciolfj.business.FindAccountAndLastExtract;
import com.github.fabriciolfj.business.SaveExtract;
import com.github.fabriciolfj.entity.CreateExtract;
import com.github.fabriciolfj.entity.Extract;
import lombok.RequiredArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;

@ApplicationScoped
@RequiredArgsConstructor
public class CreateDebit {

    private final FindAccountAndLastExtract find;
    private final ValidationOverdraft validationOverdraft;
    private final SaveExtract saveExtract;

    public Extract execute(final BigDecimal value, final String codeAccount) {
        var account = find.find(codeAccount);
        var extract = CreateExtract.debit(value, account);

        validationOverdraft.execute(account, extract);
        saveExtract.persistExtract(extract);

        return extract;
    }
}
