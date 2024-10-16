package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.*;
import com.example.exception.*;
import com.example.repository.*;
import java.util.*;



@Service
public class AccountService{
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private MessageRepository messageRepository;

    public Account registerAccount(String username, String password) throws InvalidInputException,PreexistingUsernameException{
        Optional<Account> accExist = accountRepository.findByUsername(username);
        if(accExist == null || username == "" || password.length() < 4){
            throw new InvalidInputException("Invalid Input");
        } 
        
        if(accExist.isPresent()){
            throw new PreexistingUsernameException("Invalid User");
        }
 
        Account newaccount = new Account(username,password);
        return accountRepository.save(newaccount);
    }

    public Account loginAccount(String username, String password) throws IncorrectLoginException{
        Optional<Account> accExist = accountRepository.findByUsernameAndPassword(username,password);
        if(accExist.isPresent()){
          return accExist.get();
        }else{
            throw new IncorrectLoginException("Incorrect Username or Password");
        }
    }

    public List<Message> getMessagesByUser(int accountId){
        return messageRepository.findByPostedBy(accountId);
    }


}
