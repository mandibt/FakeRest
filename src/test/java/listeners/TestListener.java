package listeners;

import io.qameta.allure.Allure;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TestListener implements ITestListener {

    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        System.out.println("Test suite started: " + iTestContext.getName());
        iTestContext.setAttribute("StartTime", ZonedDateTime.now());
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        System.out.println("Test suite finished: " + iTestContext.getName());
        ZonedDateTime startTime = (ZonedDateTime) iTestContext.getAttribute("StartTime");
        ZonedDateTime endTime = ZonedDateTime.now();

        if (startTime != null) {
            String duration = DateTimeFormatter.ofPattern("HH:mm:ss").format(endTime.minusSeconds(startTime.toEpochSecond()));
            System.out.println("Total execution time: " + duration);
        }
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        System.out.println("Test started: " + getTestMethodName(iTestResult));
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        System.out.println("Test succeeded: " + getTestMethodName(iTestResult));
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        System.out.println("Test failed: " + getTestMethodName(iTestResult));

        // Add the error message to the Allure report
        if (iTestResult.getThrowable() != null) {
            Allure.addAttachment("Error Message", iTestResult.getThrowable().getMessage());
            iTestResult.getThrowable().printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        System.out.println("Test skipped: " + getTestMethodName(iTestResult));
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        System.out.println("Test failed but within success percentage: " + getTestMethodName(iTestResult));
    }
}