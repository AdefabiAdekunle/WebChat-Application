package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class HomePage {
    @FindBy(css="#logout-button")
    private WebElement logoutButton;

    @FindBy(css="#nav-notes-tab")
    private WebElement noteModalTab;

    @FindBy(css="#add-note")
    private WebElement addNewNote;

    @FindBy(css="#note-id")
    private WebElement idNote;

    @FindBy(css="#note-title")
    private WebElement titleNote;

    @FindBy(css="#note-description")
    private WebElement descriptionNote;

    @FindBy(css="#save-changes")
    private WebElement saveChanges;

    @FindBy(css="#aResultSuccess")
    private WebElement clickContinue;

    @FindBy(css="#note-title-text")
    private WebElement noteTitleText;

    @FindBy(css="#note-description-text")
    private WebElement noteDescriptionText;

    public HomePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void logoutHomePage(){
        logoutButton.click();
    }

    public void noteTab(){
        noteModalTab.click();
    }



}
