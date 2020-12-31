package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    // Home page buttons
    @FindBy(id = "logout-button")
    private WebElement logoutButton;

    // Note tab buttons
    @FindBy(id = "add-note-button")
    private WebElement addNoteButton;
    @FindBy(id = "edit-note-button")
    private WebElement editNoteButton;
    @FindBy(id = "delete-note-button")
    private WebElement deleteNoteButton;

    // Note tab modal buttons
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

    // Credentials tab buttons
    @FindBy(id = "add-cred-button")
    private WebElement addCredbutton;
    @FindBy(id = "edit-cred-button")
    private WebElement editCredButton;
    @FindBy(id = "delete-cred-button")
    private WebElement deleteCredButton;

    // Credential tab modal buttons
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

    public void logoutAlready() {
        logoutButton.click();
    }

}
