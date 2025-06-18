package magento.testingboard.testrunner;

import io.cucumber.testng.*;
import magento.testingboard.base.BaseTestClass;
//import io.cucumber.testng.CucumberFeatureWrapper;
//import io.cucumber.testng.PickleEventWrapper;
import magento.testingboard.utilities.ExcelReader;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.testng.annotations.*;
import org.testng.log4testng.Logger;

import java.io.IOException;
import java.util.*;

public class TestExecutionEngine extends BaseTestClass {
    public static final Logger logger = Logger.getLogger(TestExecutionEngine.class);
    private TestNGCucumberRunner testNGCucumberRunner;
    private final String excelFile = DIR + "\\"+getConfigProperty("testDataFile");
    public ExcelReader exl = new ExcelReader(excelFile);
    static HashMap<String, ArrayList<HashMap<String, String>>> allExecutingScenariosTestData = new LinkedHashMap<>();
    HashMap<String, Integer> scenarioDetails = new LinkedHashMap<>();

    @BeforeSuite
    public void testData() {
        try {
            allExecutingScenariosTestData = new ExcelReader(excelFile).masterExecutionData("Master Sheet");
        } catch (Exception e) {
            System.out.println("The Error: "+ e.getMessage());
        }
    }

    @BeforeClass(alwaysRun = true)
    public void setUpClass() {
        try {
            testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("ERROR");
        }
    }

    @DataProvider(parallel = false, name = "scenarios")
    public Iterator<Object[]> scenarios() {
        ArrayList<Object[]> modifiedList = new ArrayList<>();
        try {
            if (testNGCucumberRunner == null) {
                return modifiedList.iterator();
            }
            modifiedList = filterTheFeature(testNGCucumberRunner.provideScenarios());
        } catch (Exception e) {
            e.printStackTrace();
//            logInfo("ERROR",  "Error while executing method " + getMethodName() +" in the class file "+getClassName()+ " with error: " + e.toString());
        }
        return modifiedList.iterator();
    }

    @Test(dataProvider = "scenarios")
    public void runScenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
        try {
            //scenario name and test data for executing scenario from the 'allExecutingScenariosTestData' collection
            String currentScenarioName = pickleWrapper.getPickle().getName().trim();
            System.out.println("primary execution : " + currentScenarioName);

            if (scenarioDetails.containsKey(currentScenarioName)) {
                scenarioDetails.put(currentScenarioName, scenarioDetails.get(currentScenarioName) + 1);
            } else {
                scenarioDetails.put(currentScenarioName, 1);
            }

            //Passing test data to generic keywords
            threadTestData.set(allExecutingScenariosTestData.get(currentScenarioName).get((scenarioDetails.get(currentScenarioName)) - 1));

            try {
                //Executing the scenario
                testNGCucumberRunner.runScenario(pickleWrapper.getPickle());
            } catch (Throwable e) {
                e.printStackTrace();
//                logInfo("ERROR", "Error while executing method " + getMethodName() +" in the class file "+getClassName()+ " with error: " + e.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
//            logInfo("ERROR", "Error while executing method " + getMethodName() +" in the class file "+getClassName()+ " with error: " + e.toString());
        }
    }

    private ArrayList<Object[]> filterTheFeature(Object[][] data) {
        ArrayList<Object[]> modifiedList = new ArrayList<>();
        HashMap<String, ArrayList<Object[]>> featureAndScenario = new LinkedHashMap<>();
        try {
            if (allExecutingScenariosTestData == null || allExecutingScenariosTestData.isEmpty()) {
                System.exit(1);
            } else {
                ArrayList<String> scenarioList = null;
                scenarioList = new ArrayList<>(allExecutingScenariosTestData.keySet());

                if (data != null) { //Actual Feature File Have Scenarios
                    for (String scenarioName : scenarioList) {
                        scenarioName = scenarioName.trim();
                        ArrayList<Object[]> tempObjectCollector = new ArrayList<>();
                        String featureNameFromFeatureFile = "";
                        for (int i = 0; i < data.length; i++) {
                            featureNameFromFeatureFile = (data[i][1]).toString().trim().replace("\"", "");
                            String scenarioNameFromFeatureFile = (data[i][0]).toString().trim().replaceAll("\"", "");
                            if (scenarioName.contains((scenarioNameFromFeatureFile))) {
                                for (int k = 0; k < allExecutingScenariosTestData.get(scenarioName).size(); k++) {
                                    tempObjectCollector.add(data[i]);
                                    System.out.println("FeatureAndScenario: "+featureAndScenario + " Feature From Feature File: " + featureNameFromFeatureFile + " Included " + featureAndScenario.containsKey(featureNameFromFeatureFile));
                                    if (featureAndScenario.containsKey(featureNameFromFeatureFile)) {
                                        tempObjectCollector.addAll(featureAndScenario.get(featureNameFromFeatureFile));
                                    }
                                    featureAndScenario.put(featureNameFromFeatureFile, tempObjectCollector);
                                }
                                break;
                            }
                        }
                    }
                    for (String key : featureAndScenario.keySet())
                        modifiedList.addAll(featureAndScenario.get(key));
                    Collections.reverse(modifiedList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
//            logInfo("ERROR", "Error while executing method " + getMethodName() +" in the class file "+getClassName()+ " with error: " + e.toString());
        }
        return modifiedList;
    }

    public void closeExcelFile() {
        try {
            exl.workbook.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        try {
            if (testNGCucumberRunner == null) {
                return;
            }
            testNGCucumberRunner.finish();
        } catch (Exception e) {
            System.out.println("The Error: "+ e.getStackTrace());
        }
    }


}
