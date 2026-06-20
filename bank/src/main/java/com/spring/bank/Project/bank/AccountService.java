package com.spring.bank.Project.bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class AccountService {
    @Autowired
     Repo repository;
    @Autowired
    TransactionsRepository Trepository;

    public String Login(Account acc){

        String email=acc.getEmailAddress();
        Account exists = repository.findByEmailAddress(acc.getEmailAddress());
        if(email.isEmpty()||exists==null){
            throw new RuntimeException("Account not found , Register yourself !");
        }
        if(!(email.contains("@"))|| !(email.contains("."))){
            throw new RuntimeException("Invalid Input !");
        }
        String Password=acc.getPassword();
        if(Password.length()>8){
            throw new RuntimeException("Password should be 8 characters !");
        }

        if(!(exists.getPassword().equals(Password))){
            throw new RuntimeException("incorrect Password");
        }



        return "Login Successful ";
    }
    public String Create_account(Account acc){
    String name=acc.getName();




    if (name.isEmpty()){
        throw new RuntimeException("Fill out your Name");


    }for ( int i=0; i<acc.getName().length();i++){
            if (Character.isDigit(name.charAt(i))){
               throw new RuntimeException("Invalid Input , Try again! ");

            }
        }
    acc.setName(name);


    Double deposit= acc.getBalance();
    if(deposit<1000){ throw new RuntimeException("Minimum initial Deposit is rs.1000 ");
    }

    acc.setBalance(deposit);



    String phone= acc.getPhone();
    Account PhoneExist= repository.findByPhone(acc.getPhone());
        if(PhoneExist!= null ){
            throw new RuntimeException("User already Exists !");
        }
        if (phone.isEmpty()){
            throw new RuntimeException("fill out your Mobile Number");
        }for ( int i =0; i<acc.getPhone().length();i++){
            if(!(Character.isDigit(phone.charAt(i)))){
               throw new RuntimeException("Invalid Input , Try Again !");
            }
        }
        if(phone.length() !=10){
            throw new RuntimeException("The phone must contain 10 digits");
        }
        acc.setPhone(phone);

    String email= acc.getEmailAddress();
    Account EmailExist=repository.findByEmailAddress(acc.getEmailAddress());
        if(EmailExist!=null){
            throw new RuntimeException("Email already exists");
        }
        if (email.isEmpty()||!(email.contains("@")||!(email.contains(".")))){
                throw new RuntimeException("Invalid email address");
        }
        acc.setEmailAddress(email);


        String Password=acc.getPassword();
        if(Password.length()>8){
            throw new RuntimeException("Password should be 8 characters !");
        }
        acc.setPassword(Password);


        String accNo="1023100101";
        Random rand = new Random();

        for (int i = 0; i < 6; i++) {
             accNo += rand.nextInt(6);
        }
        acc.setAccountNumber(accNo);


        repository.save(acc);




        return " ACCOUNT SUCCESSFULLY CREATED !"+"here is your account number "+accNo;

    }
    public String deposit(Account acc,Transactions trs){
        String AccountNumber =acc.getAccountNumber();
        Account existing =repository.findByAccountNumber(acc.getAccountNumber());

        if (AccountNumber==null|| existing==null){
            throw new RuntimeException("Account not found ! Create an account first !");
        }
        Double deposit=acc.getBalance();
        if (deposit==null){
            throw new RuntimeException("Invalid Input");
        }
        if (deposit<0){
            throw new RuntimeException("Invalid Amount");
        }
        Account bal= repository.findByAccountNumber(acc.getAccountNumber());
        bal.setBalance(bal.getBalance()+deposit);
        repository.save(bal);

        trs.setAccountNumber(bal.getAccountNumber());
        trs.setType("Deposit");
        trs.setAmount(deposit);
        Trepository.save(trs);


        return "Money deposit Successfully! ";

    }public String withdraw(Account acc,Transactions trs){

        String AccountNumber=acc.getAccountNumber();
        Account existing=repository.findByAccountNumber(acc.getAccountNumber());

            if(AccountNumber==null||existing==null){
                    throw new RuntimeException("Account not found ! Create an account first !");
            }

        Double withdraw=acc.getBalance();

            if(withdraw==null){
                    throw new RuntimeException("Invalid Input");
            }if (withdraw<0){
            throw new RuntimeException("Invalid input !");
        }if (withdraw>existing.getBalance()){
                throw new RuntimeException("Insufficient Balance !");
        }


            existing.setBalance(existing.getBalance()-withdraw);
            repository.save(existing);
            trs.setAccountNumber(existing.getAccountNumber());
            trs.setType("Withdraw");
            trs.setAmount(withdraw);
            Trepository.save(trs);


                return "Withdraw successful";

    }

        public String showBalance(Account acc){
                String AccountNumber = acc.getAccountNumber();
                Account exist=repository.findByAccountNumber(acc.getAccountNumber());
                if(exist ==null || AccountNumber==null){
                    throw new RuntimeException("Account not found ! Create an account first ! ");
                }

                return "Your Current balance is " + exist.getBalance();
            }
        public List<Transactions> transactionHistory(Transactions trs){

                String AccountNumber=trs.getAccountNumber();
            List<Transactions> history=Trepository.findByAccountNumber(trs.getAccountNumber());
                    if(history.isEmpty()||AccountNumber.isEmpty()){
                        throw new RuntimeException("Account not found ! Create an account first ! ");
                    }


                return history;
            }
    }

