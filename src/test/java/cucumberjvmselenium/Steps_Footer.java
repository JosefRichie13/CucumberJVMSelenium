package cucumberjvmselenium;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en_old.Tha;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class Steps_Footer {

    Driver driverMethods = new Driver();
    Configs configs = new Configs();
    Selectors selectors = new Selectors();

    @And("I confirm that the footer is {string}")
    public void verifyFooterStatus(String visibleStatus){
        if(visibleStatus.equals("not visible")){
            assertFalse(driverMethods.elementVisibleOrNot(selectors.footer));
        }
        else {
            assertTrue(driverMethods.elementVisibleOrNot(selectors.footer));
        }
    }

    @And("I click on the {string} icon in the footer")
    public void clickOnTheFooterIcons(String footerIcon) throws InterruptedException {
        switch (footerIcon){
            case "Twitter" -> driverMethods.clickButton(selectors.footerTwitter);
            case "Facebook" -> driverMethods.clickButton(selectors.footerFacebook);
            case "LinkedIn" -> driverMethods.clickButton(selectors.footerLinkedin);
            default -> throw new IllegalArgumentException("Incorrect Footer icon : " + footerIcon);
        }
        Thread.sleep(3000);
    }

    @Then("I should see the {string} page opened with the URL as {string}")
    public void checkFooterRedirectURL(String redirectPage, String redirectURL){
        switch (redirectPage){
            case "Twitter" -> {
                driverMethods.switchBetweenTabs(1);
                assertEquals(driverMethods.getTheCurrentURL(), redirectURL);
                driverMethods.switchBetweenTabs(0);
            }
            case "Facebook" -> {
                driverMethods.switchBetweenTabs(2);
                assertEquals(driverMethods.getTheCurrentURL(), redirectURL);
                driverMethods.switchBetweenTabs(0);
            }
            case "LinkedIn" -> {
                driverMethods.switchBetweenTabs(3);
                assertEquals(driverMethods.getTheCurrentURL(), redirectURL);
                driverMethods.switchBetweenTabs(0);
            }
            default -> throw new IllegalArgumentException("Incorrect Footer icon : " + redirectPage);
        }
    }

}
