package demo;
import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourse;
import pojo.WebAutomation;

public class oAuthTest {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
	//To get code from UI	
	System.setProperty("webdriver.chrome.driver", "C://Users//lavle//eclipse-workspace//chromedriver.exe");
	WebDriver driver = new ChromeDriver();
	driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=4%2F0AX4XfWjqFuV9aUM-Kc18WSm9KvKkQSGi-lbW2eoIuAT37X4MwF9FE_4L66sJKONHpc6DPg&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
	driver.findElement(By.cssSelector("input[type='email']")).sendKeys("lavtesting01@gmail.com");
	driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER);
	Thread.sleep(3000);
	driver.findElement(By.cssSelector("input[type='password']")).sendKeys("7738006420");
	driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.ENTER);
	Thread.sleep(4000);
	String url = driver.getCurrentUrl();
	String partialcode = url.split("code=")[1];
	String code = partialcode.split("&scope")[0];
	System.out.println(code);
	
	
	//To receive token with given code.
    String accessTokenResponse = given().urlEncodingEnabled(false).
    queryParam("code", code).
    queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com").
    queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W").
    queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php").
    queryParam("grant_type", "authorization_code").
    when().log().all().
    post("https://www.googleapis.com/oauth2/v4/token").asString();
    
    JsonPath js = new JsonPath(accessTokenResponse);
    String accessToken = js.getString("access_token");
    
    //To get the list of trainings provided using acccess token
    String response = given().queryParam("access_token", accessToken)
    .when().log().all()
    .get("https://rahulshettyacademy.com/getCourse.php").asString();
    
    System.out.println(response);
    
    //Deserialization using pojo class
    //if application-type is application/json then we can remove expect part
    GetCourse gc = given().queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON)
    	    .when()
    	    .get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);
    
    System.out.println(gc.getLinkedIn());
    System.out.println(gc.getUrl());
    
    //To get price of course given
    System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
    
    //To get price of course given even if position of that item changes
    List<Api> apiCourses = gc.getCourses().getApi();
    for (int i=0; i<apiCourses.size(); i++)
    {
    	if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices Testing"));
    	{
    		System.out.println(apiCourses.get(i).getPrice());
    	}
    }
    
    //To get all courses list from the json
    ArrayList<String> al = new ArrayList();
    List<WebAutomation> webauto = gc.getCourses().getWebAutomation();
    for (int i=0; i<webauto.size(); i++)
    {
    	al.add(webauto.get(i).getCourseTitle());
    }
    
    //compare coursetitles names with expected
    String[] courselist = {"A", "B", "C"};
    
    //We have to convert array to arraylist so that we can compare list.
    List<String> expectedList = Arrays.asList(courselist);
    
    Assert.assertTrue(al.equals(expectedList));
    
	}

}
