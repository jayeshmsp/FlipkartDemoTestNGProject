package smokeScenarios;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import utility.UtilityMethods;

public class TC01_NavigateToModule extends BaseInitSmokeScenario {
	
	
	@BeforeTest
	public void checkTestCase() throws IOException{
		
		if(!UtilityMethods.checkTestCaseForExecution(this.getClass().getSimpleName(), admin)){
			UtilityMethods.writeResult(1, 3, "SKIP",admin,"TestScripts","AdminPanel");
			test.log(Status.SKIP, MarkupHelper.createLabel("Execution mode of the 'TC01_NavigateToModule' test Case is set to NO", ExtentColor.YELLOW));
			throw new SkipException("Execution mode of the 'TC01_NavigateToModule' test Case is set to NO");
		}
	}
	
	@Test
	public void TC01OpenModule() throws IOException, InterruptedException {
		
		test = report.createTest("TC01_Verify that all the product details display properly.");
		test.assignCategory("Smoke Suite");
		
		WebDriverWait wait = new WebDriverWait(driver, 10);
		
		try 
		{
			//driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			
			driver.get(sitedata.getProperty("googleComURL"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		//Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='text']")));
		
		//Find search box and enter value into the search box.
		setValue("tb_google_search", "Flipkart");
		
		//Thread.sleep(2000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[role='option']")));
		
		//Store all suggested search options to the list of String type.
		List<WebElement> suggestedOptions =  driver.findElements(By.cssSelector("[role='option']"));
		
		int numberOfSuggestedOptions = suggestedOptions.size()-1;
		System.out.println("Suggested options text listed out as below:");
		
		//Iterate through all the suggested options and get text of each option
		for (int i=0; i<numberOfSuggestedOptions; i++)
		{
			String currentOptionText =  suggestedOptions.get(i).getText();
			System.out.println(currentOptionText);
		}
		
		//Press Enter key
		verifyXpath("tb_google_search").sendKeys(Keys.ENTER);
		
		//Thread.sleep(2000);
		
		//Click on Flipkart link
		//driver.findElement(By.xpath("//div[@id='main']//*[contains(@href,'www.flipkart.com')]")).click();
		//driver.findElement(By.xpath("//a[@href='https://www.flipkart.com/']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='https://www.flipkart.com/']"))).click();
	
		//Thread.sleep(2000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Get access to your Orders')]")));
		
		Boolean isloginWindowPresent = driver.findElement(By.xpath("//*[contains(text(),'Get access to your Orders')]")).isDisplayed();
		
		Actions action = new Actions(driver);
		
		if (isloginWindowPresent)
		{
			action.sendKeys(Keys.ESCAPE).perform();
		}
		
		//Thread.sleep(2000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'TVs & Appliances')]")));
		
		//Mouse hover over TVs & Appliances drop down menu 
		WebElement menuTVsAndAppliances = verifyXpath("dd_tvsandappliances");
		action.moveToElement(menuTVsAndAppliances).build().perform();
		
		//Thread.sleep(1000);
		
		
		//Click on Windows Acs category
		//driver.findElement(By.xpath("//*[contains(text(),'Window ACs')]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Window ACs')]"))).click();
		
		//Thread.sleep(2000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@data-id,'ACN')]")));
		
		List<WebElement> listOfWindowACs = driver.findElements(By.xpath("//*[contains(@data-id,'ACN')]"));
		//int numberOfAC = driver.findElements(By.xpath("//*[contains(@data-id,'ACN')]")).size();
		//int numberOfAC = listOfWindowACs.size();
		System.out.println("");
		
		for (int i=0; i<=7; i++ )
		{
			int productNumber = i+1;
			
			if (productNumber == 2 || productNumber == 3 || productNumber == 6)
			{
				String attributeValue = listOfWindowACs.get(i).getAttribute("data-id");				
				WebElement checkboxAddToCompare = driver.findElement(By.xpath("//*[@data-id='"+attributeValue+"']//span[contains(text(),'Add to Compare')]"));
				checkboxAddToCompare.click();
			}
		}
		
		//Click on COMPARE button
		driver.findElement(By.xpath("//span[contains(text(),'COMPARE')]")).click();
		
		test.log(Status.INFO, "Selected three products added successfully for comparison...");
		
		//Thread.sleep(2000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='col col-3-12']//parent::div//following::a[1]")));
		
		List<String> listOfProductName = new ArrayList<>();
		
		//Get all three product's Name and Price
		for (int i=1; i<=3; i++)
		{
			//String productName = driver.findElement(By.xpath("//div[@id='fk-compare-page']//following::a["+i+"]")).getText();
			//String productName = driver.findElement(By.xpath("//div[contains(@class,'col col-3-12')]//following::a["+i+"]")).getText();
			String productName = driver.findElement(By.xpath("//div[@class='col col-3-12']//parent::div//following::a["+i+"]")).getText();
			String productPrice = driver.findElement(By.xpath("//div[@class='col-4-5']/child::div[2]/child::div["+(i+1)+"]/child::div/child::div/child::div")).getText();
			
			listOfProductName.add(productName);
			
			System.out.println("Name of Product"+i+": " + productName);
			System.out.println("Price of Product"+i+": " + productPrice);
			System.out.println("");
		}
		
		//Add all three products to the cart
		for (int i=1; i<=3; i++)
		{
			//Thread.sleep(2000);
			
			//driver.findElement(By.xpath("(//button[text()='ADD TO CART'])["+i+"]")).click();
			//driver.findElement(By.xpath("(//button[text()='ADD TO CART'])[1]")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[text()='ADD TO CART'])[1]"))).click();
			
			Thread.sleep(2000);
			
			if(i != 3)
			{
				driver.navigate().back();
			}
		}
		
		test.log(Status.INFO, "All three products added to the cart successfully...");
		
		//Check delivery availability of already added all three products for specified pincode
		for (int i=0; i<=1; i++)
		{
			String pincode;
			if (i == 0)
			{
				pincode = "380015";
				//Enter pincode into Deliver to text box
				driver.findElement(By.xpath("//input[@placeholder='Enter delivery pincode']")).sendKeys(pincode);
			}
			else
			{
				pincode = "380016";
				
				//Thread.sleep(1000);
				
				//Click on Delivery to drop down list
				//driver.findElement(By.xpath("//span[contains(text(),'380015')]")).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'380015')]"))).click();
				
				//Thread.sleep(1000);
				
				//Enter another pincode into Deliver to text box
				//driver.findElement(By.xpath("//input[@placeholder='Enter delivery pincode']")).sendKeys("380016");
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Enter delivery pincode']"))).sendKeys(pincode);
			}
			
			//Thread.sleep(1000);
			
			//Click on check text link
			//driver.findElement(By.xpath("//span[text()='Check']")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Check']"))).click();
			
			//Iterate through all three products and check for availability of delivery for entered pincode
			for (int j=0; j<=2; j++)
			{
				Thread.sleep(2000);
				
				String actualName = listOfProductName.get(j);
				String replacedName = actualName.replace("-"," -");
				
				String deliveryMessage;
				//if(driver.findElements(By.xpath("//a[text()='"+listOfProductName.get(i)+"']/parent::div/parent::div/parent::div/div[2]//div")).size()>0)
				if(driver.findElements(By.xpath("//a[text()='"+replacedName+"']/parent::div/parent::div/parent::div/div[2]//div")).size()>0)
				{
					//deliveryMessage = driver.findElement(By.xpath("//a[text()='"+listOfProductName.get(i)+"']/parent::div/parent::div/parent::div/div[2]//div")).getText();
					deliveryMessage = driver.findElement(By.xpath("//a[text()='"+replacedName+"']/parent::div/parent::div/parent::div/div[2]//div")).getText();
				}
				else
				{
					//deliveryMessage = driver.findElement(By.xpath("//a[text()='"+listOfProductName.get(i)+"']/parent::div/parent::div/div[4]")).getText();
					deliveryMessage = driver.findElement(By.xpath("//a[text()='"+replacedName+"']/parent::div/parent::div/div[4]")).getText();
				}
				System.out.println("Delivery availability at pincode "+pincode+" for Product"+(j+1)+": " + deliveryMessage);
			}
			System.out.println("");
		}
		
		UtilityMethods.writeResult(1, 3, "PASS",admin,"TestScripts","AdminPanel");
		
		test.log(Status.PASS, "All products details displayed properly.");
	}
	
	@AfterTest
	public void afterTestLogout() throws IOException
	{
		report.flush();
	}
}
