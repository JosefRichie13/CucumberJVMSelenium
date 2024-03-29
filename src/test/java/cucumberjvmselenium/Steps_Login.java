package cucumberjvmselenium;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.Assert.*;

public class Steps_Login {

    Driver driver = new Driver();
    Configs configs = new Configs();
    Selectors selectors = new Selectors();

    @Given("I open the web page")
    public void openTheWebPage(){
        driver.loadAUrl(configs.mainURL);
    }

    @When("I login as a {string} user")
    public void loginAsAUser(String userType){
        switch (userType){
            case "standard" -> {
                driver.typeText(configs.validUser, selectors.userName);
                driver.typeText(configs.password, selectors.password);
            }
            default -> System.out.println("Incorrect User Type");
        }
        driver.clickButton(selectors.loginButton);
    }

    @Then("I should see {string} in the {string}")
    public void shouldSeeMessageInPage(String message, String page){
        switch (page){
            case "homepage" -> {
                assertEquals(driver.getTextFromElement(selectors.homepageTitle), message);
                assertFalse(driver.elementVisibleOrNot(selectors.loginButton));
            }
            case "loginpage" -> {


            }
            default -> System.out.println("Incorrect Page");
        }
    }

}
