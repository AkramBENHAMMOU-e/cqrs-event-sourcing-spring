package com.tp.eventsourcingcqrsaxon.query.entity;

import jakarta.persistence.*;
import lombok.*;
import com.tp.eventsourcingcqrsaxon.enums.AccountStatus;

import java.time.Instant;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AccountOperation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Instant date;
    private double amount;
    private String currency;
    @Enumerated(EnumType.STRING)
    private OperationType type;
    @ManyToOne
    private Account account;
}
