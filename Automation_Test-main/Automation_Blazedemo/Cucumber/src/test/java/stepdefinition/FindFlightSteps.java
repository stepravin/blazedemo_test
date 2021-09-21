package stepdefinition;

import java.util.List;
import java.util.regex.Pattern;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import java.util.regex.Matcher;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;


public class FindFlightSteps {

	public static WebDriver driver;
	private Pattern pattern;
	private Matcher matcher;
	
	//Parameters
	String depaturecity="Philadelphia";
	String destinationcity="Buenos Aires";
	String Idregex="^[0][1-9]\\d{9}$|^[1-9]\\d{12}$";

	@Given("I launch the blazedemo")
	public void i_launch_the_blazedemo() throws Exception {
		System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
		driver=new ChromeDriver();
		driver.get("http://blazedemo.com");
		driver.manage().window().maximize();
	}

	@When("I select the depature city")
	public void i_select_the_depature_city() throws Exception{
		driver.findElement(By.name("fromPort")).click();
		driver.findElements(By.tagName("option")).get(1).click();
	}

	@When("I select the destination city")
	public void i_select_the_destination_city() throws Exception{
		driver.findElement(By.name("toPort")).click();
		driver.findElements(By.tagName("option")).get(1).click();


	}

	@Then("I click on find flights button")
	public void i_click_on_find_flights_button() throws Exception {
		driver.findElement(By.cssSelector("input.btn.btn-primary")).click();
	}


	@Given("I am on reserve page")
	public void i_am_on_reserve_page() throws Exception{
		String reserve=driver.getTitle();
		Assert.assertEquals(reserve, "BlazeDemo - reserve");
		String Header=driver.findElement(By.tagName("h3")).getText();
		String Expected="Flights from "+ depaturecity +" to "+ destinationcity+":";
		Assert.assertEquals(Header, Expected);
		System.out.println(Header);
		System.out.println(Expected);
	}

	@When("I see the flight details")
	public void i_see_the_flight_details() throws Exception{
		WebElement htmltable=driver.findElement(By.xpath("//div[@class='container']/table[1]/tbody"));
		List<WebElement> rows=htmltable.findElements(By.tagName("tr"));
		int rows_count=rows.size();
		System.out.println("Number of rows:"+rows.size());
		List<WebElement> columns=htmltable.findElements(By.xpath("//div[@class='container']/table/thead/tr/th"));
		System.out.println("Number of columns:"+columns.size());
		for (int row = 0; row < rows_count; row++) {
			List < WebElement > Columns_row = rows.get(row).findElements(By.tagName("td"));
			int columns_count = Columns_row.size();
			System.out.println("Number of cells In Row " + row + " are " + columns_count);
			for (int column = 0; column < columns_count; column++) {
				String celltext = Columns_row.get(column).getText();
				System.out.println("Cell Value Of row number " + row + " and column number " + column + " Is " + celltext);
			}
		}

	}

	@Then("I click on Choose this flight")
	public void i_click_on_choose_this_flight() throws Exception{
		driver.findElements(By.cssSelector("input.btn.btn-small")).get(2).click();
	}

	@Given("I am on purchase page")
	public void i_am_on_purchase_page() throws Exception{
		String purchase=driver.getTitle();
		Assert.assertEquals(purchase, "BlazeDemo Purchase");

	}

	@When("I will the passanger details")
	public void i_will_the_passanger_details() throws Exception{
		driver.findElement(By.id("inputName")).sendKeys("Test User");
		driver.findElement(By.id("address")).sendKeys("123 Test Address");
		driver.findElement(By.id("city")).sendKeys("London");
		driver.findElement(By.id("state")).sendKeys("England");
		driver.findElement(By.id("zipCode")).sendKeys("N18XB");
		driver.findElement(By.id("cardType")).click();
		driver.findElements(By.tagName("option")).get(1).click();
		driver.findElement(By.id("creditCardNumber")).sendKeys("1234567890123456");
		driver.findElement(By.id("creditCardMonth")).clear();
		driver.findElement(By.id("creditCardMonth")).sendKeys("10");
		driver.findElement(By.id("creditCardYear")).clear();
		driver.findElement(By.id("creditCardYear")).sendKeys("2021");
		driver.findElement(By.id("nameOnCard")).sendKeys("Test User");
	}

	@Then("I click on purchase flight")
	public void i_click_on_purchase_flight() throws Exception{
		driver.findElement(By.cssSelector("input.btn.btn-primary")).click();

	}

	@Given("I am on confirmation page")
	public void i_am_on_confirmation_page() throws Exception {
		String purchase=driver.getTitle();
		Assert.assertEquals(purchase, "BlazeDemo Confirmation");
		String Header=driver.findElement(By.tagName("h1")).getText();
		String Expected="Thank you for your purchase today!";
		Assert.assertEquals(Header, Expected);
		System.out.println(Header);
		System.out.println(Expected);
	}

	@When("I see the confirmation details")
	public void i_see_the_confirmation_details() throws Exception {
		driver.findElement(By.className("table")).isDisplayed();
	}

	@Then("I see the confirmation Id")
	public boolean i_see_the_confirmation_id() throws Exception {
		WebElement Id=driver.findElement(By.xpath("//table[@class='table']/tbody/tr[1]/td[2]"));
		Id.isDisplayed();
		String Confirmationid=Id.getText();
		System.out.println(Confirmationid);
		pattern = Pattern.compile(Idregex);
		matcher = pattern.matcher(Confirmationid);
		return matcher.matches();
	}
}