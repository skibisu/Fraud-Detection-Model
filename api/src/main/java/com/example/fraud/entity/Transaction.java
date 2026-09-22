package com.example.fraud.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double amount;
    @Column(nullable = false)
    private Integer hour;
    @Column(nullable = false)
    private Integer distanceFromHomeKm;
    @Column(nullable = false)
    private Boolean foreignTransaction;
    @Column(nullable = false)
    private Boolean onlineTransaction;
    @Column(nullable = false)
    private Integer transactionsLast24h;
    @Column(nullable = false)
    private Double fraudProbability;
    @Column(nullable = false)
    private Boolean fraud;
    @Column(nullable = false)
    private String modelVersion;
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    protected Transaction() {}

    public Transaction(Double amount, Integer hour, Integer distanceFromHomeKm,
                       Boolean foreignTransaction, Boolean onlineTransaction,
                       Integer transactionsLast24h, Double fraudProbability,
                       Boolean fraud, String modelVersion) {
        this.amount = amount;
        this.hour = hour;
        this.distanceFromHomeKm = distanceFromHomeKm;
        this.foreignTransaction = foreignTransaction;
        this.onlineTransaction = onlineTransaction;
        this.transactionsLast24h = transactionsLast24h;
        this.fraudProbability = fraudProbability;
        this.fraud = fraud;
        this.modelVersion = modelVersion;
    }

    @PrePersist
    void onCreate() { createdAt = Instant.now(); }

    public Long getId() { return id; }
    public Double getAmount() { return amount; }
    public Integer getHour() { return hour; }
    public Integer getDistanceFromHomeKm() { return distanceFromHomeKm; }
    public Boolean getForeignTransaction() { return foreignTransaction; }
    public Boolean getOnlineTransaction() { return onlineTransaction; }
    public Integer getTransactionsLast24h() { return transactionsLast24h; }
    public Double getFraudProbability() { return fraudProbability; }
    public Boolean getFraud() { return fraud; }
    public String getModelVersion() { return modelVersion; }
    public Instant getCreatedAt() { return createdAt; }
}

