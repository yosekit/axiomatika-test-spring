package com.yosekit.creditmanager.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "passport")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Passport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "series", nullable = false)
    private String series;

    @Column(name = "number", nullable = false)
    private String number;

    @Column(name = "issuance_date", nullable = false)
    private LocalDate issuanceDate;

    @Column(name = "subdivision_code", nullable = false)
    private String subdivisionCode;

    @Column(name = "issued", nullable = false)
    private String issued;

    @OneToOne(mappedBy = "passport")
    private Client client;

    public Passport(
            String series,
            String number,
            LocalDate issuanceDate,
            String subdivisionCode,
            String issued
    ) {
        this.series = series;
        this.number = number;
        this.issuanceDate = issuanceDate;
        this.subdivisionCode = subdivisionCode;
        this.issued = issued;
    }
}
