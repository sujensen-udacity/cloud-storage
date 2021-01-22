package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class HomePage {

    // Home page buttons
    @FindBy(id = "logout-button")
    private WebElement logoutButton;

    // Note tab elements
    @FindBy(id = "nav-notes-tab")
    private WebElement navNotes;
    @FindBy(id = "add-note-button")
    private WebElement addNoteButton;
    @FindBy(id = "edit-note-button")
    private WebElement editNoteButton;
    @FindBy(id = "delete-note-button")
    private WebElement deleteNoteButton;
    @FindBy(id = "tbody-row")
    private List<WebElement> tbodyRows;


    // Note tab modal elements
    @FindBy(id = "note-title")
    private WebElement noteTitle;
    @FindBy(id = "note-description")
    private WebElement noteDescription;
    @FindBy(id = "note-submit")
    private WebElement noteSubmit;
    @FindBy(id = "note-close-button")
    private WebElement noteCloseButton;
    @FindBy(id = "note-save-changes-button")
    private WebElement noteSaveChangesButton;

    // Credentials tab elements
    @FindBy(id = "add-cred-button")
    private WebElement addCredbutton;
    @FindBy(id = "edit-cred-button")
    private WebElement editCredButton;
    @FindBy(id = "delete-cred-button")
    private WebElement deleteCredButton;

    // Credential tab modal elements
    @FindBy(id = "credential-url")
    private WebElement credUrl;
    @FindBy(id = "credential-username")
    private WebElement credUsername;
    @FindBy(id = "credential-password")
    private WebElement credPassword;
    @FindBy(id = "credential-close-button")
    private WebElement credCloseButton;
    @FindBy(id = "credential-save-changes-button")
    private WebElement credSaveChangesButton;


    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void logoutNow() {

        logoutButton.click();
    }

    public void addNote(String title, String desc) throws InterruptedException {

        navNotes.click();
        Thread.sleep(1000);

        addNoteButton.click();
        Thread.sleep(1000);

        noteTitle.sendKeys(title);
        noteDescription.sendKeys(desc);
        noteSaveChangesButton.click();
        Thread.sleep(1000);
    }

    public List<NoteForm> getNotes() throws InterruptedException {

        List<NoteForm> retList = new ArrayList<>();
        navNotes.click();
        Thread.sleep(1000);

        for (WebElement row : tbodyRows) {
            WebElement th = row.findElement(By.tagName("th"));
            String thText = th.getAttribute("innerText");
            WebElement td = row.findElement(By.id("note-desc"));
            String tdText = td.getAttribute("innerText");
            retList.add(new NoteForm(null, thText, tdText, null));
        }
        return retList;
    }

    public void editNote(String origTitle, String newTitle, String newDesc) throws InterruptedException {
        navNotes.click();
        Thread.sleep(1000);

        // Find note with origTitle
        for (WebElement row : tbodyRows) {
            WebElement th = row.findElement(By.tagName("th"));
            String thText = th.getAttribute("innerText");
            if (thText.equals(origTitle)) {
                // Click edit button
                WebElement buttons = row.findElement(By.id("row-buttons"));
                WebElement editButton = buttons.findElement(By.id("edit-note-button"));
                editButton.click();
                Thread.sleep(1000);
                // New title
                noteTitle.clear();
                noteTitle.sendKeys(newTitle);
                Thread.sleep(1000);
                // New desc
                noteDescription.clear();
                noteDescription.sendKeys(newDesc);
                Thread.sleep(1000);
                // Click save changes
                noteSaveChangesButton.click();
                Thread.sleep(1000);
            }
        }
    }

    public void deleteNote(String origTitle) throws InterruptedException {
        navNotes.click();
        Thread.sleep(1000);

        // Find note with origTitle
        for (WebElement row : tbodyRows) {
            WebElement th = row.findElement(By.tagName("th"));
            String thText = th.getAttribute("innerText");
            if (thText.equals(origTitle)) {
                // Click edit button
                WebElement buttons = row.findElement(By.id("row-buttons"));
                WebElement deleteButton = buttons.findElement(By.id("delete-note-button"));
                deleteButton.click();
                Thread.sleep(1000);
            }
        }




    }
}
