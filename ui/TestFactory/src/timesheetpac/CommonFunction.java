package timesheetpac;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class CommonFunction {
	public static WebDriver driver;
	public static StringBuffer verificationErrors = new StringBuffer();

	// public static String URL =
	// "https://ignatiuzsoftware-2560be1aea4c00.sharepoint.com/Office365Timesheet/";
	// TestUsrl
	public static String URL = "https://ignatiuzsoftware-2560be1aea4b5a.sharepoint.com/AutomationSite/Timesheet_2.4/Office365Timesheet";

	public static String Text;
	public static String valiadtonMsg;
	public static String Textvalue;
	public static boolean acceptNextAlert = true;
	public static boolean temp = true;
	public static boolean test;
	public static Dimension value;

	// Launch/Login/Quit Methods
	public static void launch() {
		try {

			System.setProperty("webdriver.gecko.driver", "D:\\Geckofile\\geckodriver.exe");
			driver = new FirefoxDriver();
			driver.get(URL);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		} catch (Exception e) {
			System.out.println(e);

		}
	}

	public static String login(String UserName, String Password) throws Exception {
		try {

			launch();
			Thread.sleep(7000);
			EnterTextByID("i0116", UserName);
			ButtonClickByID("idSIButton9");
			EnterTextByID("i0118", Password);
			Thread.sleep(8000);
			ButtonClickByID("idSIButton9");
			Thread.sleep(7000);
			ButtonClickByID("idSIButton9");
			AssertPageTitle("Office 365 Timesheet");
			Thread.sleep(3000);
			driver.findElement(By.xpath("//*[@id=\"ctl00_onetidHeadbnnr2\"]")).click();
			Thread.sleep(7000);
			Text = driver.findElement(By.xpath("//*[@id=\"suiteLinksBox\"]")).getText();
			System.out.println("Logged-in User = " + Text);
		}

		catch (Exception e) {
			System.out.println(e);

		}
		return Text;

	}

	public static void tearDown() throws Exception {
		try {

			driver.quit();
			String verificationErrorString = verificationErrors.toString();
			if (!"".equals(verificationErrorString)) {
				fail(verificationErrorString);
			}
		} catch (Exception e) {
			System.out.println(e);

		}
	}

	// @SuppressWarnings("unlikely-arg-type")
	public static boolean VerifyAccountDetails(String Xpath, List<String> expected, int num) {

		try {

			Text = driver.findElement(By.xpath("//div[@id='sideNavBox']")).getText();
			value = driver.findElement(By.xpath("//div[@id='sideNavBox']")).getSize();
			
			Boolean temp = true;
			List<String> actual = new ArrayList<String>();
			for (int i = 2; i <= num; i++) {
				Textvalue = driver.findElement(By.xpath("//ul[@id='leftNavigation']//li[" + i + "]")).getText();
				System.out.println("Navigation links = " + Textvalue);
				actual.add(Textvalue);
				if (Textvalue.equals("Help")) {
					try {
						if (actual.equals(expected)) {
							assertEquals(actual, expected);
							System.out.println("Expected " + expected + " match found with actual " + actual);
							temp = true;
							break;
						} else {
							fail("Expected " + expected + " match not found with acutal value " + actual + ".\n");
							  assertTrue(false);
						}
					} catch (AssertionError e) {
						fail("Expected " + expected + " match not found with acutal value " + actual + ".\n");
						  assertTrue(false);
						System.out.println(e);
					}

				}
			}
			if (temp == false) {
				fail("Expected " + expected + "match not found");
			}
			// return temp;
		} catch (Exception e) {
			fail("Expected " + expected + "match not found");
			System.out.println(e);
		}
		return temp;
	}

	// Assert Methods
	public static void AssertPageTitle(String expected) {
		String actual = null;
		try {
			actual = driver.getTitle();
			System.out.println(actual);
			assertEquals(actual, expected);
		} catch (AssertionError e) {
			fail("Expected " + expected + " match not found with acutal value " + actual + ".\n");
			  assertTrue(false);
			System.out.println(e);

		}
	}

	// Not yet used
	public static void AssertValidationMsgByXpath(String Path) throws InterruptedException {
		try {

			valiadtonMsg = driver.findElement(By.xpath(Path)).getText();
			// Thread.sleep(5000);
			System.out.println("Added User = " + valiadtonMsg);
			Thread.sleep(5000);
		} catch (Exception e) {
			fail("Expected match not found");
			  //assertTrue(false);
			System.out.println(e);

		}
	}

	// Not yet used
	public static void AssertValueByID(String ID, String expected) {
		String actual = null;
		try {
			actual = driver.findElement(By.id(ID)).getText();
			System.out.println(actual);
			assertEquals(actual, expected);
		} catch (AssertionError e) {
			fail("Expected " + expected + " match not found with acutal value " + actual + ".\n");
			System.out.println(e);

		}

	}

	public static void AssertValueByXPathForDifrntStatus(String xpath, String expected) {
		String actual = null;
		try {

			actual = driver.findElement(By.xpath(xpath)).getText();
			System.out.println("Fetched value = " + actual);
			if (actual.equals(expected)) {

				assertEquals(actual, expected);
				System.out.println("" + expected + " expected get compared with actual value " + actual + "\n");

			} else {
				assertNotEquals(actual, expected,
						" " + expected + " timesheet gets submitted as " + actual + " state.");
				System.out.println("" + expected + " timesheet gets submitted in " + actual + " state.\n");
			}

		} catch (AssertionError e) {
			fail("Expected " + expected + " match not found with acutal value " + actual + ".\n");
			  assertTrue(false);
			System.out.println(e);

		}
	}

	public static void AssertValue(String xpath, String expected) {
		String actual = null;
		try {

			actual = driver.findElement(By.xpath(xpath)).getText();
			System.out.println("Fetched value = " + actual);
			if (actual != null) {
				assertEquals(actual, expected);
				System.out.println("" + expected + " expected value compared with actual value " + actual + "\n");
			} else {
				  assertTrue(false,"Expected " + expected + " match not found with acutal value " + actual + ".");
				//fail("Expected " + expected + " match not found with acutal value " + actual + ".");
			}
		} catch (AssertionError e) {
			 assertTrue(false,"Expected " + expected + " match not found with acutal value " + actual + ".");
			//fail("Expected " + expected + " match not found with acutal value " + actual + ".");
			System.out.println(e);

		}
	}
	//Not used yet
	public static void Asserttext(String Value) {
		try {
			valiadtonMsg = driver.findElement(By.xpath("//div[contains(@class,'dataTables_info')]")).getText();
			// String[] arrText = valiadtonMsg.split("");

			Boolean temp = true;
			for (int i = 1; i <= 25; i++) {

				Text = driver.findElement(By.xpath("//div[@id='content']//tbody//tr[" + i + "]//td[2]//span[1]"))
						.getText();

				if (Text.equals(Value)) {

					assertEquals(Text, Value);
					System.out.println("Match found = " + Value + " Added successfully.");

					temp = true;
					break;
				}
			}
			if (temp == false) {
				fail("Expected " + Value + " not found");
				// System.out.println("Match not found");
			}
		} catch (AssertionError e) {
			fail("Expected " + Value + " not found");
			System.out.println(e);

		}

	}

	public static void AssertHeaderByxpath(String xpath, String expected) {
		String actual = null;
		try {
			actual = driver.findElement(By.xpath(xpath)).getText();
			System.out.println(actual);
			assertEquals(actual, expected);
		} catch (AssertionError e) {
			fail("Expected " + expected + " match not found with acutal value " + actual + ".");
			  assertTrue(false);
			System.out.println(e);
		}
	}

	public static void AssertURL(String expected) {
		try {
			String currentURL = driver.getCurrentUrl();
			if (currentURL.contains("Timesheet-User-Manual")) {
				System.out.println(currentURL);
				assertEquals(currentURL, expected);
			}
		} catch (AssertionError e) {
			fail("Expected " + expected + " not found");
			System.out.println(e);
		}

	}

	// Enter Value
	public static void EnterTextByID(String ID, String Text) {
		try {
			driver.findElement(By.id(ID)).sendKeys(Text);

		} catch (Exception e) {
			System.out.println(e);

		}
	}

	public static void EnterTextByXPath(String xpath, String Text) {
		driver.findElement(By.xpath(xpath)).sendKeys(Text);
	}

	// Button Click Methods
	public static void ButtonClickByID(String ID) {
		driver.findElement(By.id(ID)).click();
	}

	// Click Link Methods
	public static void ClickBylinkText(String Text) {
		
		try {
			driver.findElement(By.linkText(Text)).click();
		} catch (Exception e) {
			fail();
			e.printStackTrace();
		}
	}

	public static void ClickByID(String ID) throws InterruptedException {
		driver.findElement(By.id(ID)).click();
		Thread.sleep(7000);
	}

	public static void ClearByID(String ID) throws InterruptedException {
		driver.findElement(By.id(ID)).clear();
		Thread.sleep(7000);
	}

	public static String ClickByXpathGetText(String ID) throws InterruptedException {
		try {
			driver.findElement(By.xpath(ID)).click();
			Text = driver.findElement(By.xpath(ID)).getText();
		} catch (Exception e) {
			System.out.println(e);

		}
		return Text;
	}

	public static void ClickByXpath(String xpath) {

		driver.findElement(By.xpath(xpath)).click();
	}

	// Select Methods
	public static void SelectReportCheckBox(String xpath1, String xpath2) {

		WebElement element = driver.findElement(By.xpath(xpath1));
		Actions action = new Actions(driver);
		action.moveToElement(element).perform();

		WebElement subElement2 = driver.findElement(By.xpath(xpath2));
		action.moveToElement(subElement2);
		subElement2.click();
		action.perform();
	}

	public static void SelectCheckBox(String xpath1) {

		WebElement element = driver.findElement(By.xpath(xpath1));
		Actions action = new Actions(driver);
		action.moveToElement(element);
		element.click();
		action.perform();

	}

	// Not yet used
	public static void SelectValueFromAutoTextDropdown(String Text) throws InterruptedException {
		try {
			driver.findElement(By.id("selecteditem")).sendKeys(Text);
			Thread.sleep(2000);
			java.util.List<WebElement> listItems = driver.findElements(By.id("selecteditem-list"));
			listItems.get(0).click();
		} catch (Exception e) {

			System.out.println(e);

		}
	}

	public static void SelectByVisibleText(String ID, String Text) {

		new Select(driver.findElement(By.id(ID))).selectByVisibleText(Text);

	}

	public static void SelectByVisibleTextXpath(String Xpath, String Text) {

		new Select(driver.findElement(By.xpath(Xpath))).selectByVisibleText(Text);

	}

	// Get Methods
	public static String GetSelectedOption(String Xpath) {
		try {
			Select select = new Select(driver.findElement(By.xpath(Xpath)));
			WebElement option = select.getFirstSelectedOption();
			String defaultItem = option.getText();
			System.out.println("Selected Object is = " + defaultItem);
			return defaultItem;

		} catch (NoSuchElementException e) {
			System.out.println(e);
		}
		return Text;
	}

	public static String GetTextByXpath(String xpath) throws InterruptedException {

		Thread.sleep(1000);
		Textvalue = driver.findElement(By.xpath(xpath)).getText();
		System.out.println(Textvalue);
		Thread.sleep(5000);
		return Textvalue;

	}

	public static String[] GetEnteriesDetails(String xpath) {

		String enteries = driver.findElement(By.xpath(xpath)).getText();
		System.out.println(enteries);

		String[] entries = enteries.split(" ");
		return entries;
	}

	// Not yet used
	public static void GetTextByClass(String className) {

		Textvalue = driver.findElement(By.className(className)).getText();
		System.out.println("Validation Message = " + Textvalue);
	}

	public static boolean GetCheckBoxValue(String xpath1) {

		WebElement checkbox = driver.findElement(By.xpath(xpath1));
		System.out.println("The checkbox is selection state is - " + checkbox.isSelected());

		return test;

	}

	public static List<String> GetAllFridaysBetweenDateRange(String startdate, String enddate, String pattenFormat) {
		String start = startdate;// "01/01/2018";
		String end = enddate;// "30/12/2018";
		DateTimeFormatter pattern = DateTimeFormat.forPattern("dd/MM/yyyy");
		DateTime startDate = pattern.parseDateTime(start);
		DateTime endDate = pattern.parseDateTime(end);

		List<DateTime> fridays = new ArrayList<>();
		List<String> fridays1 = new ArrayList<>();

		String pattern2 = pattenFormat;// "yyyy/MM/dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern2);

		while (startDate.isBefore(endDate)) {
			if (startDate.getDayOfWeek() == DateTimeConstants.FRIDAY) {
				fridays.add(startDate);
				fridays1.add(simpleDateFormat.format(startDate.toDate()));
			}
			startDate = startDate.plusDays(1);
		}

		return fridays1;
	}

	public static int GetRowCountofTheTable(String xpath) throws InterruptedException {

		int rowCount = driver.findElements(By.xpath(xpath)).size(); // Get the table Row Count
		return rowCount;
	}

	// Not yet used
	public static String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				try {
					Robot r = new Robot();
					r.keyPress(KeyEvent.VK_ENTER);
				} catch (AWTException e) {
					e.printStackTrace();
				}
				// alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}

	public static void PressEnter() throws Exception {

		try {
			Alert alert = driver.switchTo().alert();
			System.out.println(alert.getText());
			alert.accept();
			/*
			 * Robot r = new Robot(); r.keyPress(KeyEvent.VK_ENTER);
			 * r.keyRelease(KeyEvent.VK_ENTER);
			 */
			// Thread.sleep(4000);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void GetURL(String URL) {

		driver.navigate().to(URL);
		String CurrURL = driver.getCurrentUrl();
		// System.out.println(CurrURL);
		if (CurrURL.contains("Timesheet-User-Manual")) {

			System.out.println("User Manual present on this URL :- " + CurrURL);

		} else {
			fail("User Manual not found");
		}

	}

	public static void SendEmail() {
		
		Actions actionObject = new Actions(driver);
		actionObject.keyDown(Keys.CONTROL).sendKeys(Keys.F5).keyUp(Keys.CONTROL).perform();
		
		// Create object of Property file
		Properties props = System.getProperties();//new Properties();
 
		// this will set host of server- you can change based on your requirement 
		props.put("mail.smtp.host", "smtp.gmail.com");
 
		// set the port of socket factory 
		props.put("mail.smtp.socketFactory.port", "465");
 
		// set socket factory
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
 
		// set the authentication to true
		props.put("mail.smtp.auth", "true");
 
		// set the port of SMTP server
		props.put("mail.smtp.port", "465");
 
		// This will handle the complete authentication
		Session session = Session.getDefaultInstance(props,
 
				new javax.mail.Authenticator() {
 
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
 
					return new PasswordAuthentication("yogendra.kushwah@ignatiuz.com", "Ashi@321");
 
					}
 
				});
 
		try {
 
			// Create object of MimeMessage class
			Message message = new MimeMessage(session);
 
			// Set the from address
			message.setFrom(new InternetAddress("yogendra.kushwah@ignatiuz.com"));
 
			// Set the recipient address
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("priyanka.saroch@ignatiuz.com"));
            
                        // Add the subject link
			message.setSubject("TestCase Execution Report");
 
			// Create object to add multimedia type content
			BodyPart messageBodyPart1 = new MimeBodyPart();
 
			// Set the body of email
			messageBodyPart1.setText("Execution report of all the Test Suite.");
 
			// Create another object to add another content
			MimeBodyPart messageBodyPart2 = new MimeBodyPart();
 
			// Mention the file which you want to send
			//String filename = "C:\\Users\\priyanka.saroch\\eclipse-workspace\\Timesheet\\test-output\\emailable-report.html";
			String filename = System.getProperty("user.dir")+"\\Default test.pdf";
				System.out.println("File Path: " + filename);	
			// Create data source and pass the filename
			DataSource source = new FileDataSource(filename);
		 
			// set the handler
			messageBodyPart2.setDataHandler(new DataHandler(source));
 
			// set the file
			messageBodyPart2.setFileName(filename);
 
			// Create object of MimeMultipart class
			Multipart multipart = new MimeMultipart();
 
			// add body part 1
			multipart.addBodyPart(messageBodyPart2);
 
			// add body part 2
			multipart.addBodyPart(messageBodyPart1);
 
			// set the content
			message.setContent(multipart);
 
			// finally send the email
			Transport.send(message);
 
			System.out.println("=====1st Email Sent=====");
 
		} catch (MessagingException e) {
 
			throw new RuntimeException(e);
		}

 }
	
	public static void sendPDFReportByGMail() {

		String from = "yogendra.kushwah@ignatiuz.com";
		String pass = "Ashi@321";
		String to = "priyanka.saroch@ignatiuz.com";
		String subject = "Testing Subject";
		String body = "This is message body";
				
		Properties props = System.getProperties();
		String host = "smtp.gmail.com";
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.password", pass);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		try {
			// Set from address
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			// Set subject
			message.setSubject(subject);
			message.setText(body);
			BodyPart objMessageBodyPart = new MimeBodyPart();
			objMessageBodyPart.setText("Please Find The Attached Report File!");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(objMessageBodyPart);
			objMessageBodyPart = new MimeBodyPart();
			// Set path to the pdf report file
			String filename = System.getProperty("user.dir") + "\\Default test.pdf";
			// Create data source to attach the file in mail
			DataSource source = new FileDataSource(filename);
			objMessageBodyPart.setDataHandler(new DataHandler(source));
			objMessageBodyPart.setFileName(filename);
			multipart.addBodyPart(objMessageBodyPart);
			message.setContent(multipart);
			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, pass);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			System.out.println("=====2nd Email Sent=====");
		}
		catch (AddressException ae) {
			ae.printStackTrace();
		}
		catch (MessagingException me) {

			me.printStackTrace();

		}
	}
		
	public static void takeSnapShot(WebDriver webdriver,String fileWithPath) throws Exception{

        //Convert web driver object to TakeScreenshot

        TakesScreenshot scrShot =((TakesScreenshot)webdriver);

        //Call getScreenshotAs method to create image file

                File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);

            //Move image file to new destination

                File DestFile=new File(fileWithPath);

                //Copy file at destination

                FileUtils.copyFile(SrcFile, DestFile);
            
    }
	

}
