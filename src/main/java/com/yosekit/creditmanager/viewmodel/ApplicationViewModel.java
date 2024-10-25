package com.yosekit.creditmanager.viewmodel;

import com.yosekit.creditmanager.model.Application;
import com.yosekit.creditmanager.model.Client;
import com.yosekit.creditmanager.util.ModelFormatter;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

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
    private final String status;

    public ApplicationViewModel(
            Long id,
            BigDecimal requiredAmount,
            LocalDate requiredTerm,
            LocalDateTime createdAt,
            Client client,
            Application.ApplicationStatus status
    ) {
            this.id = id;
            this.requiredAmount = ModelFormatter.formatMoneyAmount(requiredAmount);
            this.requiredTerm = ModelFormatter.formatDate(requiredTerm);
            this.createdAt = ModelFormatter.formatTimestamp(createdAt);
            this.client = this.formatClient(client);
            this.status = this.formatStatus(status);
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

    private String formatStatus(Application.ApplicationStatus status) {
        // TODO enum russian locale (or string returning rule)
        return switch (status) {
            case OPEN -> "Открыта";
            case APPROVED -> "Одобрена";
            case REJECTED -> "Не одобрена";
        };
    }
}
