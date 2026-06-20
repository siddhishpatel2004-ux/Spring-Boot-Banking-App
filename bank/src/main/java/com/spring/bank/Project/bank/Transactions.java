package com.spring.bank.Project.bank;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name="transactionHistory")
public class Transactions {

        @Id
        @GeneratedValue
        @Column(name="id")
            private Long id;
        @Column(name="account_number")
            private String accountNumber;
        @Column(name="type")
            private String type;
        @Column(name="amount")
            private Double amount;

    public void setId(Long id) {
        this.id = id;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }



}
