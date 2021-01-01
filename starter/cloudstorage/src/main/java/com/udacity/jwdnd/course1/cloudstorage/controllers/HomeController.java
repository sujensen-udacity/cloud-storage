package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileStorageService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping
public class HomeController {

    private FileStorageService fileStorageService;
    private UserService userService;

    public HomeController(FileStorageService fileStorageService, UserService userService) {
        this.fileStorageService = fileStorageService;
        this.userService = userService;
    }

    @GetMapping("/home")
    public String getHomePage(Principal principal, Model model) {

        model.addAttribute("storedFiles", fileStorageService.getFiles(principal.getName()));
        return "home";
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam String fileName, Principal principal) throws InterruptedException, IOException {

        File newFile = fileStorageService.getFile(principal.getName(), fileName);
        InputStreamResource resource = new InputStreamResource(newFile.getFileData());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + fileName + "\"")
                .contentLength(Long.valueOf(newFile.getFileSize()))
                .contentType(MediaType.parseMediaType(newFile.getContentType()))
                .body(resource);

    }

    @GetMapping("/delete")
    public String deleteFile(@RequestParam String fileName, Principal principal, Model model) {

        String resultError = null;

        // Get the authenticated user name
        User user = userService.getUser(principal.getName());

        // Delete the file for that user
        fileStorageService.deleteFile(user.getUsername(), fileName);

        model.addAttribute("storedFiles", fileStorageService.getFiles(principal.getName()));

        return "result";
    }

    @PostMapping("/home")
    public String setHomePage(Principal principal, Model model) {

        model.addAttribute("storedFiles", fileStorageService.getFiles(principal.getName()));
        return "home";
    }

    @PostMapping("/file-upload")
    public String handleFileUpload(@RequestParam("fileUpload") MultipartFile fileUpload,
                                   Principal principal,
                                   Model model) throws IOException {

        String resultError = null;

        // Get the authenticated user name
        User user = userService.getUser(principal.getName());

        // If the filename already exists for that user, that's an error.
        if (!fileStorageService.isFilenameAvailable(user.getUsername(), fileUpload.getOriginalFilename())) {
            resultError = "The filename already exists.";
        }

        // Upload the file for that user
        if (resultError == null) {
            File newFile = new File(null,
                    fileUpload.getOriginalFilename(),
                    fileUpload.getContentType(),
                    String.valueOf(fileUpload.getSize()),
                    user.getUserId(),
                    fileUpload.getInputStream());
            fileStorageService.uploadFile(newFile);
        }

        if (resultError != null) {
            model.addAttribute("resultError", resultError);
        }
        model.addAttribute("storedFiles", fileStorageService.getFiles(principal.getName()));

        return "result";
    }
}
