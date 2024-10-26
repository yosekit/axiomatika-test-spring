package com.yosekit.creditmanager.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class CreateApplicationRequest {

    private Long existingClientId;

    @Valid
    private CreateClientRequest client;

    @Positive
    @Min(0)
    private BigDecimal requiredAmount;
    @Future
    private LocalDate requiredTerm;

    public CreateApplicationRequest(CreateClientRequest client) {
        this.client = client;
    }

    // term must be string ISO 8601
    public void setRequiredTerm(String requiredTerm) {
        this.requiredTerm = LocalDate.now().plus(Period.parse(requiredTerm));
    }
}
