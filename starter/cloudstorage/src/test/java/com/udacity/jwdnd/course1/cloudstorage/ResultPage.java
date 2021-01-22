package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ResultPage {

    // Note tab buttons
    @FindBy(id = "result-success-link")
    private WebElement resultSuccessLink;
    @FindBy(id = "result-error-link")
    private WebElement resultErrorLink;

    public ResultPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void successClick() throws InterruptedException {
        resultSuccessLink.click();
        Thread.sleep(1000);
    }

    public void errorClick() throws InterruptedException {
        resultErrorLink.click();
        Thread.sleep(1000);
    }

}
