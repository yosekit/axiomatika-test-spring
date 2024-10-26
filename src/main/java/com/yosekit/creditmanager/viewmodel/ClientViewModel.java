package com.yosekit.creditmanager.viewmodel;

import com.yosekit.creditmanager.model.Client;
import com.yosekit.creditmanager.model.Employment;
import com.yosekit.creditmanager.model.Passport;
import com.yosekit.creditmanager.util.EnumFormatter;
import com.yosekit.creditmanager.util.ModelFormatter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ClientViewModel {

    @Setter
    private Long id;
    @Setter
    private String fullName;
    private String passport;
    private String birthdate;
    private String phone;
    private String familyStatus;
    private String employment;

    public void setPassport(Passport passport) {
        this.passport = this.formatPassport(passport);
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = ModelFormatter.formatDate(birthdate);
    }

    public void setPhone(String phone) {
        this.phone = ModelFormatter.formatPhone(phone);
    }

    public void setFamilyStatus(Client.FamilyStatus familyStatus) {
        this.familyStatus = EnumFormatter.format(familyStatus);
    }

    public void setEmployment(Employment employment) {
        this.employment = this.formatEmployment(employment);
    }

    public ClientViewModel(
            Long id,
            Passport passport,
            String fullName,
            LocalDate birthdate,
            String phone,
            Client.FamilyStatus familyStatus,
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
