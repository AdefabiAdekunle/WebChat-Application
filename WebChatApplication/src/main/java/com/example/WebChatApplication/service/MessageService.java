package com.example.WebChatApplication.service;


import com.example.WebChatApplication.mapper.MessageMapper;
import com.example.WebChatApplication.model.ChatForm;
import com.example.WebChatApplication.model.ChatMessage;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


@Service
public class MessageService {
    //    private String message;
//
//    public MessageService(String message){
//        this.message= message;
//    }
//
//    public String uppercase(){
//        return this.message.toUpperCase();
//    }
//
//    public String lowercase(){
//        return this.message.toLowerCase();
//    }
//
//    @PostConstruct
//    //Post Construct helps the below method to be executed when
//    // a Class is initiated
//    public void postConstruct(){
//        System.out.println("Creating MessageService bean");
//    }
    private MessageMapper messageMapper;

    public MessageService(MessageMapper messageMapper) {
        this.messageMapper = messageMapper;
    }

    private List<ChatMessage> chatMessages;
    private final List<String> bannedWords= new ArrayList<>(List.of("sex","fuck","pussy","dick"));

    @PostConstruct
    public void postConstruct() {
        System.out.println("Creating MessageService bean");
        this.chatMessages = new ArrayList<>();
    }

    public void addMessage(ChatForm chatForm) {
        ChatMessage newMessage = new ChatMessage();
        newMessage.setUsername(chatForm.getUsername());
        String words= chatForm.getMessageText();
        boolean status=true;
        for(String value:bannedWords){
            if(words.contains(value)){
                status=false;
            }
        }
        switch (chatForm.getMessageType()) {
            case "Say":
                if(status){
                    newMessage.setMessageText(chatForm.getMessageText());
                }
                else{
                    newMessage.setMessageText("");
                }
                break;
            case "Shout":
                if(status){
                    newMessage.setMessageText(chatForm.getMessageText().toUpperCase());
                }
                else{
                    newMessage.setMessageText("");
                }
                break;
            case "Whisper":
                if(status){
                    newMessage.setMessageText(chatForm.getMessageText().toLowerCase());
                }
                else{
                    newMessage.setMessageText("");
                }
                break;
        }
        messageMapper.addMessage(newMessage);
    }

    public List<ChatMessage> getChatMessages() {
        return messageMapper.getAllMessages();
    }
}
