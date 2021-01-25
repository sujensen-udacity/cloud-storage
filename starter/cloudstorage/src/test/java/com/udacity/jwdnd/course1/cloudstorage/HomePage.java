package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.CredForm;
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
    @FindBy(id = "note-tbody-row")
    private List<WebElement> noteTbodyRows;


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
    @FindBy(id = "nav-credentials-tab")
    private WebElement navCreds;
    @FindBy(id = "add-cred-button")
    private WebElement addCredButton;
    @FindBy(id = "edit-cred-button")
    private WebElement editCredButton;
    @FindBy(id = "delete-cred-button")
    private WebElement deleteCredButton;
    @FindBy(id = "cred-tbody-row")
    private List<WebElement> credTbodyRows;

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

        for (WebElement row : noteTbodyRows) {
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
        for (WebElement row : noteTbodyRows) {
            WebElement th = row.findElement(By.tagName("th"));
            String thText = th.getAttribute("innerText");
            if (thText.equals(origTitle)) {
                // Click edit button
                WebElement buttons = row.findElement(By.id("note-row-buttons"));
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
        for (WebElement row : noteTbodyRows) {
            WebElement th = row.findElement(By.tagName("th"));
            String thText = th.getAttribute("innerText");
            if (thText.equals(origTitle)) {
                // Click edit button
                WebElement buttons = row.findElement(By.id("note-row-buttons"));
                WebElement deleteButton = buttons.findElement(By.id("delete-note-button"));
                deleteButton.click();
                Thread.sleep(1000);
            }
        }




    }

    public void addCred(String url, String username, String password) throws InterruptedException {

        navCreds.click();
        Thread.sleep(1000);

        addCredButton.click();
        Thread.sleep(1000);

        credUrl.sendKeys(url);
        credUsername.sendKeys(username);
        credPassword.sendKeys(password);
        credSaveChangesButton.click();
        Thread.sleep(1000);
    }

    public List<CredForm> getCreds() throws InterruptedException {

        List<CredForm> retList = new ArrayList<>();
        navCreds.click();
        Thread.sleep(1000);

        for (WebElement row : credTbodyRows) {
            WebElement th = row.findElement(By.tagName("th"));
            String thTextUrl = th.getAttribute("innerText");

            WebElement td1 = row.findElement(By.id("cred-un"));
            String tdTextUsername = td1.getAttribute("innerText");

            WebElement td2 = row.findElement(By.id("cred-pw"));
            String tdTextPassword = td2.getAttribute("innerText");
            retList.add(new CredForm(null, thTextUrl, tdTextUsername, null, tdTextPassword, null));
        }
        return retList;
    }

    public String viewCredPassword(String origUrl) throws InterruptedException {
        navCreds.click();
        Thread.sleep(1000);

        String retString = null;
        // Find cred with origUrl
        for (WebElement row : credTbodyRows) {
            WebElement th = row.findElement(By.tagName("th"));
            String thTextUrl = th.getAttribute("innerText");
            if (thTextUrl.equals(origUrl)) {
                // Click edit button
                WebElement buttons = row.findElement(By.id("cred-row-buttons"));
                WebElement editButton = buttons.findElement(By.id("edit-cred-button"));
                editButton.click();
                Thread.sleep(1000);
                // getAttribute("value") for the existing value in the form WebElement
                retString = credPassword.getAttribute("value");
                credCloseButton.click();
                Thread.sleep(1000);
            }
        }
        return retString;
    }

    public void editCred(String origUrl, String newUrl, String newUsername, String newPassword) throws InterruptedException {
        navCreds.click();
        Thread.sleep(1000);

        // Find cred with origUrl
        for (WebElement row : credTbodyRows) {
            WebElement th = row.findElement(By.tagName("th"));
            String thTextUrl = th.getAttribute("innerText");
            if (thTextUrl.equals(origUrl)) {
                // Click edit button
                WebElement buttons = row.findElement(By.id("cred-row-buttons"));
                WebElement editButton = buttons.findElement(By.id("edit-cred-button"));
                editButton.click();
                Thread.sleep(1000);
                // New url
                credUrl.clear();
                credUrl.sendKeys(newUrl);
                Thread.sleep(1000);
                // New username
                credUsername.clear();
                credUsername.sendKeys(newUsername);
                Thread.sleep(1000);
                // New password
                credPassword.clear();
                credPassword.sendKeys(newPassword);
                Thread.sleep(1000);
                // Click save changes
                credSaveChangesButton.click();
                Thread.sleep(1000);
            }
        }
    }

    public void deleteCred(String origUrl) throws InterruptedException {
        navCreds.click();
        Thread.sleep(1000);

        // Find cred with origUrl
        for (WebElement row : credTbodyRows) {
            WebElement th = row.findElement(By.tagName("th"));
            String thTextUrl = th.getAttribute("innerText");
            if (thTextUrl.equals(origUrl)) {
                // Click edit button
                WebElement buttons = row.findElement(By.id("cred-row-buttons"));
                WebElement deleteButton = buttons.findElement(By.id("delete-cred-button"));
                deleteButton.click();
                Thread.sleep(1000);
            }
        }
    }
}
