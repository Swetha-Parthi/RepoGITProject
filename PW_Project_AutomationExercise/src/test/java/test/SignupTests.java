package test;

import java.util.UUID;
import java.util.regex.Pattern;

import org.testng.annotations.Test;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;

import base.BaseTest;

public class SignupTests extends BaseTest {

	@Test(description = "AE01_TC01_Verify whether new user is able to signup")
	public void Test_AE01_TC01_Verify_NewUser_SignUp() {

		// Case:1 - Register new user

		System.out.println("Running, Case:1 - New user Signup");
		PlaywrightAssertions.assertThat(page.getByText(Pattern.compile("New User Signup!"))).isVisible();
		String userName = "CFTestUser" + System.currentTimeMillis();
		String email = userName + "@gmail.com";
		page.locator("//input[@name='name']").fill(userName);
		page.locator("//input[@data-qa='signup-email']").fill(email);
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Signup")).click();

		// Entering demographic details
		PlaywrightAssertions.assertThat(page.getByText(Pattern.compile("Enter Account Information"))).isVisible();
		page.getByLabel(Pattern.compile("^\\s*Mr.\\s*$")).click();
		pw.selectors().setTestIdAttribute("data-qa");
		String pwd = "CF@pwd" + UUID.randomUUID().toString().substring(0, 3);
		page.getByTestId("password").fill(pwd);
		page.getByTestId("days").selectOption("10");
		page.getByTestId("months").selectOption("March");
		page.getByTestId("years").selectOption("1996");
		page.getByText("Sign up for our newsletter!").click();
		page.getByLabel(Pattern.compile("Receive special offers from our partners!")).click();

		page.getByTestId("first_name").fill(userName);
		page.locator("#last_name").fill("01");
		page.getByTestId("company").fill("Cloudyfolk");
		page.getByTestId("address").fill("123 Vagai st");
		page.getByTestId("address2").fill("SK colony");
		page.getByTestId("country").selectOption("India");
		page.getByTestId("state").fill("Tamilnadu");
		page.getByTestId("city").fill("Coimbatore");
		page.getByTestId("zipcode").fill("600001");
		page.getByTestId("mobile_number").fill("7530012480");

		// Click Create Account Button and see the message Account created
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(Pattern.compile("Create Account"))).click();
		PlaywrightAssertions.assertThat(page.getByText(Pattern.compile("Account Created!"))).isVisible();
		page.getByText("Continue").click();

		// Verify User logged in
		PlaywrightAssertions.assertThat(page.getByText("Logged in as:" + userName));

		// Delete Account
		page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Delete Account")).click();
		PlaywrightAssertions
				.assertThat(
						page.getByRole(AriaRole.HEADING,
								new Page.GetByRoleOptions()
										.setName(Pattern.compile("Account Deleted!", Pattern.CASE_INSENSITIVE))))
				.isVisible();
		PlaywrightAssertions
				.assertThat(page.getByText(Pattern.compile("permanently deleted", Pattern.CASE_INSENSITIVE)))
				.isVisible();
		page.getByTestId("continue-button").click();
		
		
	}

	@Test(description = "AE01_TC05_Verify whether user getting error message using registered mail while Signup")
	public void Test_AE04__TC05_Signup_ExistingMail() {

		// Case:5 - Enter SignUp details with existing mail

		System.out.println("Running, Case:5 - Signup using already registered mail");
		PlaywrightAssertions.assertThat(page.getByText(Pattern.compile("New User Signup!"))).isVisible();
		pw.selectors().setTestIdAttribute("data-qa");
		page.getByTestId("signup-name").fill("AEtestCF06");
		page.getByTestId("signup-email").fill("AEtestCF02@gmail.com");
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Signup")).click();
		PlaywrightAssertions.assertThat(page.getByText("Email Address already exist!")).isVisible();
	}
}
