package pageFactoryClasses;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FlightsSearchResultsPage {
    WebDriver driver;

    // Page heading
    @FindBy(tagName = "h3")
    WebElement pageHeading;

    // Locating all "Choose This Flight" buttons
    @FindBy(xpath = "//table[@class='table']/tbody/tr/td[1]/input")
    List<WebElement> chooseFlightButtons;

    // Locating all rows in the flight results table
    @FindBy(xpath = "//table[@class='table']/tbody/tr")
    List<WebElement> flightRows;

    public FlightsSearchResultsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Method to verify if flights are displayed
    public boolean areFlightsDisplayed() {
        return flightRows.size() > 0;
    }

    // Method to fetch flight details for a given row
    public Map<String, String> getFlightDetails(int rowIndex) {
        Map<String, String> flightDetails = new LinkedHashMap<>();

        if (flightRows.size() >= rowIndex) {
            WebElement selectedRow = flightRows.get(rowIndex - 1); // Adjusting for 0-based index
            List<WebElement> rowCells = selectedRow.findElements(org.openqa.selenium.By.tagName("td"));

            flightDetails.put("Flight #", rowCells.get(1).getText());
            flightDetails.put("Airline", rowCells.get(2).getText());
            flightDetails.put("Departure Time", rowCells.get(3).getText());
            flightDetails.put("Arrival Time", rowCells.get(4).getText());
            flightDetails.put("Price", rowCells.get(5).getText());
        }
        return flightDetails;
    }

    // Method to select a flight dynamically by row index
    public void selectFlightByRow(int rowIndex) {
        if (flightRows.size() >= rowIndex) {
            WebElement selectedRow = flightRows.get(rowIndex - 1); // Adjusting for 0-based index
            List<WebElement> rowCells = selectedRow.findElements(org.openqa.selenium.By.tagName("td"));

            // Printing flight details dynamically
            System.out.println("Flight Details (Row " + rowIndex + "):");
            System.out.println("Flight #: " + rowCells.get(1).getText());
            System.out.println("Airline: " + rowCells.get(2).getText());
            System.out.println("Departure Time: " + rowCells.get(3).getText());
            System.out.println("Arrival Time: " + rowCells.get(4).getText());
            System.out.println("Price: " + rowCells.get(5).getText());

            // Clicking the "Choose This Flight" button dynamically
            chooseFlightButtons.get(rowIndex - 1).click();
            System.out.println("Flight in row " + rowIndex + " selected!");
        } else {
            System.out.println("Not enough flights available! Requested row: " + rowIndex);
        }
    }
}
