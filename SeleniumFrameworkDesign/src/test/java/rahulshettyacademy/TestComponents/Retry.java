package rahulshettyacademy.TestComponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer {

	int count = 0;
	int maxTry = 1;
	
	@Override
	public boolean retry(ITestResult result) {
		// TODO Auto-generated method stub
		//After test gets failed, then listener fail method gets executed and then control will come to this method.
		if(count<maxTry)
		{
			count++;
			return true;
		}
		return false;
	}

	
	
}
