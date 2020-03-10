package SalesForcePakage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ReusableMethods {
		static WebDriver driver;
		protected static ExtentReports report;
		protected static ExtentTest logger;
		/* name of the method:   Intialize browser
		 * BriefDescription  :   browser intialization
		 *  created date     :08/08/19 
		 *  LastModified Date:08/08/19           
		 */
	public static void initialize_driver() {
		System.setProperty("webdriver.chrome.driver","C:\\Users\\poorn\\eclipse-workspace\\Page_object_model\\Drivers\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
	}
	/* name of the method:   lunch browser
	 * BriefDescription  :   lunching browser
	 *  created date     :08/08/19 
	 *  LastModified Date:08/08/19           
	 */
	public static void launchUrl()
	{
		driver.get("https://login.salesforce.com");
		driver.manage().window().maximize();
	}
	
	public static void enterText(WebElement obj,String textval,String objName) throws InterruptedException

	{
		if(obj.isDisplayed())
		{
			Thread.sleep(4000);
			obj.sendKeys(textval);
			//System.out.println("pass: "+textval+"value is entered in " +objName+"field");
           logger.log(LogStatus.INFO, textval+"value is entered in " +objName+"field");

		}
		else
		{
			//System.out.println("fail:" +objName+ "field does not exist please check application");
			logger.log(LogStatus.ERROR, objName+ "field does not exist please check application");
		}
	}
	
	/* name of the method:   clickobject--->Button
	 * BriefDescription  :   clicking a button
	 * Arguments         :  obj-->object,objName--->object name
	 *  createdby        :  Automation team 
	 *  created date     :08/08/19 
	 *  LastModified Date:08/08/19         
	 */
	public static void clickObj(WebElement obj,String objName)
	{
		if(obj.isDisplayed())
		{
			obj.click();
			logger.log(LogStatus.PASS,objName + "button is clicked");
			//System.out.println("pass :" +objName + "button is clicked");
					}
		else
		{
			logger.log(LogStatus.FAIL, objName+"button is not displayed ,please check the application");
			//System.out.println("Fail:" +objName+"button is not displayed ,please check the application");
			
		}
	}
	
	public static String[][] readXlData(String path, String string) throws IOException{
		FileInputStream fs=new FileInputStream(new File(path));
		HSSFWorkbook wb=new HSSFWorkbook(fs);
		HSSFSheet sheet=wb.getSheet("Sheet1");
		int rowCount=sheet.getLastRowNum()+1;
		int colCount=sheet.getRow(0).getLastCellNum();
		String[][] data=new String[rowCount][colCount];
		for(int i=0;i<rowCount;i++){
			for(int j=0;j<colCount;j++){
				int cellType=sheet.getRow(i).getCell(j).getCellType();
				if(cellType==HSSFCell.CELL_TYPE_NUMERIC){
					int val=(int)sheet.getRow(i).getCell(j).getNumericCellValue();
					data[i][j]=String.valueOf(val);
				}
				else
					data[i][j]=sheet.getRow(i).getCell(j).getStringCellValue();
					
			}
		}
		return (data);
	}
	public static void initializeExtentReport() {
		String fileName=new SimpleDateFormat("'SimpleDemo_'yyyyMMddHHmm'.html'").format(new Date());
		String path="C:\\Users\\poorn\\eclipse-workspace\\SalesForce\\Reports\\" + fileName;
		report =new ExtentReports(path);
		
	}
	public static void closeTestCase() {
		report.endTest(logger);
		driver.quit();
		report.flush();
	}
	
}
	



