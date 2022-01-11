package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping("credential")
public class CredentialController {

    @Autowired
    private UserService userService;

    @Autowired
    private EncryptionService  encryptionService;

    @Autowired
    private CredentialService credentialService;

    @GetMapping
    public String getHomePage(Authentication authentication, @ModelAttribute("newNote") NoteForm newNote,
                              @ModelAttribute("newCredential") CredentialForm newCredential, Model model){
        int currentUserId= userService.getUser(authentication.getName()).getUserId();
        List<Credential> credentials = credentialService.getCredentials(currentUserId);
        model.addAttribute("credentials",credentials);
        model.addAttribute("encryptionService",encryptionService);

        return "home";
    }

    @PostMapping("/addCredentials")
    public String addNotes(Authentication authentication,@ModelAttribute("newNote") NoteForm noteForm,
                           @ModelAttribute("newCredential") CredentialForm newCredential,Model model){

        int currentUserId= userService.getUser(authentication.getName()).getUserId();

        String newUrl = newCredential.getUrl();
        String credentialIdString= newCredential.getCredentialId();
        String newUsername = newCredential.getUsername();
        String newPassword = newCredential.getPassword();

        if(credentialIdString.isEmpty()){
            credentialService.saveCredential(newUrl,newUsername,newPassword,currentUserId);
        }
        else{
            credentialService.updateCredential(newUrl,newUsername,newPassword,Integer.parseInt(credentialIdString));
        }

        List<Credential> credentials = credentialService.getCredentials(currentUserId);
        model.addAttribute("credentials",credentials);
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("result","success");
        return "result";
    }


    @GetMapping("/deleteCredential/{credentialId}")
    public String deleteFile(@PathVariable Integer credentialId, Model model, Authentication authentication,
                             @ModelAttribute("newNote") NoteForm newNote,
                             @ModelAttribute("newCredential") CredentialForm newCredential){

        credentialService.deleteCredential(credentialId);
        int currentUserId= userService.getUser(authentication.getName()).getUserId();
        List<Credential> credentials = credentialService.getCredentials(currentUserId);
        model.addAttribute("credentials",credentials);
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("result", "success");
        return "result";
    }


}
