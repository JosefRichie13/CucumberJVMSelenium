package cucumberjvmselenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

public class Driver {

    public void loadAUrl(String url){
        Hooks.driver.get(url);
    }

    public void typeText(String textToType, By element){
        Hooks.driver.findElement(element).sendKeys(textToType);
    }

    public void clickButton(By element){
        Hooks.driver.findElement(element).click();
    }

    // Checks if an element is present in the DOM or not
    // Returns FALSE if element is NOT visible, which means the size will be 0
    // Returns TRUE if element is visible, which means the size will be greater than 0
    public boolean elementVisibleOrNot(By element){
        int status = Hooks.driver.findElements(element).size();
        if(status == 0){
            return false;
        }
        else {
            return true;
        }
    }

    public String getTextFromElement(By element){
        return Hooks.driver.findElement(element).getText();
    }

    public String getSpecificTextFromAListOfElements(By element, int index){
        return Hooks.driver.findElements(element).get(index).getText();
    }

    public List<String> getAllTextFromAListOfElements(By element) {
        List<WebElement> elements = Hooks.driver.findElements(element);
        return elements.stream().map(WebElement::getText).collect(Collectors.toList());
    }


    public void selectFromDropdownUsingText(By element, String selectOptionInText){
        WebElement elementToSelect = Hooks.driver.findElement(element);
        Select select = new Select(elementToSelect);
        select.selectByVisibleText(selectOptionInText);
    }
}
