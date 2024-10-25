package com.yosekit.creditmanager.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "contract")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "term", nullable = false)
    private LocalDate term;

    @Column(name = "sign_date")
    private LocalDate signDate;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Basic(optional = false)
    @Column(name = "sign_status")
    private ContractSignStatus signStatus;

    @OneToOne
    @JoinColumn(name = "application_id", referencedColumnName = "id")
    private Application application;


    public enum ContractSignStatus {
        UNSIGNED,
        SIGNED,
    }
}
