package magento.testingboard.testrunner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.AfterSuite;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;

@CucumberOptions(features = "src/test/resources/features/", glue = { "magento.testingboard.stepsdefinitions",
        "magento.testingboard.hooks" }, tags = "@Test", monochrome = true, plugin = { "pretty",
                "html:target/cucumber-reports/cucumber-html-report.html",
                "json:target/cucumber.json", "rerun:target/cucumber-reports/rerun.txt" })
public class TestRunner extends TestExecutionEngine {

    @AfterSuite
    public static void setup() {
        File reportOutputDirectory = new File("target");
        List<String> jsonFiles = new ArrayList<>();
        jsonFiles.add("target/cucumber.json");
        String buildNumber = "1";
        String projectName = "Magento Testing Board BDD";
        Configuration configuration = new Configuration(reportOutputDirectory, projectName);
        configuration.setBuildNumber(buildNumber);
        configuration.addClassifications("Platform", "Windows");
        configuration.addClassifications("Browser", "Chrome");
        configuration.addClassifications("Branch", "release/1.0");
        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
        reportBuilder.generateReports();
    }
}
