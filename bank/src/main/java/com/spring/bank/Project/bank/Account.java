package com.spring.bank.Project.bank;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Getter;


@Entity
@Table(name="Users")
@Getter
public class Account {


//    @GeneratedValue
//    @Column(name="id")
//    private int id;
    @Id
    @Column(name="account_number")
    private String accountNumber ;
    @Column(name="name")
    private   String name ;
    @Column(name="mobile_number")
    private   String phone ;
    @Column(name="balance")
    private   Double balance;

    @Column(name="email_address")
    private  String emailAddress;
    @Column(name="password")
    private String password;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }



    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }






}
