package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.example.entity.*;
import com.example.exception.*;
import com.example.service.*;

import java.util.*;

import javax.websocket.server.PathParam;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Account> register(@RequestBody Account account){
        try {
            Account newAccount = accountService.registerAccount(account.getUsername(), account.getPassword());
            return ResponseEntity.ok(newAccount);  
        } catch (PreexistingUsernameException  e) {
            return ResponseEntity.status(409).body(null); // 409
        } catch (InvalidInputException e) {
            return ResponseEntity.status(400).body(null); // 400
        }
    }
    
    @PostMapping(value = "/login")
    public ResponseEntity<Account> login(@RequestBody Account account){
        try{
            Account newAccount = accountService.loginAccount(account.getUsername(),account.getPassword());
            return ResponseEntity.ok(newAccount);
        } catch(IncorrectLoginException e){
            return ResponseEntity.status(401).body(null);
        }
    }

    @PostMapping(value ="/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message){
        Message newMessage = messageService.createMessage(message.getMessageText(), message.getPostedBy(), message.getTimePostedEpoch());
        if(newMessage != null){
            return ResponseEntity.ok(newMessage);
        }else{
            return ResponseEntity.status(400).body(null);
        }
    }

    @GetMapping(value = "/messages")
    public ResponseEntity<List<Message>> getMessage(){
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    @GetMapping(value = "/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable int messageId){
        return ResponseEntity.status(200).body(messageService.getMessageById(messageId));
    }

    @DeleteMapping(value = "/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable int messageId){
        return ResponseEntity.status(200).body(messageService.deleteMessageById(messageId));
    }

    @PatchMapping(value = "/messages/{messageId}")
    public ResponseEntity<Integer> updateMessageById(@PathVariable int messageId,@RequestBody Message message){
        if(messageService.updateMessageById(messageId,message)){
            return ResponseEntity.status(200).body(1);
        }else{
            return ResponseEntity.status(400).body(null);
        }
    }

    @GetMapping(value = "/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByUser(@PathVariable int accountId){
        return ResponseEntity.status(200).body(accountService.getMessagesByUser(accountId));
    }
}
