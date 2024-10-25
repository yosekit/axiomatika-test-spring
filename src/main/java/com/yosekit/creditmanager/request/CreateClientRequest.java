package com.yosekit.creditmanager.request;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class CreateClientRequest {

    @NotBlank(message = "Full name is required")
    @Size(min = 2, max = 50, message = "Full name must be between 2 and 50 characters")
    private String fullName;
    private LocalDate birthdate;
    private String familyStatus;
    private String phone;

    private String registrationAddress;
    private String residenceAddress;

    private CreatePassportRequest passport;
    private CreateEmploymentRequest employment;

    public CreateClientRequest(
            CreateEmploymentRequest employment,
            CreatePassportRequest passport
    ) {
        this.employment = employment;
        this.passport = passport;
    }
}
