package com.kishore.playwright;

import com.microsoft.playwright.*;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@UsePlaywright(HeadlessChromeOptions.class)
public class AddingItemsToCartTest {

//    protected static Playwright playwright;
//    protected static Browser browser;
//    protected static BrowserContext browserContext;
//    Page page;

//    @BeforeAll
//    static void setUpBrowser() {
//        playwright = Playwright.create();
//        browser = playwright.chromium().launch(
//                new BrowserType.LaunchOptions().setHeadless(false)
//                        .setArgs(Arrays.asList("--no-sandbox", "--disable-extensions", "--disable-gpu"))
//        );
//        playwright.selectors().setTestIdAttribute("data-test");
//    }
//
//    @BeforeEach
//    void setUp() {
//        browserContext = browser.newContext();
//        page = browserContext.newPage();
//    }
//
//    @AfterEach
//    void closeContext() { browserContext.close(); }

//    @AfterAll
//    static void endAll() {
//        browser.close();
//        playwright.close();
//    }

    @DisplayName("Search for pliers")
    @Test
    void searchForPliers(Page page) {
        page.navigate("https://practicesoftwaretesting.com");
        page.getByPlaceholder("Search").fill("Pliers");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search")).click();

        assertThat(page.locator(".card")).hasCount(4);

        List<String> productNames =  page.getByTestId("product-name").allTextContents();
        Assertions.assertThat(productNames).allMatch(name -> name.contains("Pliers"));

        Locator outOfStockItem = page.locator(".card")
                .filter(new Locator.FilterOptions().setHasText("Out of Stock"))
                .getByTestId("product-name");

        assertThat(outOfStockItem).hasCount(1);
        assertThat(outOfStockItem).hasText("Long Nose Pliers");
    }

}
