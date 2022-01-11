package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    @FindBy(css="#logout-button")
    private WebElement logoutButton;

    @FindBy(css="#noteModalLabel")
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



    public HomePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void logoutHomePage(){
        logoutButton.click();
    }

//    public void noteTab(){
//        noteModalTab.click();
//    }

    public NoteForm addNote(String noteTitle, String noteDescription, String noteId){
        noteModalTab.click();
        addNewNote.click();
        titleNote.sendKeys(noteTitle);
        descriptionNote.sendKeys(noteDescription);
        NoteForm noteForm = new NoteForm();
        noteForm.setNoteId(noteId);
        noteForm.setNoteTitle(noteTitle);
        noteForm.setNoteDescription(noteDescription);
        saveChanges.click();
        clickContinue.click();
        return  noteForm;
    }

}
