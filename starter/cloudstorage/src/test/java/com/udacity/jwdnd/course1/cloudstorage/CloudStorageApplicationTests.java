package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	// Verify that an unauthorized user can only access the login and signup pages

	// Sign up a new user, log in, verify that the home page is accessible, log out,
	// and verify that the home page is no longer accessible

	// Create a note, and verify it is displayed

	// Edit an existing note, and verify that the changes are displayed

	// Delete a note, and verify that the note is no longer displayed

	// Create a set of credentials, verify they are displayed, and verify that the displayed
	// password is encrypted

	// View an existing set of credentials, verify that the viewable password is unencrypted,
	// edit the credentials, and verify that the changes are displayed.

	// Delete an existing set of credentials, and verify that the credentials are no longer
	// displayed.



}
