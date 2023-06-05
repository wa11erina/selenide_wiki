package com.github.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class SelenideWikiTest {

    @BeforeAll
    static void beforeAll() {
        Configuration.pageLoadStrategy = "eager";
        Configuration.browserSize = "1920x1080";
        Configuration.holdBrowserOpen = true;

    }

    @Test
    void selenideWikiPage() {
        // Open GitHub main page
        open("https://github.com/");
        // Type "selenide" into search field and press Enter
        $("[placeholder='Search GitHub']").setValue("selenide").pressEnter();
        // Choose first result from the results' list and click it so to navigate to the page
        $$("ul.repo-list li").first().$("a").click();
        // Check presence of "selenide / selenide" text in the header
        $("#repository-container-header").shouldHave(text("selenide / selenide"));

        // Click Wiki section
        $("#wiki-tab").click();
        // Check presence of "Welcome to the selenide wiki!" text in the header
        $("#wiki-body h1").shouldHave(text("Welcome to the selenide wiki!"));

        // Type "SoftAssertions" into pages' search field
        $("#wiki-pages-filter").setValue("SoftAssertions");
        // Check presence of "SoftAssertions" text in the search results
        $("[data-filterable-for=wiki-pages-filter]").shouldHave(text("SoftAssertions"));

        // Open SoftAssertions page
        $("a[href='/selenide/selenide/wiki/SoftAssertions']").click();
        // Check presence of JUnit5 test code on the page
        $("#wiki-content .markdown-body")
                .$$("div pre")
                .findBy(text("@ExtendWith({SoftAssertsExtension.class})"))
                .shouldHave(text("""
                        class Tests {
                          @Test
                          void test() {
                            Configuration.assertionMode = SOFT;
                            open("page.html");
                                               
                            $("#first").should(visible).click();
                            $("#second").should(visible).click();
                          }
                        }"""));

    }
}




