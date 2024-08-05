package com.ars.reactiveapi.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("transaction")
@Builder
public class Transaction {

    @Id
    @Column("transaction_id")
    private Long transactionId;

    @Column("transaction_date")
    private LocalDateTime transactionDate;

    @Column("user_id")
    private Long userId;

    @Column("status")
    private String status;

    @Column("payment_method")
    private String paymentMethod;
}
