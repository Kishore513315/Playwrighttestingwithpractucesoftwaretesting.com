package com.kishore.playwright;

import com.microsoft.playwright.*;
import com.microsoft.playwright.junit.UsePlaywright;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;


public class MyFirstPlaywrightTest {
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

    @BeforeEach
    public void setUp() {
        page = browserContext.newPage();
    }

    @AfterAll
    public static void finish() {
        browser.close();
        playwright.close();
    }



    @Test
    void myFirstTest() {
        page.navigate("https://practicesoftwaretesting.com");
        String title = page.title();

        Assertions.assertTrue(title.contains("Practice Software Testing"));
        }

        @Test
    void testSearchKeyword() {

        page.navigate("https://practicesoftwaretesting.com");
   page.locator("[placeholder=Search]").fill("Pliers");
   page.locator("button:has-text('Search')").click();

   int matchingSearchResults = page.locator(".card").count();
   Assertions.assertTrue(matchingSearchResults > 6);

    }
    }

