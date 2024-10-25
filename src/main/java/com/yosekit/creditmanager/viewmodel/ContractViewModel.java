package com.yosekit.creditmanager.viewmodel;

import com.yosekit.creditmanager.model.Client;
import com.yosekit.creditmanager.model.Contract;
import com.yosekit.creditmanager.util.ModelFormatter;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class ContractViewModel {

    private final Long id;
    private final String amount;
    private final String term;
    private final String createdAt;
    private final String client;
    private final String signDate;
    private final String signStatus;

    public ContractViewModel(
            Long id,
            BigDecimal amount,
            LocalDate term,
            LocalDateTime createdAt,
            Client client,
            LocalDate signDate,
            Contract.ContractSignStatus signStatus
    ) {
        this.id = id;
        this.amount = ModelFormatter.formatMoneyAmount(amount);
        this.term = ModelFormatter.formatDate(term);
        this.createdAt = ModelFormatter.formatTimestamp(createdAt);
        this.client = this.formatClient(client);
        this.signDate = signDate != null ? ModelFormatter.formatDate(signDate) : null;
        this.signStatus = this.formatStatus(signStatus);
    }

    private String formatClient(Client client) {
        var passport = client.getPassport();

        String result = client.getFullName() +
                " [" +
                passport.getSeries() +
                " " +
                passport.getNumber() +
                "]";

        return result.trim();
    }

    private String formatStatus(Contract.ContractSignStatus status) {
        return switch (status) {
            case UNSIGNED -> "Не подписан";
            case SIGNED -> "Подписан";
        };
    }
}
