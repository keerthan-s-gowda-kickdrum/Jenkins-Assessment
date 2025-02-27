package testClasses;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pageFactoryClasses.FlightsSearchResultsPage;

public class FlightsSearchResultsTest {
    WebDriver driver;
    String baseUrl = "https://blazedemo.com/reserve.php";
    FlightsSearchResultsPage flightsSearchResultsPage;

    @Parameters({"browser"})  // Accept browser from TestNG XML or Jenkins
    @BeforeClass
    public void setUp(@Optional("chrome") String browser) {

        System.out.println("🔹 Browser Selected: " + browser);

        switch (browser.toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            default:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
        }
        driver.manage().window().maximize();
        driver.get(baseUrl); // Update the URL if needed
        flightsSearchResultsPage = new FlightsSearchResultsPage(driver);
    }

    @Test
    public void testSelectAnyFlight() {
        // Verify flights are displayed
        Assert.assertTrue(flightsSearchResultsPage.areFlightsDisplayed(), "❌ No flights found!");

        // Select the 4th flight dynamically
        flightsSearchResultsPage.selectFlightByRow(4);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

