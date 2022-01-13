package com.example.WebChatApplication;

import com.example.WebChatApplication.model.ChatMessage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WebChatApplicationTests {
	@LocalServerPort
	public int port;

	public static WebDriver driver;

	public String baseURL;


	@BeforeAll
	public static void beforeAll() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();

	}

	@AfterAll
	public static void afterAll() {
		driver.quit();
		driver = null;
	}

	@BeforeEach
	public void beforeEach() {
		baseURL = "http://localhost:" + port;
	}

	@Test
	public void testUserSignupLoginAndSubmitMessage() throws InterruptedException {
		String username = "pzastoup";
		String password = "whatabadpassword";
		String messageText = "Hello!";

		String username2 = "adekunle";
		String password2 = "Ikeoluwa_007";
		String messageText2 = "This is Adekunle";


		driver.get(baseURL + "/signup");

		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("Peter", "Zastoupil", username, password);

		driver.get(baseURL + "/login");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		ChatPage chatPage = new ChatPage(driver);
		chatPage.sendChatMessage(messageText);

		ChatMessage sentMessage = chatPage.getFirstMessage();

		//For second User
		chatPage.setLogoutButton();
		driver.get(baseURL + "/signup");

		SignupPage signupPage2 = new SignupPage(driver);
		signupPage2.signup("Adekunle", "Adefabi", username2, password2);

		driver.get(baseURL + "/login");

		LoginPage loginPage2 = new LoginPage(driver);
		loginPage2.login(username2, password2);

		ChatPage2 chatPage2 = new ChatPage2(driver);
		chatPage2.sendChatMessage(messageText2);
		ChatMessage sentMessage2 = chatPage2.getFirstMessage();
		Thread.sleep(5000);


        //For Single User
		assertEquals(username, sentMessage.getUsername());
		assertEquals(messageText, sentMessage.getMessageText());
		//For Double user
		assertEquals(username2,sentMessage2.getUsername());
		assertEquals(messageText2,sentMessage2.getMessageText());
	}

}
