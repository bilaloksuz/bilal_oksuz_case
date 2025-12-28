package com.Pages;

import com.Common.BasePage;
import com.Common.Config;
import com.codeborne.selenide.*;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class Landing extends BasePage {

    private final SelenideElement logo = $(byClassName("header-logo"));
    private final SelenideElement cookies = $(byId("cookie-law-info-bar"));
    private final SelenideElement cookieReject = $(byId("wt-cli-reject-btn"));
    private final SelenideElement header = $(byClassName("header-top"));
    private final ElementsCollection menuItemList = $$(byClassName("header-menu-item"));
    private final ElementsCollection menuActionItemList = $$(byClassName("header-menu-action"));
    private final ElementsCollection footerMain = $$(byClassName("footer-main"));
    private final ElementsCollection footerMenuList = $$(byClassName("footer-links-col"));
    private SelenideElement footerMenuBody;
    private SelenideElement careerLinkButton;

    public Landing() {

    }

    public void openLandingPage() {
        navigateUrl(com.Common.Config.getConfig().getBaseUrl());
        isExits("Logo is not found. Landing page is not open.", logo);
        isExits("Header is not found. Landing page is not open.", header);

        if (cookies.exists()) {
            clickButton(cookieReject);
        }

        isExits("Menu item list not found. Landing page is not open.", menuItemList);
        isExits("Menu action item list not found. Landing page is not open.", menuActionItemList);
        isExits("Footer item is not found. Landing page is not open.", footerMain);
        isExits("Footer menu item is not found. Landing page is not open.", footerMenuList);

        goToCarrierPage();
    }

    public void goToQaCarrierPage() {
        navigateUrl(WebDriverRunner.url()+"/quality-assurance");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void goToCarrierPage() {
        SelenideElement companyElement = footerMenuList.stream().filter(x -> x.$(byClassName("footer-links-col-item-head")).getText().equals("COMPANY")).findFirst().orElse(null);
        if (companyElement != null) {
            footerMenuBody = companyElement.$(byClassName("footer-links-col-item-body"));
            careerLinkButton = footerMenuBody.$(byAttribute("data-text", Config.getConfig().getCareerButtonText()));
            clickButton(careerLinkButton);
        }
    }



    public static void main(String[] args) {
        Config.getConfig().initialize();
        Configuration.browser = "chrome";
        Landing landing = new Landing();

        landing.openLandingPage();
        landing.goToQaCarrierPage();
    }
}
