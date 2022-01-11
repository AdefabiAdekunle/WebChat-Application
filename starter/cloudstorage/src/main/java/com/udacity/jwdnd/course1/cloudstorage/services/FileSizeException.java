package com.udacity.jwdnd.course1.cloudstorage.services;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class FileSizeException {
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleFileSizeException(MaxUploadSizeExceededException e, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("error","File is too big");
        return "redirect:/home";
    }
}
