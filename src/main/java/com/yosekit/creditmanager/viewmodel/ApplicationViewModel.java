package com.yosekit.creditmanager.viewmodel;

import com.yosekit.creditmanager.model.Application.ApplicationStatus;
import com.yosekit.creditmanager.model.Client;
import com.yosekit.creditmanager.model.Passport;
import com.yosekit.creditmanager.util.EnumFormatter;
import com.yosekit.creditmanager.util.ModelFormatter;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class ApplicationViewModel {

    private final Long id;
    private final String requiredAmount;
    private final String requiredTerm;
    private final String createdAt;
    private final String client;
    private final String passport;
    private final String status;

    public ApplicationViewModel(
            Long id,
            BigDecimal requiredAmount,
            LocalDate requiredTerm,
            LocalDateTime createdAt,
            Client client,
            Passport passport,
            ApplicationStatus applicationStatus
    ) {
            this.id = id;
            this.requiredAmount = ModelFormatter.formatMoneyAmount(requiredAmount);
            this.requiredTerm = ModelFormatter.formatDate(requiredTerm);
            this.createdAt = ModelFormatter.formatTimestamp(createdAt);
            this.client = this.formatClient(client);
            this.passport = this.formatPassport(passport);
            this.status = EnumFormatter.format(applicationStatus);
    }

    private String formatClient(Client client) {
        return client.getFullName().trim();
    }

    private String formatPassport(Passport passport) {
        return passport.getNumber() + " " + passport.getSeries();
    }
}
