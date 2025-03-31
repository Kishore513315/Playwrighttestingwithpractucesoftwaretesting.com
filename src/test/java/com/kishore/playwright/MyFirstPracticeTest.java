package com.kishore.playwright;


import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.SelectOption;
import org.assertj.core.util.Strings;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class MyFirstPracticeTest {

    private static Playwright playwright;
    private static Browser browser;
    private static BrowserContext browserContext;
    Page page;

    @BeforeAll
    public static void start() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setArgs(Arrays.asList("--no-sandbox", "--disable-gpu"))
        );
        browserContext = browser.newContext();
    }

    @BeforeEach
    public void setUp() {
        page = browserContext.newPage();
    }

    @AfterAll
    public static void theEnd() {
        browser.close();
        playwright.close();
    }

    @Test
    void pageTitle() {                                                          // Page Title Test//
        page.navigate("https://practicesoftwaretesting.com");
        String title = page.title();
        Assertions.assertTrue(title.contains("Practice Software Testing"));
    }

    @Test
    void searchField() {                                                      //Search results test with pliers//
        page.navigate("https://practicesoftwaretesting.com");
        page.locator("[placeholder=Search]").fill("Pliers");
        page.locator("button:has-text('Search')").click();

        int pliersValue = page.locator(".card").count();
        Assertions.assertTrue(pliersValue > 4);
    }

    @Test
    void txtElements() {                                                            //Test using text//
        page.navigate("https://practicesoftwaretesting.com");
        page.getByText("Slip Joint Pliers").click();
        PlaywrightAssertions.assertThat(page.getByText("MightyCraft Hardware")).isVisible();
    }

    @Test
    void altElements() {                                                       // Alt text is used for images//
        page.navigate("https://practicesoftwaretesting.com");
        page.getByAltText("Long Nose Pliers").click();
        PlaywrightAssertions.assertThat(page.getByText("MightyCraft Hardware")).isVisible();
    }


@DisplayName("Find and Fill Fields in Contact Page")
    @Nested
    class fillContactForm {                                                  // Contact Form fields filling and testing//


        @DisplayName("Input all fields")
    @Test
        void fillFields() {
            page.navigate("https://practicesoftwaretesting.com/contact");
            var firstName = page.getByLabel("First Name");
            var lastName = page.getByLabel("Last Name");
            var email = page.getByLabel("Email Address");
            var message = page.getByLabel("Message");
            var subject = page.getByLabel("Subject");

            firstName.fill("Kishore");
            lastName.fill("Chigurupati");
            email.fill("qwerty@example.com");
            message.fill("Hello There");
            subject.selectOption(new SelectOption().setIndex(5));

            assertThat(firstName).hasValue("Kishore");
            assertThat(lastName).hasValue("Chigurupati");
            assertThat(email).hasValue("qwerty@example.com");
            assertThat(message).hasValue("Hello There");
            assertThat(subject).hasValue("warranty");
        }

        @DisplayName("Mandatory Fields Test")
    @ParameterizedTest
    @ValueSource(strings = {"First Name", "Last Name", "Email", "Message"})
    void mandatoryFields(String fieldName) {                                    // Mandatory Fields Check//
            page.navigate("https://practicesoftwaretesting.com/contact");
            var firstName = page.getByLabel("First Name");
            var lastName = page.getByLabel("Last Name");
            var email = page.getByLabel("Email Address");
            var message = page.getByLabel("Message");
            var subject = page.getByLabel("Subject");
            var sendButton = page.getByText("Send");

            firstName.fill("Kishore");
            lastName.fill("Chigurupati");
            email.fill("qwerty@example.com");
            message.fill("Hello There");
            subject.selectOption(new SelectOption().setIndex(5));

            assertThat(firstName).hasValue("Kishore");
            assertThat(lastName).hasValue("Chigurupati");
            assertThat(email).hasValue("qwerty@example.com");
            assertThat(message).hasValue("Hello There");
            assertThat(subject).hasValue("warranty");

            // Clear one of the fields//
            page.getByLabel(fieldName).clear();
            sendButton.click();

            // Check the error message for that field//
            var errorMessage = page.getByRole(AriaRole.ALERT).getByText(fieldName + " is required");

            assertThat(errorMessage).isVisible();

        }

}




}
