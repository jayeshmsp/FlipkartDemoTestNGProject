package smokeScenarios;

import java.io.IOException;
import org.testng.SkipException;
import org.testng.annotations.BeforeSuite;
//import parentClass.BaseInit;
import parentClass.BaseInit3;

import utility.UtilityMethods;

public class BaseInitSmokeScenario extends BaseInit3 
{
	@BeforeSuite
	public void checkTestSuite() throws IOException, InterruptedException
	{
		startUP();
		
		if(!UtilityMethods.checkTestSuiteForExecution("SmokeScenarios", suite)){
				
			throw new SkipException("Execution mode of the test suite(smokeScenario) is set to NO");
		}
	}
}
