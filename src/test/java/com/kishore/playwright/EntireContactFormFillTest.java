package com.kishore.playwright;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.SelectOption;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@UsePlaywright(HeadlessChromeOptions.class)

public class EntireContactFormFillTest {

    @DisplayName("Interacting with text fields")
    @Nested
    class WhenInteractingWithTextFields {

        @BeforeEach
        void openContactPage(Page page) {
            page.navigate("https://practicesoftwaretesting.com/contact");
        }

        @DisplayName("Input fields test")
        @Test
        void fieldValues(Page page) {
            var firstNameField = page.getByLabel("First name");
            var lastNameField = page.getByLabel("Last name");
            var emailField = page.getByLabel("Email");
            var messageField = page.getByLabel("Message");
            var subjectField = page.getByLabel("Subject");

            firstNameField.fill("Sarah-Jane");
            lastNameField.fill("Jones");
            emailField.fill("Sarah@example.com");
            messageField.fill("Hello There");
            subjectField.selectOption(new SelectOption().setIndex(5));

            PlaywrightAssertions.assertThat(firstNameField).hasValue("Sarah-Jane");
            PlaywrightAssertions.assertThat(lastNameField).hasValue("Jones");
            PlaywrightAssertions.assertThat(emailField).hasValue("Sarah@example.com");
            PlaywrightAssertions.assertThat(messageField).hasValue("Hello There");
            PlaywrightAssertions.assertThat(subjectField).hasValue("warranty");


        }

        @DisplayName("Mandatory fields")
        @ParameterizedTest
        @ValueSource(strings = {"First name", "Last name", "Email", "Message"})
        void mandatoryFields(String fieldName, Page page) {
            var firstNameField = page.getByLabel("First name");
            var lastNameField = page.getByLabel("Last name");
            var emailNameField = page.getByLabel("Email");
            var messageField = page.getByLabel("Message");
            var subjectField = page.getByLabel("Subject");
            var sendButton = page.getByText("Send");

            // Fill in the field values
            firstNameField.fill("Sarah-Jane");
            lastNameField.fill("Smith");
            emailNameField.fill("sarah-jane@example.com");
            messageField.fill("Hello, world!");
            subjectField.selectOption("Warranty");

            // Clear one of the fields
            page.getByLabel(fieldName).clear();

            sendButton.click();

            // Check the error message for that field
            var errorMessage = page.getByRole(AriaRole.ALERT).getByText(fieldName + " is required");

            assertThat(errorMessage).isVisible();
        }

        @DisplayName("Text fields")
        @Test
        void textFieldValues(Page page) {
            var messageField = page.getByLabel("Message");

            messageField.fill("This is my message");

            assertThat(messageField).hasValue("This is my message");
        }

        @DisplayName("Dropdown lists")
        @Test
        void dropdownFieldValues(Page page) {
            var subjectField = page.getByLabel("Subject");

            subjectField.selectOption("Warranty");

            assertThat(subjectField).hasValue("warranty");
        }

    }
}
