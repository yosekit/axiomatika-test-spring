package com.yosekit.creditmanager.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "employment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "post", nullable = false)
    private String post;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "salary", nullable = false)
    private BigDecimal salary;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @OneToOne(mappedBy = "employment")
    private Client client;

    public Employment(
            String post,
            String companyName,
            BigDecimal salary,
            LocalDate startDate)
    {
        this.post = post;
        this.companyName = companyName;
        this.salary = salary;
        this.startDate = startDate;
    }
}
