package WorkFlow;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.opencsv.CSVReader;
import timesheetpac.CommonFunction;
import timesheetpac.JyperionListener;

@Listeners(JyperionListener.class)
public class ToVerifyTimesheetCreationUsingCSV {
	// Provide CSV file path. It Is In D: Drive.
		String CSV_PATH = "D:\\Details.csv";	
		String valiadtonMsg;
		String Text;
		boolean temp;
		int rowcount;
		int reCount;
		int i;
		String Status;
		String date;
		String UserRole = "Manager";
		String User = "Employee";

		//CSV Variables
		String EMLoginID ;
		String EMPassword ;
		String Project;
		String ProjectTask ;
		String Date ;
		String Mon ;
		String Tue ;
		String Wed ;
		String Thur ;
		String Fri ;
		String TSKDesp ;
		String ManLoginID;
		String ManPassword;
		String Approved;
		String Rejected;
		
			
		@Test(priority = 1, description="ToVerifyTimesheetNewCreation")
		public void ToVerifyTimesheetNewCreation() throws IOException {

			/*This will load csv file*/
			CSVReader reader = new CSVReader(new FileReader(CSV_PATH));
			String[] csvCell;
			while ((csvCell = reader.readNext()) != null) {
				/*if(csvCell.length <=10 && csvCell.length >=6) {
				AdLoginID = csvCell[0];
				AdPassword = csvCell[1];
				Project = csvCell[2];
				ProjectTask = csvCell[3];
				User = csvCell[4];
				UserRole = csvCell[5];
			}else*/if(csvCell.length <=10 && csvCell.length <=6) {
					ManLoginID = csvCell[0];
					ManPassword = csvCell[1];
					Project = csvCell[2];
					ProjectTask = csvCell[3];
					Approved = csvCell[4];
					Rejected = csvCell[5];
				}else if(csvCell.length <=11 ) {	
					EMLoginID = csvCell[0];
					EMPassword = csvCell[1];
					Project = csvCell[2];
					ProjectTask = csvCell[3];
					Date = csvCell[4];
					Mon = csvCell[5];
					Tue = csvCell[6];
					Wed = csvCell[7];
					Thur = csvCell[8];
					Fri = csvCell[9];
					TSKDesp = csvCell[10];
				}else {
					System.out.println("Parameters doesn't match ");
				}
			}			
				try {
					CommonFunction.login(EMLoginID, EMPassword);
					Thread.sleep(1000);
					List<String> expected = new ArrayList<String>();
					expected.add("Draft");
					expected.add("Pending Approval");
					expected.add("Rejected");
					expected.add("Approved");
					expected.add("Employee Portal");
					expected.add("New Timesheet");
					expected.add("Project Tasks");
					expected.add("Reports");
					expected.add("License");
					expected.add("Help");

					CommonFunction.VerifyAccountDetails("//ul[@id='leftNavigation']//li[2]", expected, 9);
					System.out.println("The logged -in user is Employee Account");

					CommonFunction.ClickBylinkText("New Timesheet");
					Thread.sleep(9000);

					CommonFunction.ClickByXpath("//*[@id=\"datetimepicker1\"]");
					CommonFunction.EnterTextByXPath("//input[@id='datetimepicker1']", Date);
					Thread.sleep(3000);
					CommonFunction.ClickByID("ddlProject");
					CommonFunction.SelectByVisibleText("ddlProject", Project);

					CommonFunction.ClickByID("ddltasks");
					CommonFunction.SelectByVisibleText("ddltasks", ProjectTask);

					CommonFunction.ClickByID("txtMon");
					CommonFunction.EnterTextByID("txtMon", Mon);

					CommonFunction.ClickByID("txtTue");
					CommonFunction.EnterTextByID("txtTue", Tue);

					CommonFunction.ClickByID("txtWed");
					CommonFunction.EnterTextByID("txtWed", Wed);

					CommonFunction.ClickByID("txtThu");
					CommonFunction.EnterTextByID("txtThu", Thur);

					CommonFunction.ClickByID("txtFri");
					CommonFunction.EnterTextByID("txtFri", Fri);

					CommonFunction.ClickByID("txtDesc");
					CommonFunction.EnterTextByID("txtDesc", TSKDesp);
					Thread.sleep(5000);
					CommonFunction.ClickByID("btnSubmit"); // btnDraft
					Thread.sleep(10000);
					valiadtonMsg = CommonFunction.driver.findElement(By.xpath("//span[contains(@id,'UserName')]")).getText();
					System.out.println("User gets directed on Employe portal page of = " + valiadtonMsg);
					Thread.sleep(5000);
					// Verify the added Time sheet Value
					rowcount = CommonFunction.GetRowCountofTheTable("//table[@id='DataTables_Table_0']/tbody/tr[*]");
					for (int i = 1; i <= rowcount; i++) 
					{
						Text = CommonFunction.ClickByXpathGetText("//tbody//tr[" + i + "]//td[7]");
						date = CommonFunction.ClickByXpathGetText("/html[1]/body[1]/form[1]/div[12]/div[1]/div[2]/div[2]/div[3]/div[3]/div[1]/div[1]/table[1]/tbody[1]/tr["+ i + "]/td[2]");
						if (Text.equals("Pending Approval") && date.equals(Date))
						{
							CommonFunction.AssertValue("/html[1]/body[1]/form[1]/div[12]/div[1]/div[2]/div[2]/div[3]/div[3]/div[1]/div[1]/table[1]/tbody[1]/tr["+ i + "]/td[2]",Date);
							CommonFunction.AssertValue("/html[1]/body[1]/form[1]/div[12]/div[1]/div[2]/div[2]/div[3]/div[3]/div[1]/div[1]/table[1]/tbody[1]/tr["+ i + "]/td[7]",Text);
							break;
						}
					}
					Thread.sleep(5000);											
				} catch (Exception e) {
					System.out.println(e + " Element not found or some exception occured");
				}		
		}
	
