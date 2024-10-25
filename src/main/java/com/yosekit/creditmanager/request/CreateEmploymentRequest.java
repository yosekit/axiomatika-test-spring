package com.yosekit.creditmanager.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class CreateEmploymentRequest {

    private String companyName;
    private String post;
    private LocalDate workPeriod;
    private BigDecimal salary;
}
