package base;

import java.util.regex.Pattern;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;

public class BaseTest {

	protected Playwright pw;
	protected Browser browser;
	protected Page page;
	String baseUrl = "https://automationexercise.com/";

	@BeforeMethod(description = "Browser and URL Launch")
	public void setup() {
		pw = Playwright.create();
		browser = pw.chromium()
				.launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false).setSlowMo(1000));
		page = browser.newPage();
		System.out.println("Browser Launched successfully");
		page.navigate(baseUrl);
		System.out.println("Navigated to URL successfully");

		// Check whether URL launched successfully
		PlaywrightAssertions.assertThat(page).hasURL(Pattern.compile("automationexercise"));

		// Click on SignUp/Login link
		page.getByRole(AriaRole.LINK,
				new Page.GetByRoleOptions().setName(Pattern.compile("Login", Pattern.CASE_INSENSITIVE))).click();
	}

	@AfterMethod(description = "Browser and PLaywright close")
	public void teardown() {
		if (browser != null)
			browser.close();
		if (pw != null)
			pw.close();
	}
}

