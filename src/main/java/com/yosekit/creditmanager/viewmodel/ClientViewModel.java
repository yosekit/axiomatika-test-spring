package com.yosekit.creditmanager.viewmodel;

import com.yosekit.creditmanager.model.Client.FamilyStatus;
import com.yosekit.creditmanager.model.Employment;
import com.yosekit.creditmanager.model.Passport;
import com.yosekit.creditmanager.util.EnumFormatter;
import com.yosekit.creditmanager.util.ModelFormatter;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ClientViewModel {

    private final Long id;
    private final String passport;
    private final String fullName;
    private final String birthdate;
    private final String phone;
    private final String familyStatus;
    private final String employment;

    public ClientViewModel(
            Long id,
            Passport passport,
            String fullName,
            LocalDate birthdate,
            String phone,
            FamilyStatus familyStatus,
            Employment employment
    ) {
        this.id = id;
        this.passport = this.formatPassport(passport);
        this.fullName = fullName;
        this.birthdate = ModelFormatter.formatDate(birthdate);
        this.phone = ModelFormatter.formatPhone(phone);
        this.familyStatus = EnumFormatter.format(familyStatus);
        this.employment = this.formatEmployment(employment);
    }

    private String formatPassport(Passport passport) {
        return  passport.getSeries() + " " + passport.getNumber();
    }

    private String formatEmployment(Employment employment) {
        return employment.getCompanyName() + " - " + employment.getPost() + " - "
                + ModelFormatter.formatMoneyAmount(employment.getSalary());
    }
}
