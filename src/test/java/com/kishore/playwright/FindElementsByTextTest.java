package com.kishore.playwright;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import org.junit.jupiter.api.*;

import java.util.Arrays;

public class FindElementsByTextTest {

    private static Playwright playwright;
    private static Browser browser;
    private static BrowserContext browserContext;
    Page page;

    @BeforeAll
    public static void setBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setArgs(Arrays.asList("--no-sandbox","--disable-extensions","--disable-gpu"))
        );
        browserContext = browser.newContext();
    }

    void openPage() {
        page.navigate("https://practicesoftwaretesting.com"); // Replace with your URL
    }


    @BeforeEach
    public void setUp() {
        page = browserContext.newPage();
    }

    @AfterAll
    public static void finish() {
        browser.close();
        playwright.close();
    }

    @DisplayName("Find elements by text")
    @Nested

    class FindElementsByText {

    @BeforeEach
        void openTheCatalogPage() {
        openPage();
    }

    @DisplayName("Locating elements by text contents")
        @Test
        void byText() {
        page.getByText("Bolt Cutters").click();
        PlaywrightAssertions.assertThat(page.getByText("MightyCraft Hardware")).isVisible();
    }

    @DisplayName("Using alt text")
        @Test
        void byAltText() {
        page.getByAltText("Combination Pliers").click();  // Alt text is used for finding images
        PlaywrightAssertions.assertThat(page.getByText("ForgeFlex Tools")).isVisible();
    }

    @DisplayName("Using Title")
        @Test
        void byTitle() {
        page.getByAltText("Combination Pliers").click();
        page.getByTitle("Practice Software Testing - Toolshop").click();
    }

    }
}
