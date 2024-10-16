package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.*;
import com.example.exception.*;
import com.example.repository.*;
import java.util.*;


@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private AccountRepository accountRepository;

    public Message createMessage(String messageText, int postedBy, long timePostedEpoch){
        Optional<Account> acc = accountRepository.findById(postedBy);
        if(messageText.length() <= 255 && acc.isPresent() && messageText != ""){
            Message message = new Message(postedBy, messageText, timePostedEpoch);
            messageRepository.save(message);
            return message;
        }else{
            return null;
        }
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Message getMessageById(int messageId){
        Optional<Message> message = messageRepository.findById(messageId);
        if(message.isPresent()){
            Message newMessage = message.get();
            return newMessage;
        }else{
            return null;
        }
    }

    public Integer deleteMessageById(int messageId){
        Optional<Message> message = messageRepository.findById(messageId);
        if(message.isPresent()){
            messageRepository.deleteById(messageId);
            return 1;
        }else{
            return null;
        }
    }

    public boolean updateMessageById(int messageId,Message msg){
        Optional<Message> message = messageRepository.findById(messageId);
        if(message.isPresent() && msg.getMessageText().length() <= 255 && msg.getMessageText() != ""){
            Message newMessage = message.get();
            newMessage.setMessageText(msg.getMessageText());
            messageRepository.save(newMessage);
            return true;
        }else{
            return false;
        }
    }


}
