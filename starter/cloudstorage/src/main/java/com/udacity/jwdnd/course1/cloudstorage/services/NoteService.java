package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private UserMapper userMapper;
    private NoteMapper noteMapper;

    public NoteService(UserMapper userMapper, NoteMapper noteMapper) {
        this.userMapper = userMapper;
        this.noteMapper = noteMapper;
    }

    public String editNote(NoteForm noteForm, String username) {

        Integer userId = userMapper.getUserId(username);
        Integer noteId = Integer.valueOf(noteForm.getNoteId());

        NoteForm origNote = noteMapper.getNote(userId, noteId);

        String proposedTitle = noteForm.getNoteTitle();
        String proposedText = noteForm.getNoteText();

        // Is the title an empty string, once whitespace is removed?
        if (proposedTitle.replaceAll(" ", "").isEmpty()) {
            return "Please include some text in the note title.";
        }

        // If proposing to change the title, does the user already have a note with this title?
        if (!proposedTitle.equals(origNote.getNoteTitle())) {
            List<String> existingNoteTitles = noteMapper.getNoteTitles(userId);
            if (existingNoteTitles.contains(proposedTitle)) {
                return "Please choose a unique note title.";
            }
        }

        noteForm.setUserId(userId);
        noteMapper.update(userId, noteId, proposedTitle, proposedText);
        return null;
    }

    public String addNote(NoteForm noteForm, String username) {

        Integer userId = userMapper.getUserId(username);

        // Is the title an empty string, once whitespace is removed?
        String submittedTitle = noteForm.getNoteTitle();
        if (submittedTitle.replaceAll(" ", "").isEmpty()) {
            return "Please include some text in the note title.";
        }

        // Does the user already have a note of this title?
        List<String> existingNoteTitles = noteMapper.getNoteTitles(userId);
        if (existingNoteTitles.contains(submittedTitle)) {
            return "Please choose a unique note title.";
        }

        noteForm.setUserId(userId);
        noteMapper.insert(noteForm);
        return null;
    }

    public List<NoteForm> getNotes(String username) {

        Integer userId = userMapper.getUserId(username);
        List<NoteForm> notes = noteMapper.getNotes(userId);
        return notes;
    }

    public void deleteNote(String username, Integer noteId) {

        Integer userId = userMapper.getUserId(username);
        noteMapper.deleteNote(userId, noteId);
    }
}
