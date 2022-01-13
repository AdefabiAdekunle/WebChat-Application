package com.example.WebChatApplication;


import com.example.WebChatApplication.model.ChatMessage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ChatPage2 {

    @FindBy(id="messageText")
    private WebElement textField;

    @FindBy(id="submitMessage")
    private WebElement submitButton;



    @FindBy( className= "chatMessageUsername")
    private List<WebElement> firstMessageUsername;

    @FindBy(className ="chatMessageText")
    private List<WebElement> firstMessageText;

    @FindBy(id="logout-Button")
    private  WebElement logoutButton;

    public ChatPage2(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void sendChatMessage(String text) {
        this.textField.sendKeys(text);
        this.submitButton.click();
    }

    public ChatMessage getFirstMessage() {
        ChatMessage result = new ChatMessage();
        result.setMessageText(firstMessageText.get(firstMessageText.size()-1).getText());
        result.setUsername(firstMessageUsername.get(firstMessageUsername.size()-1).getText());
        System.out.println(firstMessageUsername.size());;
        return result;
    }

    public void setLogoutButton(){
        this.logoutButton.click();
    }

}