		@Test(priority = 2, description="ToVerifyTimesheetGetsApprovedFromManager")
		public void ToVerifyTimesheetGetsApprovedFromManager() throws Exception 		
		 {
		    CommonFunction.login(ManLoginID, ManPassword);
			Thread.sleep(6000);
			CommonFunction.ClickBylinkText("Pending Approval");
			Thread.sleep(5000);	
			Boolean temp = true;
			rowcount = CommonFunction.GetRowCountofTheTable("//table[@id='DataTables_Table_0']/tbody/tr[*]");
			for (int i = 1; i <= rowcount; i++) 
			{
				Text = CommonFunction.ClickByXpathGetText("/html[1]/body[1]/form[1]/div[12]/div[1]/div[2]/div[2]/div[3]/div[3]/div[1]/table[1]/tbody[1]/tr["+i+"]/td[7]");
				date = CommonFunction.ClickByXpathGetText("/html[1]/body[1]/form[1]/div[12]/div[1]/div[2]/div[2]/div[3]/div[3]/div[1]/table[1]/tbody[1]/tr["+i+"]/td[2]");
				if (Text.equals("Pending Approval") && date.equals(Date)) 
				{
					CommonFunction.ClickByXpath("(//a[contains(.,'View')])["+i+"]");
					Thread.sleep(5000);
					CommonFunction.EnterTextByXPath("//textarea[@id='txtManagerComment']", Approved);
					Thread.sleep(1000);
					CommonFunction.ClickByXpath("//input[@id='btnSubmit']");
					Thread.sleep(7000);
					temp = true;
					break;
				}
			}
			rowcount = CommonFunction.GetRowCountofTheTable("//table[@id='DataTables_Table_0']/tbody/tr[*]");
			for (int i = 1; i <= rowcount; i++) 
			{
				Status = CommonFunction.ClickByXpathGetText("/html[1]/body[1]/form[1]/div[12]/div[1]/div[2]/div[2]/div[3]/div[3]/div[1]/table[1]/tbody[1]/tr["+ i + "]/td[7]");
				date = CommonFunction.ClickByXpathGetText("/html[1]/body[1]/form[1]/div[12]/div[1]/div[2]/div[2]/div[3]/div[3]/div[1]/table[1]/tbody[1]/tr["+ i + "]/td[2]");
				if (Text != Status) {
					if (date.equals(Date)) {
						CommonFunction.AssertValue("/html[1]/body[1]/form[1]/div[12]/div[1]/div[2]/div[2]/div[3]/div[3]/div[1]/table[1]/tbody[1]/tr["+ i + "]/td[2]",Date);
						CommonFunction.AssertValueByXPathForDifrntStatus("/html[1]/body[1]/form[1]/div[12]/div[1]/div[2]/div[2]/div[3]/div[3]/div[1]/table[1]/tbody[1]/tr["+ i + "]/td[7]",Status);
						temp = true;
						break;
					}
				}
			}
			if (temp == false) 
			{
				System.out.println("Match not found.");
			}		
		}
		
