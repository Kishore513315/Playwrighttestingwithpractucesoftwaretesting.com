package com.kishore.playwright;

import com.microsoft.playwright.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

import java.util.Arrays;

public class MyPracticeFindByElementTest {
    private static Playwright playwright;
    private  static Browser browser;
    private  static BrowserContext browserContext;
    Page page;

    @BeforeAll
    public static void startSetUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setArgs(Arrays.asList("--no-sandbox","--disable-gpu", "--disable-extensions"))
        );
        browserContext = browser.newContext();
    }

    @BeforeEach
    public void context() {
        page = browserContext.newPage();
    }

    @AfterAll
    public static void theEnd() {
        browser.close();
        playwright.close();
    }

    @DisplayName("Find Elements By Text")
    @Test
    void searchByText() {
        page.navigate("https://practicesoftwaretesting.com");
        page.getByText("Slip Joint Pliers").click();
        Assertions.assertThat(page.getByText("MightyCraft Hardware"));
    }




}
