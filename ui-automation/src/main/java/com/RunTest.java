package com;

import com.Common.Config;
import com.Pages.Landing;
import com.Pages.OpenPosition;
import com.Pages.QaCarriers;
import com.codeborne.selenide.Browsers;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.junit.Assert;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Objects;

import static com.codeborne.selenide.Selenide.screenshot;
import static com.codeborne.selenide.Selenide.switchTo;
import static com.codeborne.selenide.WebDriverRunner.setWebDriver;

public class RunTest {

    public static void main(String[] args)
    {
        try
        {
            Config.getConfig().initialize();
            Landing landing = new Landing();
            QaCarriers qaCarriers = new QaCarriers();
            OpenPosition openPosition = new OpenPosition();

            Configuration.browser = "chrome";
            Configuration.browserSize="start-maximized";
            Configuration.reportsFolder=System.getProperty("user.dir")+"/test-result";

            //Selenium grid start
            if(!Config.getConfig().isDebug())
            {
                DesiredCapabilities desiredCapabilities=new DesiredCapabilities();
                RemoteWebDriver driver = null;
                URL url=new URL(Config.getConfig().getGridUrl());
                switch (Config.getConfig().getBrowser())
                {
                    case "firefox":
                        desiredCapabilities.setCapability(CapabilityType.BROWSER_NAME, Browsers.FIREFOX);
                        FirefoxOptions firefoxOptions=new FirefoxOptions();
                        firefoxOptions.merge(desiredCapabilities);
                        driver = new RemoteWebDriver(url, firefoxOptions);
                        break;
                    case "chrome":
                    default:
                        desiredCapabilities.setCapability(CapabilityType.BROWSER_NAME, Browsers.CHROME);
                        ChromeOptions chromeOptions=new ChromeOptions();
                        chromeOptions.merge(desiredCapabilities);
                        driver = new RemoteWebDriver(url, chromeOptions);
                        break;
                }
                setWebDriver(driver);
            }
            //Selenium grid end

            /* Test Automation 1. step*/
            landing.openLandingPage();

            /* Test Automation 2. step*/
            landing.goToQaCarrierPage();
            qaCarriers.clickJobButton();
            openPosition.checkAndFilter();

            /* Test Automation 3. step*/
            openPosition.checkJobs();

            /* Test Automation 4. step*/
            openPosition.clickApplyButton();
            switchTo().window(1);
            String leverUrl=WebDriverRunner.getWebDriver().getCurrentUrl();
            if(!Objects.requireNonNull(leverUrl).contains(Config.getConfig().getLevelUrl()))
            {
                Assert.fail("Lever Application is not open");
            }
        }
        catch (Exception e)
        {
            screenshot("test-failed-"+new Timestamp(System.currentTimeMillis()));
            throw new RuntimeException(e);
        }

    }
}
