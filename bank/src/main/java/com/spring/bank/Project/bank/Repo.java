package com.spring.bank.Project.bank;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Repo extends JpaRepository<Account,String> {

        Account  findByPhone(String phone);
        Account findByAccountNumber(String accountNumber);
        Account findByEmailAddress(String emailAddress);


}
