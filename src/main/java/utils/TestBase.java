package utils;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.util.UUID;

public class TestBase {

    protected String generateRandomString() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    protected int generateRandomId() {
        return (int) (Math.random() * 1000) + 1;
    }

    @BeforeSuite
    public void setupSuite() {
        // Load configurations
        ConfigReader.loadConfig();

        // Initialize report configuration
        ReportManager.initializeReport();
    }

    @BeforeClass
    public void setupTest() {
        // Configure RestAssured
        RestAssured.baseURI = ConfigReader.getProperty("base.url");
        System.out.println("Setting baseURI to: " + RestAssured.baseURI);

        // Add logging filters
        RestAssured.filters(
                new RequestLoggingFilter(),
                new ResponseLoggingFilter(),
                new AllureRestAssured()
        );
    }
}