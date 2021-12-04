package demo;

import io.restassured.RestAssured;
import pojo.AddPlace;
import pojo.locations;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class SerializationTest {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		AddPlace p = new AddPlace();
		locations l = new locations();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		p.setLocation(l);
		p.setAccuracy(50);
		p.setName("Frontline house");
		p.setPhone_number("(+91) 983 893 3937");
		p.setAddress("29, side layout, cohen 09");
		List<String> myList = new ArrayList<String>();
		myList.add("shoe park");
		myList.add("shop");
		p.setTypes(myList);
		p.setWebsite("http://google.com");
		p.setLanguage("French-IN");
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String res = given().log().all().queryParam("key", "qaclick123").
		body(p).
		when().post("maps/api/place/add/json").
		then().assertThat().statusCode(200).extract().response().asString();
		System.out.println(res);
	}

}
