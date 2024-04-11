package cucumberjvmselenium;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en_old.Tha;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class Steps_ProductCheckout {

    Driver driverMethods = new Driver();
    Configs configs = new Configs();
    Selectors selectors = new Selectors();

    @And("I add {string} to the cart")
    public void addToCart(String productToAdd){
        addOrRemoveProductFromCart(productToAdd);
    }

    @And("I remove {string} from the cart")
    public void removeFromTheCart(String productToRemove){
        addOrRemoveProductFromCart(productToRemove);
    }

    public void addOrRemoveProductFromCart(String productType){
        switch (productType){
            case "Sauce Labs Backpack" -> driverMethods.clickButton(selectors.productBackpack);
            case "Sauce Labs Bike Light" -> driverMethods.clickButton(selectors.productBikelight);
            case "Sauce Labs Bolt T-Shirt" -> driverMethods.clickButton(selectors.productTshirt);
            case "Sauce Labs Fleece Jacket" -> driverMethods.clickButton(selectors.productJacket);
            case "Sauce Labs Onesie" -> driverMethods.clickButton(selectors.productOnesie);
            case "Test.allTheThings() T-Shirt (Red)" -> driverMethods.clickButton(selectors.productTshirtRed);
            default -> throw new IllegalArgumentException("Unavailable product : " + productType);
        }
    }

    @And("I click on the cart")
    public void clickOnTheCart(){
        driverMethods.clickButton(selectors.cart);
    }

    @And("I checkout")
    public void checkout(){
        driverMethods.clickButton(selectors.checkout);
    }

    @And("I enter my information to continue")
    public void enterMyInfoAndContinue(DataTable table){
        driverMethods.typeText(table.asMaps().get(0).get("FirstName"), selectors.firstName);
        driverMethods.typeText(table.asMaps().get(0).get("LastName"), selectors.lastName);
        driverMethods.typeText(table.asMaps().get(0).get("Zip"), selectors.zipCode);
        driverMethods.clickButton(selectors.continueButton);
    }

    @And("I confirm my order")
    public void confirmMyOrder(){
        driverMethods.clickButton(selectors.finishButton);
    }

    @Then("I should see {string} after the order is placed")
    public void seeMessageAfterOrderPlacement(String message){
        assertEquals(driverMethods.getTextFromElement(selectors.checkoutBanner), message);
    }

    /* We get the Tax shown in the UI, extract the number, convert it into float and store it in a variable, taxCalculatedByAPP
       We get the non taxed sum shown in the UI, extract the number, convert into float, multiple it by 0.08 (8%), round the result off to 2 and store it in a variable, taxCalculatedByCODE
       Then we check if both taxCalculatedByAPP and taxCalculatedByCODE are equal

       We get the total shown in the UI, extract the number, convert it into float and store it in a variable, totalCalculatedByAPP
       We get the non tax added total shown in the UI, extract the number, convert into float, add the tax calculated (taxCalculatedByCODE) and store it in a variable, totalCalculatedByCODE
       Then we check if both totalCalculatedByAPP and totalCalculatedByCODE are equal
    */
    @Then("I should see the tax calculated at 8 percent")
    public void verifyTheTaxCalculation(){
        float taxCalculatedByAPP = Float.parseFloat(driverMethods.getTextFromElement(selectors.taxCalculated).replaceAll("[^\\d.]", ""));
        float taxCalculatedByCODE = Math.round(Float.parseFloat(driverMethods.getTextFromElement(selectors.subtotal).replaceAll("[^\\d.]", "")) * 0.08 * 100f) / 100f;
        assertEquals(taxCalculatedByAPP, taxCalculatedByCODE, 0);

        float totalCalculatedByAPP = Float.parseFloat(driverMethods.getTextFromElement(selectors.fullTotal).replaceAll("[^\\d.]", ""));
        float totalCalculatedByCODE = Float.parseFloat(driverMethods.getTextFromElement(selectors.subtotal).replaceAll("[^\\d.]", "")) + taxCalculatedByAPP;
        assertEquals(totalCalculatedByAPP, totalCalculatedByCODE, 0);
    }

    /*
    We get the individual prices into an array, individualPrices
    We remove the $ sign, convert the array elements into float and sum it. Storing it in a variable, sumCalculatedByCODE
    We get the total displayed in the UI, extract the number, convert into float and store it in a variable, sumCalculatedByAPP
    Then we check if sumCalculatedByCODE and sumCalculatedByAPP are equal
    */
    @Then("I should see the individual items total correctly")
    public void verifyIndividualItemsTotal(){
        List<String> individualPrices = driverMethods.getAllTextFromAListOfElements(selectors.priceList);
        List<String> individualPricesInFloatWithoutSign = individualPrices.stream().map(s -> s.substring(1)).collect(Collectors.toList());
        float sumCalculatedByCODE = individualPricesInFloatWithoutSign.stream().map(Float::valueOf).reduce(0f, Float::sum);

        float sumCalculatedByAPP = Float.parseFloat(driverMethods.getTextFromElement(selectors.subtotal).replaceAll("[^\\d.]", ""));

        assertEquals(sumCalculatedByCODE, sumCalculatedByAPP, 0);
    }
}
