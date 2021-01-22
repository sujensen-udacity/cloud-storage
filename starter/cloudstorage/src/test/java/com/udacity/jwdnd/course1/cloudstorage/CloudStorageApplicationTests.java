package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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



	/*
	Sign up a new user, log in, verify that the home page is accessible, log out,
	and verify that the home page is no longer accessible
	 */
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

	/*
	Create a note, and verify it is displayed
	 */
	@Test
	public void testCreateNote() throws InterruptedException {

		// Sign up Bob
		driver.get("http://localhost:" + port + "/signup");
		signupPage.signupNow("Robinson", "Crusoe", "Bob", "foobar");

		// Log in Bob
		driver.get("http://localhost:" + port + "/login");
		loginPage.loginNow("Bob", "foobar");

		// Create a note
		driver.get("http://localhost:" + port + "/home");
		homePage.addNote("Shopping list", "Eggs milk cheese");

		// Click the success button to go back to home page
		resultPage.successClick();

		// Verify it is displayed
		List<NoteForm> notes = homePage.getNotes();
		Assertions.assertEquals(1, notes.size());
		Assertions.assertEquals("Shopping list", notes.get(0).getNoteTitle());
		Assertions.assertEquals("Eggs milk cheese", notes.get(0).getNoteText());

	}

	/*
	Edit an existing note, and verify that the changes are displayed
	 */
	@Test
	public void testEditNote() throws InterruptedException {

		// Sign up Bob
		driver.get("http://localhost:" + port + "/signup");
		signupPage.signupNow("Robinson", "Crusoe", "Bob", "foobar");

		// Log in Bob
		driver.get("http://localhost:" + port + "/login");
		loginPage.loginNow("Bob", "foobar");

		// Create a note
		driver.get("http://localhost:" + port + "/home");
		homePage.addNote("Shopping list", "Eggs milk cheese");

		// Click the success button to go back to home page
		resultPage.successClick();

		// Edit that note
		homePage.editNote("Shopping list", "New list", "Apples oranges bananas");

		// Click the success button to go back to home page
		resultPage.successClick();

		// Verify it is displayed
		List<NoteForm> notes = homePage.getNotes();
		Assertions.assertEquals(1, notes.size());
		Assertions.assertEquals("New list", notes.get(0).getNoteTitle());
		Assertions.assertEquals("Apples oranges bananas", notes.get(0).getNoteText());
	}

	/*
	Delete a note, and verify that the note is no longer displayed
	 */
	@Test
	public void testDeleteNote() throws InterruptedException {

		// Sign up Bob
		driver.get("http://localhost:" + port + "/signup");
		signupPage.signupNow("Robinson", "Crusoe", "Bob", "foobar");

		// Log in Bob
		driver.get("http://localhost:" + port + "/login");
		loginPage.loginNow("Bob", "foobar");

		// Create a note
		driver.get("http://localhost:" + port + "/home");
		homePage.addNote("Shopping list", "Eggs milk cheese");

		// Click the success button to go back to home page
		resultPage.successClick();

		// Verify there is one note
		List<NoteForm> notes = homePage.getNotes();
		Assertions.assertEquals(1, notes.size());

		// Delete that note
		homePage.deleteNote("Shopping list");

		// Click the success button to go back to home page
		resultPage.successClick();

		// Verify that there are no notes
		List<NoteForm> emptyNotes = homePage.getNotes();
		Assertions.assertEquals(0, emptyNotes.size());

	}

	// Create a set of credentials, verify they are displayed, and verify that the displayed
	// password is encrypted

	// View an existing set of credentials, verify that the viewable password is unencrypted,
	// edit the credentials, and verify that the changes are displayed.

	// Delete an existing set of credentials, and verify that the credentials are no longer
	// displayed.



}
