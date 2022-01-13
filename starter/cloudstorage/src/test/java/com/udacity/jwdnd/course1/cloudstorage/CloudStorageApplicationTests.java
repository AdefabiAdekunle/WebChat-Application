package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
import java.time.Duration;
import java.util.List;

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

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password)  {
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submit-button")));
		WebElement buttonSignUp = driver.findElement(By.id("submit-button"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You've successfully signed up!"));
		//webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-link"))).click();
//		WebElement buttonSignUpRedirect = driver.findElement(By.id("button-redirect"));
//		buttonSignUpRedirect.click();
	}

	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submit-button")));
		WebElement loginButton = driver.findElement(By.id("submit-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a successfully sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	public void testRedirection()  {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");
		
		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() throws InterruptedException {
		// Create a test account
		doMockSignUp("URL","Test","UTF","123");
		doLogIn("UTF", "123");
		
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() throws InterruptedException {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));
	}

	@Test
	public void testSignUpAndLoginFlow() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait (driver, 3);
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		//Login to the account
		doLogIn("UT", "123");

		Assertions.assertEquals("http://localhost:" + this.port + "/home", driver.getCurrentUrl());

		HomePage homePage = new HomePage(driver);
		homePage.logoutHomePage();

		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());

	}

	@Test
	public void homePageRestriction() {
		WebDriverWait wait = new WebDriverWait (driver, 3);
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/home");

		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	@Test
	public void noteCreationAddDelete() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait (driver, 30);
		JavascriptExecutor jse =(JavascriptExecutor) driver;

		//SignUp for account
		doMockSignUp("URL","Test","UTL","123");
		//Login to the account
		doLogIn("UTL", "123");
		String noteId= "1";
		String noteTitle = "To-test";
		String noteDescription = "I want to use Selenium";

		//addNote
		WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
		jse.executeScript("arguments[0].click()", notesTab);
		wait.withTimeout(Duration.ofSeconds(30));
		WebElement newNote = driver.findElement(By.id("add-note"));
		wait.until(ExpectedConditions.elementToBeClickable(newNote)).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title"))).sendKeys(noteTitle);
		WebElement newNoteDescription = driver.findElement(By.id("note-description"));
		newNoteDescription.sendKeys(noteDescription);
		WebElement saveChanges = driver.findElement(By.id("save-changes"));
		wait.until(ExpectedConditions.elementToBeClickable(saveChanges)).click();

		//Check if the Note created was successfull
		Assertions.assertEquals("Result",driver.getTitle());

		//To check if the note created was successfull
		WebElement redirectToHome = driver.findElement(By.id("aResultSuccess"));
		redirectToHome.click();
		 notesTab = driver.findElement(By.id("nav-notes-tab"));
		jse.executeScript("arguments[0].click()", notesTab);
		String currentNoteTitle= wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title-text"))).getText();
		String currentNoteDescription = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description-text"))).getText();
		//Check if the note created was displayed
		Assertions.assertEquals(noteTitle,currentNoteTitle);
		Assertions.assertEquals(noteDescription,currentNoteDescription);

		//Logout the user
		HomePage homePage = new HomePage(driver);
		homePage.logoutHomePage();


		//Edit existing Note
		//Login to the account
		doLogIn("UTL", "123");
		notesTab = driver.findElement(By.id("nav-notes-tab"));
		jse.executeScript("arguments[0].click()", notesTab);
		wait.withTimeout(Duration.ofSeconds(30));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("btnEditNote"))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title"))).clear();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title"))).sendKeys("To-Test-Note");
		saveChanges = driver.findElement(By.id("save-changes"));
		wait.until(ExpectedConditions.elementToBeClickable(saveChanges)).click();
		redirectToHome = driver.findElement(By.id("aResultSuccess"));
		redirectToHome.click();
		notesTab = driver.findElement(By.id("nav-notes-tab"));
		jse.executeScript("arguments[0].click()", notesTab);
		currentNoteTitle= wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title-text"))).getText();
		//check if the note edited was successful edited and displayed
		Assertions.assertEquals("To-Test-Note",currentNoteTitle);


		//Delete Note
		wait.until(ExpectedConditions.elementToBeClickable(By.id("btnDeleteNote"))).click();
		redirectToHome = driver.findElement(By.id("aResultSuccess"));
		redirectToHome.click();
		notesTab = driver.findElement(By.id("nav-notes-tab"));
		jse.executeScript("arguments[0].click()", notesTab);
		List<WebElement> noteList = driver.findElements(By.id("note-title-text"));
		//Check if it note has been deleted
		Assertions.assertEquals(0,noteList.size());

	}

	@Test
	public void credentialCreationAddDelete() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait (driver, 30);
		JavascriptExecutor jse =(JavascriptExecutor) driver;

		//SignUp for account
		doMockSignUp("URL","Test","UTK","123");
		//Login to the account
		doLogIn("UTK", "123");

		String url = "www.w3Schools.com";
		String username = "Adekunle";
		String password = "Ikeoluwa_007";

		//addCredential
		WebElement credentialsTab = driver.findElement(By.id("nav-credentials-tab"));
		jse.executeScript("arguments[0].click()", credentialsTab);
		wait.withTimeout(Duration.ofSeconds(30));
		WebElement newCredential = driver.findElement(By.id("add-credential"));
		wait.until(ExpectedConditions.elementToBeClickable(newCredential)).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url"))).sendKeys(url);
		WebElement newCredentialUsername = driver.findElement(By.id("credential-username"));
		newCredentialUsername.sendKeys(username);
		WebElement newCredentialPassword = driver.findElement(By.id("credential-password"));
		newCredentialPassword.sendKeys(password);
		WebElement saveChangesCredential = driver.findElement(By.id("save-changes-credential"));
		wait.until(ExpectedConditions.elementToBeClickable(saveChangesCredential)).click();
		//Check if the Credential  created was successfully
		Assertions.assertEquals("Result",driver.getTitle());


		//To check if the credential created  was successfully displayed
		WebElement redirectToHome = driver.findElement(By.id("aResultSuccess"));
		redirectToHome.click();
		credentialsTab = driver.findElement(By.id("nav-credentials-tab"));
		jse.executeScript("arguments[0].click()", credentialsTab);
		String currentDescriptionUrl= wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url-text"))).getText();
		String currentNoteDescriptionUsername = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username-text"))).getText();
		String currentNoteDescriptionEncryptedPassword = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password-text"))).getText();
		//Check if the note created was displayed
		Assertions.assertEquals(url,currentDescriptionUrl);
		Assertions.assertEquals(username,currentNoteDescriptionUsername);
		Assertions.assertNotEquals(password,currentNoteDescriptionEncryptedPassword);

		//Logout the user
		HomePage homePage = new HomePage(driver);
		homePage.logoutHomePage();

		//Edit existing Note
		//Login to the account
		doLogIn("UTK", "123");
		credentialsTab = driver.findElement(By.id("nav-credentials-tab"));
		jse.executeScript("arguments[0].click()", credentialsTab);
		wait.withTimeout(Duration.ofSeconds(30));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("btnEditCredential"))).click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url"))).clear();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url"))).sendKeys("www.adekunle.com");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-username"))).clear();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-username"))).sendKeys("Adebayo");
		saveChangesCredential = driver.findElement(By.id("save-changes-credential"));
		wait.until(ExpectedConditions.elementToBeClickable(saveChangesCredential)).click();
		redirectToHome = driver.findElement(By.id("aResultSuccess"));
		redirectToHome.click();
		credentialsTab = driver.findElement(By.id("nav-credentials-tab"));
		jse.executeScript("arguments[0].click()", credentialsTab);
		currentDescriptionUrl= wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url-text"))).getText();
		 currentNoteDescriptionUsername = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username-text"))).getText();
		currentNoteDescriptionEncryptedPassword = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password-text"))).getText();

		//check if the note edited was successful edited and displayed
		Assertions.assertEquals("www.adekunle.com",currentDescriptionUrl);
		Assertions.assertEquals("Adebayo",currentNoteDescriptionUsername);


		//Delete Credential
		wait.until(ExpectedConditions.elementToBeClickable(By.id("btnCredentialDelete"))).click();
		redirectToHome = driver.findElement(By.id("aResultSuccess"));
		redirectToHome.click();
		credentialsTab = driver.findElement(By.id("nav-credentials-tab"));
		jse.executeScript("arguments[0].click()", credentialsTab);
		Thread.sleep(2000);
		List<WebElement> credentialList = driver.findElements(By.id("credential-url-text"));
		//Check if it note has been deleted
		Assertions.assertEquals(0,credentialList.size());


	}



     @Test
	public void fileUploadAndDownload() throws InterruptedException {
// Create a test account
		 doMockSignUp("Large File","Test","LFK","123");
		 doLogIn("LFK", "123");

		 // Try to upload an arbitrary large file
		 WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		 String fileName = "barca1.jpg";

		 webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		 WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		 fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		 WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		 uploadButton.click();
		 try {
			 webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("aResultSuccess")));
		 } catch (org.openqa.selenium.TimeoutException e) {
			 System.out.println("Large File upload failed");
		 }

		 WebElement redirectToHome = driver.findElement(By.id("aResultSuccess"));
		 redirectToHome.click();
		 Thread.sleep(2000);
		String uploadedFileName= webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileName"))).getText();

		Assertions.assertEquals(fileName,uploadedFileName);
	}


}
