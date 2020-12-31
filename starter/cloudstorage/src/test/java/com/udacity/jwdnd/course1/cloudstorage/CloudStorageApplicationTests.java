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
	private SignupPage signupPage;
	private LoginPage loginPage;
	private HomePage homePage;
	private ResultPage resultPage;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {

		this.driver = new ChromeDriver();
		signupPage = new SignupPage(driver);
		loginPage = new LoginPage(driver);
		homePage = new HomePage(driver);
		resultPage = new ResultPage(driver);
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}



	/*
	Verify that an unauthorized user can only access the login and signup pages
	 */
	@Test
	public void testUnauthUserAccess() {

		// Successfully reach the Login page
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());

		// Successfully reach the Sign Up page
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());

		// Redirected to the Login page
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());

		// Redirected to the Login page
		driver.get("http://localhost:" + this.port + "/result");
		Assertions.assertEquals("Login", driver.getTitle());

	}



	// Sign up a new user, log in, verify that the home page is accessible, log out,
	// and verify that the home page is no longer accessible
	@Test
	public void testAllUserAccess() {

		// Sign up Bob
		driver.get("http://localhost:" + port + "/signup");
		signupPage.signupNow("Robinson", "Crusoe", "Bob", "foobar");

		// Log in Bob
		driver.get("http://localhost:" + port + "/login");
		loginPage.loginNow("Bob", "foobar");

		// Verify Bob can get to the home page
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Home", driver.getTitle());

		// Log out Bob
		homePage.logoutNow();

		// Verify the home page redirects to the login page
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());

	}

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
