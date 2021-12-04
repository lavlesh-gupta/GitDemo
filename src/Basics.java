import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

import files.ReUsableMethods;
import files.payload;
public class Basics {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
//validate if Add Place API is working as expected these 3 methods in rest assured we have
		
		//given - all input details
		//when - Submit the API -resource,http method
		//then - validate the response
		//contents of the file to string --> content of file can convert into Byte-->Byte data to string
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		//.body(payload.AddPlace()).when().post("maps/api/place/add/json")
		.body(new String(Files.readAllBytes(Paths.get("C:\\Users\\lavle\\eclipse-workspace\\DemoProject\\AddPlace.json")))).when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope", equalTo("APP"))//log().all() is used to log all things in console.
		.header("server","Apache/2.4.18 (Ubuntu)")
		.extract().response().asString();//Extract response into string and store in response string.
		
		System.out.println(response);
		JsonPath js = new JsonPath(response);
		String placeId = js.getString("place_id");   //Here we are mentioning only place_id as it does not have parent. if parent was there then we can put parent.child
		System.out.println(placeId);
		//Add place --> update place with new address --> get place to validate if new address is present in response.
		
		//Update Place
		String newAddress = "Summer Walk, Africa";
		given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+placeId+"\",\r\n"
				+ "\"address\":\""+newAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}")
		.when().put("maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		String getPlaceResponse = given().log().all().queryParams("place_id",placeId,"key","qaclick123")
		.when().get("maps/api/place/get/json")
		.then().assertThat().log().all().statusCode(200).extract().response().asString();
		
		JsonPath js1 = ReUsableMethods.rawToJson(getPlaceResponse);
		String actualAddress = js1.getString("address");
		System.out.println(actualAddress);
		//Cucumber Junit, testng framework are there for testing framework.
		Assert.assertEquals(actualAddress, newAddress);
	}

}
