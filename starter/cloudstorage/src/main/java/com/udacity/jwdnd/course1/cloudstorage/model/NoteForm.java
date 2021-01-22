package com.udacity.jwdnd.course1.cloudstorage.model;

public class NoteForm {

    private String noteId;
    private String noteTitle;
    private String noteText;
    private Integer userId;

    public NoteForm(String noteId, String noteTitle, String noteText, Integer userId) {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.noteText = noteText;
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }


}
