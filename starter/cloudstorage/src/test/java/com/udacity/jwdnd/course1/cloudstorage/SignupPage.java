package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

    @FindBy(id = "inputFirstName")
    private WebElement firstName;

    @FindBy(id = "inputLastName")
    private WebElement lastName;

    @FindBy(id = "inputUsername")
    private WebElement username;

    @FindBy(id = "inputPassword")
    private WebElement password;

    @FindBy(id = "submit-button")
    private WebElement button;

    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void signupNow(String fn, String ln, String un, String pw) {
        firstName.sendKeys(fn);
        lastName.sendKeys(ln);
        username.sendKeys(un);
        password.sendKeys(pw);
        button.click();
    }
}
