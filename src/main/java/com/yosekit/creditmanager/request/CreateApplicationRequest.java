package com.yosekit.creditmanager.request;

import jakarta.validation.Valid;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;


@Getter
@Setter
@ToString
public class CreateApplicationRequest {

    @Valid
    private CreateClientRequest client;

    private BigDecimal requiredAmount;
    private LocalDate requiredTerm;

    public CreateApplicationRequest(CreateClientRequest client) {
        this.client = client;
    }

    // term must be string ISO 8601
    public void setRequiredTerm(String requiredTerm) {
        this.requiredTerm = LocalDate.now().plus(Period.parse(requiredTerm));
    }
}
