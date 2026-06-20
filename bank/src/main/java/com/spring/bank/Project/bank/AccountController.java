package com.spring.bank.Project.bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bank")
public class AccountController {

    @Autowired
    AccountService service;
    @PostMapping("/login")
    public String login(@RequestBody Account auth){
        return service.Login(auth);
    }
    @PostMapping("/create")
    public String create_Account(@RequestBody Account acc){
        return service.Create_account(acc);

        }
    @PostMapping("/deposit")
        public String deposit(@RequestBody Account acc,Transactions trs)
    {
            return service.deposit(acc,trs);
        }
    @PostMapping("/withdraw")
        public String withdraw (@RequestBody Account acc,Transactions trs){
            return service.withdraw(acc,trs);
    }
    @PostMapping("/showbalance")
        public String showBalance(@RequestBody Account acc){
            return service.showBalance(acc);
    }
    @GetMapping("/transactions")
        public List<Transactions> transactionsHistory(@RequestBody Transactions trs){
        return service.transactionHistory(trs);
    }

}
