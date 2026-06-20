package com.spring.bank.Project.bank;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionsRepository extends JpaRepository <Transactions,String>{

        List<Transactions> findByAccountNumber(String accountNumber);
}
