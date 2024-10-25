package com.yosekit.creditmanager.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class CreatePassportRequest {

    private String series;
    private String number;
    private LocalDate issuanceDate;
    private String subdivisionCode;
    private String issued;
}