		@Test(priority = 3, description="ToVerifyCreatedTimesheetGetsDeleted")
		public void ToVerifyTimesheetGetsRejectedFromManager() throws Exception 
			{
			// Reject the approved Timehseet
			CommonFunction.ClickByXpath("//a[contains(.,'Approved')]");
			Thread.sleep(7000);
			rowcount = CommonFunction.GetRowCountofTheTable("//table[@id='DataTables_Table_0']/tbody/tr[*]");
			//Boolean temp = true;	
			for (int i = 1; i <= rowcount; i++) {
				Text = CommonFunction.ClickByXpathGetText("/html[1]/body[1]/form[1]/div[12]/div[1]/div[2]/div[2]/div[3]/div[3]/div[1]/table[1]/tbody[1]/tr["+i+"]/td[7]");
				date = CommonFunction.ClickByXpathGetText("/html[1]/body[1]/form[1]/div[12]/div[1]/div[2]/div[2]/div[3]/div[3]/div[1]/table[1]/tbody[1]/tr["+i+"]/td[2]");
				if (Text.equals("Approved") && date.equals(Date)) 
				{				
					CommonFunction.ClickByXpath("(//a[contains(.,'View')])["+i+"]");
					Thread.sleep(5000);
					String Value = CommonFunction.GetTextByXpath("//div[@id='txtOldManagerComment']");			
					if (Value.contains("Approved")) 
					{							
						CommonFunction.EnterTextByXPath("//textarea[@id='txtManagerComment']", Rejected);			
						CommonFunction.ClickByXpath("//input[@id='btnDraft']");	
						Thread.sleep(2000);
						CommonFunction.PressEnter();											
					}
					else 
					{
						System.out.println("This timesheet is already rejected or Approved without any metioned comment.");			
						CommonFunction.ClickByXpath("//input[@id='btnCancel']");
					}					
				}
				else {
					System.out.println("No Approved Timesheet present.");			
				}
			}
				rowcount = CommonFunction.GetRowCountofTheTable("//table[@id='DataTables_Table_0']/tbody/tr[*]");
				for (int i = 1; i <= rowcount; i++) 
				{
					Text = CommonFunction.ClickByXpathGetText("/html[1]/body[1]/form[1]/div[12]/div[1]/div[2]/div[2]/div[3]/div[3]/div[1]/table[1]/tbody[1]/tr["+i+"]/td[7]");
					date = CommonFunction.ClickByXpathGetText("/html[1]/body[1]/form[1]/div[12]/div[1]/div[2]/div[2]/div[3]/div[3]/div[1]/table[1]/tbody[1]/tr["+i+"]/td[2]");
				if (Text.equals("Rejected") && (date.equals(Date)))
				{
					CommonFunction.AssertValue("/html[1]/body[1]/form[1]/div[12]/div[1]/div[2]/div[2]/div[3]/div[3]/div[1]/table[1]/tbody[1]/tr["+i+"]/td[7]",Text);			
					CommonFunction.AssertValue("/html[1]/body[1]/form[1]/div[12]/div[1]/div[2]/div[2]/div[3]/div[3]/div[1]/table[1]/tbody[1]/tr["+i+ "]/td[2]",Date);					
					temp = true;
					break;				
				}			
			}	
			if (temp == false) 
			{
				System.out.println("Match not found");
			}
			CommonFunction.tearDown();	
		}
						
		@Test(priority = 4, description="ToVerifyCreatedTimesheetGetsDeleted")
		public void ToVerifyCreatedTimesheetGetsDeleted() throws Exception 
		{
			
			try {
				CommonFunction.login(EMLoginID, EMPassword);
				Thread.sleep(9000);
				//To delete the created time sheet
				CommonFunction.ClickBylinkText("Employee Portal");
				Thread.sleep(5000);
				rowcount = CommonFunction.GetRowCountofTheTable("//table[@id='DataTables_Table_0']/tbody/tr[*]");
				for (int i = 1; i <= rowcount; i++) 
				{
					Text = CommonFunction.ClickByXpathGetText("//tbody//tr[" + i + "]//td[7]");
					date = CommonFunction.ClickByXpathGetText("/html[1]/body[1]/form[1]/div[12]/div[1]/div[2]/div[2]/div[3]/div[3]/div[1]/div[1]/table[1]/tbody[1]/tr["+ i +"]/td[2]");
					Thread.sleep(4000);
					if (date.equals(Date) && ((Text.equals("Draft") || Text.equals("Rejected") || Text.equals("Pending Approval"))))
					{											
							Thread.sleep(7000);
							CommonFunction.ClickByXpath("//tbody//tr[" + i + "]//td[8]//img[1]");
							//Thread.sleep(7000);
							CommonFunction.PressEnter();
							Thread.sleep(5000);
							reCount = CommonFunction
									.GetRowCountofTheTable("//table[@id='DataTables_Table_1']/tbody/tr[*]");
							if (rowcount != reCount) {
								if (reCount == rowcount - 1) {
									System.out.println("Timesheet row got deleted");
									break;
								}
							} 						
					}
				}
				Thread.sleep(5000);
				CommonFunction.SendEmail();
				Thread.sleep(8000);
				CommonFunction.sendPDFReportByGMail();
				Thread.sleep(8000);
				CommonFunction.tearDown();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		

}
