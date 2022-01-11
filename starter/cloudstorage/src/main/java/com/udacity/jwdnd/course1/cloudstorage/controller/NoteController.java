package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("note")
public class NoteController {
    @Autowired
    private UserService userService;

    @Autowired
    private NoteService noteService;



    @GetMapping
    public String getHomePage(Authentication authentication, @ModelAttribute("newNote") NoteForm newNote,
                              @ModelAttribute("newCredential") CredentialForm newCredential,Model model){
        int currentUserId= userService.getUser(authentication.getName()).getUserId();
        List<Note> notes = noteService.getNotes(currentUserId);
        model.addAttribute("notes",notes);


        return "home";
    }

    @PostMapping("/addNotes")
    public String addNotes(Authentication authentication,@ModelAttribute("newNote") NoteForm noteForm,
                           @ModelAttribute("newCredential") CredentialForm newCredential,Model model){

        int currentUserId= userService.getUser(authentication.getName()).getUserId();

        String newTitle = noteForm.getNoteTitle();
        String noteIdString= noteForm.getNoteId();
        String newDescription = noteForm.getNoteDescription();
        if(noteIdString.isEmpty()){
            Note note = new Note(null,noteForm.getNoteTitle(),noteForm.getNoteDescription(),currentUserId);
            note.setUserId(currentUserId);
            int a=noteService.saveFile(note);
        }
        else{
            noteService.updateNote(newTitle,newDescription,Integer.parseInt(noteIdString));
        }

        List<Note> notes = noteService.getNotes(currentUserId);
        model.addAttribute("notes",notes);
        model.addAttribute("result","success");
        return "result";
    }

    @GetMapping("/deleteNote/{noteId}")
    public String deleteFile(@PathVariable Integer noteId, Model model, Authentication authentication,
                             @ModelAttribute("newNote") NoteForm newNote,
                             @ModelAttribute("newCredential") CredentialForm newCredential){
        noteService.deleteNote(noteId);
        int currentUserId= userService.getUser(authentication.getName()).getUserId();
        List<Note> notes = noteService.getNotes(currentUserId);
        model.addAttribute("notes",notes);
        model.addAttribute("result", "success");
        return "result";
    }
}
