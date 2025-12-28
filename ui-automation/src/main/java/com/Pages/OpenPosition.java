package com.Pages;

import com.Common.BasePage;
import com.Common.Config;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byClassName;
import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class OpenPosition extends BasePage
{
    public SelenideElement departmentFilter=$(byId("filter-by-department"));
    public SelenideElement locationFilter=$(byId("filter-by-location"));
    public ElementsCollection cities;
    public SelenideElement myLocation;
    public ElementsCollection positionTitles=$$(byClassName("position-title"));
    public ElementsCollection positionDepartments=$$(byClassName("position-department"));
    public ElementsCollection positionLocations=$$(byClassName("position-location"));
    public ElementsCollection positionLists=$$(byClassName("position-list-item"));
    public ElementsCollection viewRoleButtons=$$(byClassName("btn-navy"));

    public OpenPosition()
    {

    }

    public void checkAndFilter()
    {
        isExits("Department Filter is not visible.",departmentFilter);
        isExits("Location Filter is not visible.",locationFilter);
        String departmentValue=departmentFilter.getText().replace("×\n","");
        assertValue(departmentValue, Config.getConfig().getQaJobButtonText());
        clickButton(locationFilter);
        cities=$$(byClassName("results__option"));
        for (int i = 0; i <cities.size() ; i++) {
            if(cities.get(i).getText().equals(Config.getConfig().getDefaultLocation()))
            {
                myLocation=cities.get(i);
                break;
            }
        }
        isExits(Config.getConfig().getDefaultLocation()+" is not visible.",myLocation);
        clickButton(myLocation);
    }

    public void checkJobs() throws InterruptedException {
        Thread.sleep(5000);

        for (int i = 0; i <positionTitles.size() ; i++)
        {
            SelenideElement position=waitElementForVisible(positionLists.get(i));
            if(!position.getText().contains(Config.getConfig().getQaJobTitle()))
            {
                fail("Position title is not match.");
            }
        }

        for (int i = 0; i <positionDepartments.size() ; i++)
        {
            SelenideElement position=waitElementForVisible(positionDepartments.get(i));
            if(!position.getText().contains(Config.getConfig().getQaJobTitle()))
            {
                fail("Position department is not match.");
            }
        }

        for (int i = 0; i <positionLocations.size() ; i++)
        {
            SelenideElement position=waitElementForVisible(positionLocations.get(i));
            if(!position.getText().contains(Config.getConfig().getDefaultLocation()))
            {
                fail("Position location is not match.");
            }
        }
    }

    public void clickApplyButton()
    {
        for (int i = 0; i <viewRoleButtons.size() ; i++)
        {
            SelenideElement button=waitElementForVisible(viewRoleButtons.get(i).hover());
            if(button.getText().equals("View Role"))
            {
                clickButton(button);
                break;
            }
        }
    }

}
