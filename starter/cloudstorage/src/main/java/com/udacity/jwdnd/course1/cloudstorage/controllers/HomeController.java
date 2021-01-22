package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileStorageService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
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
    private NoteService noteService;
    private UserService userService;

    public HomeController(FileStorageService fileStorageService, NoteService noteService, UserService userService) {
        this.fileStorageService = fileStorageService;
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping("/home")
    public String getHomePage(@ModelAttribute("noteForm") NoteForm noteForm, Principal principal, Model model) {
        System.out.println("In GetMapping home");
        List<NoteForm> notes = noteService.getNotes(principal.getName());
        System.out.println("  go through notes:");
        for (NoteForm nf : notes) {
            System.out.println("    note id = " + nf.getNoteId() + ", title = " + nf.getNoteTitle() + ", text = " + nf.getNoteText());
        }

        // Get the latest list of files
        model.addAttribute("storedFiles", fileStorageService.getFiles(principal.getName()));

        // Get the latest list of notes
        model.addAttribute("storedNotes", noteService.getNotes(principal.getName()));

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

    @GetMapping("/deleteFile")
    public String deleteFile(@RequestParam String fileName, Principal principal, Model model) {

        String resultError = null;

        // Get the authenticated user name
        User user = userService.getUser(principal.getName());

        // Delete the file for that user
        fileStorageService.deleteFile(user.getUsername(), fileName);

        return "result";
    }

    @GetMapping("/deleteNote")
    public String deleteNote(@RequestParam Integer noteId, Principal principal, Model model) {

        String resultError = null;

        // Get the authenticated user name
        User user = userService.getUser(principal.getName());

        // Delete the note for that user
        noteService.deleteNote(user.getUsername(), noteId);

        return "result";
    }

    @PostMapping("/home")
    public String setHomePage(Principal principal, Model model) {
        System.out.println("In PostMapping home");

        // Get the latest list of files
        model.addAttribute("storedFiles", fileStorageService.getFiles(principal.getName()));

        // Get the latest list of notes
        model.addAttribute("storedNotes", noteService.getNotes(principal.getName()));

        return "home";
    }

    @PostMapping("/note-add-edit")
    public String addNote(@ModelAttribute("noteForm") NoteForm noteForm, Principal principal, Model model) {
        System.out.println("In PostMapping note-add");
        System.out.println("  noteForm text = " + noteForm.getNoteText());

        // Keep track of any errors from the services, which are passed via the model to the results page
        String resultError = null;

        // Get the authenticated user name
        User user = userService.getUser(principal.getName());

        // Does the note already have an ID?
        System.out.println("check if the note has an id! " + noteForm.getNoteId());
        if (!noteForm.getNoteId().isEmpty()) {

            // Edit the note, catching any error as a String result.
            resultError = noteService.editNote(noteForm, user.getUsername());
        } else {

            // Add the note, catching any error as a String result.
            resultError = noteService.addNote(noteForm, user.getUsername());
        }

        // Pass any errors to the model for the results page
        if (resultError != null) {
            model.addAttribute("resultError", resultError);
        }
        return "result";
    }

    @PostMapping("/file-upload")
    public String handleFileUpload(@RequestParam("fileUpload") MultipartFile fileUpload,
                                   Principal principal,
                                   Model model) throws IOException {

        // Keep track of any errors from the services, which are passed via the model to the results page
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

        // Pass any errors to the model for the results page
        if (resultError != null) {
            model.addAttribute("resultError", resultError);
        }

        return "result";
    }
}
