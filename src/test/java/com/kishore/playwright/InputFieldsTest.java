package com.kishore.playwright;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.junit.UsePlaywright;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@UsePlaywright(HeadlessChromeOptions.class)
public class InputFieldsTest {

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
            firstNameField.fill("Sarah-Jane");
            PlaywrightAssertions.assertThat(firstNameField).hasValue("Sarah-Jane");
        }

    }
}
