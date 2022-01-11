package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.apache.tomcat.util.file.ConfigurationSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@ControllerAdvice
@RequestMapping("/home")
public class HomeController {
    //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
    //https://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@github.com/AdefabiAdekunle/SuperDuperDrive1.git
    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;

    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;

    @Autowired
    private NoteService noteService;

    @Autowired
    private CredentialService credentialService;

    @Autowired
    private EncryptionService encryptionService;

    @GetMapping()
    public String getHomePage(Authentication authentication, @ModelAttribute("newNote") NoteForm newNote,
                              @ModelAttribute("newCredential") CredentialForm newCredential, Model model){
        int currentUserId= userService.getUser(authentication.getName()).getUserId();
        List<File> file = fileService.getFiles(currentUserId);
        model.addAttribute("files",file);
        List<Note> notes = noteService.getNotes(currentUserId);
        model.addAttribute("notes",notes);
        List<Credential> credentials = credentialService.getCredentials(currentUserId);
        model.addAttribute("credentials",credentials);
        model.addAttribute("encryptionService",encryptionService);

        return "home";
    }

    @PostMapping()
    public String uploadMultipleFiles(@RequestParam("files") MultipartFile[] files,Authentication authentication,
                                      @ModelAttribute("newNote") NoteForm newNote,
                                      @ModelAttribute("newCredential") CredentialForm newCredential,Model model) throws IOException {
        String duplicateError=null;
        int currentUserId= userService.getUser(authentication.getName()).getUserId();
        for(MultipartFile file: files){
            System.out.println(file.getSize());
                int filesCount= fileService.getFileContent(file.getOriginalFilename()).size();
                if(filesCount>0){
                    model.addAttribute("result", "notSaved");
                    duplicateError="The File already exists";
                }
                else{
                    fileService.saveFile(file,currentUserId);
                    model.addAttribute("result", "success");
                    duplicateError=null;
                }

        }
        if(duplicateError==null){

            model.addAttribute("result", "success");
        }
        else{
            model.addAttribute("result", "notSaved");

        }

    List<File> file = fileService.getFiles(currentUserId);
       model.addAttribute("files",file);

       return "result";
    }


    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Integer fileId){
        File file = fileService.getFile(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=\""+file.getFileName()+"\"")
                .body(new ByteArrayResource(file.getFileData()));
    }


    @GetMapping("/deleteFile/{fileId}")
    public String deleteFile(@PathVariable Integer fileId,Model model,Authentication authentication,
                             @ModelAttribute("newNote") NoteForm newNote,
                             @ModelAttribute("newCredential") CredentialForm newCredential){
        int noDeletedFile= fileService.deleteFile(fileId);
        if(noDeletedFile >0){

            model.addAttribute("result", "success");
            int currentUserId= userService.getUser(authentication.getName()).getUserId();
            List<File> file = fileService.getFiles(currentUserId);
            model.addAttribute("files",file);
        }

        return "result";
    }


    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleFileSizeException(Authentication authentication,MaxUploadSizeExceededException e, RedirectAttributes redirectAttributes,Model model){
        redirectAttributes.addFlashAttribute("uploadSuccess",true);
        int currentUserId= userService.getUser(authentication.getName()).getUserId();
        List<File> file = fileService.getFiles(currentUserId);
        model.addAttribute("result", "error");
        model.addAttribute("files",file);
        return "result";
    }



}
