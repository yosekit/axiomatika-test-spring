package com.yosekit.creditmanager.model;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "client")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    @Column(name = "phone", nullable = false)
    private String phone;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Basic(optional = false)
    @Column(name = "family_status")
    private FamilyStatus familyStatus;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "employment_id", referencedColumnName = "id")
    private Employment employment;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "passport_id", referencedColumnName = "id")
    private Passport passport;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Address> addresses;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Application> applications;

    public enum FamilyStatus {
        SINGLE,
        MARRIED,
    }

    public Client (
            String fullName,
            LocalDate birthdate,
            String phone,
            FamilyStatus familyStatus
    ) {
        this.fullName = fullName;
        this.birthdate = birthdate;
        this.phone = phone;
        this.familyStatus = familyStatus;
    }

    public void addAddress(Address address) {
        if (this.addresses == null) {
            this.addresses = new HashSet<>();
        }
        this.addresses.add(address);
    }

    public void addApplication(Application application) {
        if (this.applications == null) {
            this.applications = new HashSet<>();
        }
        this.applications.add(application);
    }
}
