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
@Table(name = "application")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "required_amount", nullable = false)
    private BigDecimal requiredAmount;

    @Column(name = "required_term", nullable = false)
    private LocalDate requiredTerm;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Basic(optional = false)
    @Column(name = "status")
    private ApplicationStatus status = ApplicationStatus.OPEN;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false)
    private Client client;

    @OneToOne(mappedBy = "application")
    private Contract contract;


    public enum ApplicationStatus {
        OPEN,
        REJECTED,
        APPROVED,
    }

    public Application(BigDecimal requiredAmount, LocalDate requiredTerm) {
        this.requiredAmount = requiredAmount;
        this.requiredTerm = requiredTerm;
    }
}
