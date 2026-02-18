package test;

import java.util.regex.Pattern;

import org.testng.annotations.Test;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;

import base.BaseTest;

public class LoginTests extends BaseTest {

	@Test(description="AE01_TC02_Verify whether user be able to Login using correct details")
	public void Test_AE01_TC2_Verify_Login_CorrectDetails() {

		// Case:2 - Entering correct Login Details

		System.out.println("Running, Case:2 - Entering correct Login Details");
		page.getByRole(AriaRole.LINK,
				new Page.GetByRoleOptions().setName(Pattern.compile("Login", Pattern.CASE_INSENSITIVE))).click();
		PlaywrightAssertions.assertThat(page.getByText("Login to your account")).isVisible();

		// Enter User Details		
		pw.selectors().setTestIdAttribute("data-qa");
		page.getByTestId("login-email").fill("CFTestUser1770977658133@gmail.com");
		page.getByTestId("login-password").fill("CF@pwd0f7");
		page.getByTestId("login-button").click();

		// Verify User logged in correctly
		String username = "CFTestUser1770977658133";
		PlaywrightAssertions.assertThat(page.getByText(Pattern.compile("Logged in as" + username)));

		// Delete Account
		page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(Pattern.compile("Delete Account"))).click();
		PlaywrightAssertions
				.assertThat(
						page.getByRole(AriaRole.HEADING,
								new Page.GetByRoleOptions()
										.setName(Pattern.compile("Account Deleted", Pattern.CASE_INSENSITIVE))))
				.isVisible();
	}

	@Test(description="AE01_TC03_Verify whether user getting message with incorrect login details")
	public void Test_AE01_TC3_Verify_Login_Incorrect_MailPwd() {

		// Case:3 - Entering incorrect Login Details

		System.out.println("Running, Case:3 - Entering Incorrect Login Details(Mail and Password)");
		page.getByRole(AriaRole.LINK,
				new Page.GetByRoleOptions().setName(Pattern.compile("Login", Pattern.CASE_INSENSITIVE))).click();
		PlaywrightAssertions.assertThat(page.getByText("Login to your account")).isVisible();

		// Enter Incorrect Details
		pw.selectors().setTestIdAttribute("data-qa");
		page.getByTestId("login-email").fill("AEtest@gmail.com");
		page.getByTestId("login-password").fill("AEtestCF");
		page.getByTestId("login-button").click();
		PlaywrightAssertions.assertThat(page.getByText("Your email or password is incorrect!")).isVisible();
	}

	@Test(description="AE01_TC04_Verify whether user be able to logout")
	public void Test_AE01_TC04_Verify_LogoutUser() {

		// Case:4 - Logout User

		System.out.println("Running, Case:4 - Verify user is able to logout");
		String userName = "AEtestCF02";
		pw.selectors().setTestIdAttribute("data-qa");
		page.getByTestId("login-email").fill("AEtestCF02@gmail.com");
		page.getByTestId("login-password").fill("AEtestCF02");
		page.getByTestId("login-button").click();
		PlaywrightAssertions.assertThat(page.getByText("'Logged in as" + userName));

		page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(Pattern.compile("Logout"))).click();
		PlaywrightAssertions.assertThat(page).hasURL(Pattern.compile("login", Pattern.CASE_INSENSITIVE));
	}

}
