package com.yosekit.creditmanager.request;

import jakarta.validation.Valid;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;


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
}
