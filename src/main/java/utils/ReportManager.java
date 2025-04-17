package utils;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class ReportManager {

    public static void initializeReport() {
        // Configure any report initialization if needed
    }

    @Step("API Response: {0}")
    public static void logResponse(Response response) {
        String responseBody = response.getBody().asString();
        Allure.addAttachment("Response", "application/json",
                new ByteArrayInputStream(responseBody.getBytes(StandardCharsets.UTF_8)), ".json");
        Allure.addAttachment("Status Code", String.valueOf(response.getStatusCode()));
        Allure.addAttachment("Headers", response.getHeaders().toString());
    }

    @Step("API Request: {0}")
    public static void logRequest(String request) {
        Allure.addAttachment("Request Body", "application/json",
                new ByteArrayInputStream(request.getBytes(StandardCharsets.UTF_8)), ".json");
    }
}