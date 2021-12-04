package demo;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.locations;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class SpecBuilderTest {

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
		
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123").
				setContentType(ContentType.JSON).build();
		
		ResponseSpecification res = new ResponseSpecBuilder().expectStatusCode(200).
				expectContentType(ContentType.JSON).build();
		
		RequestSpecification reqs=given().spec(req).body(p);
		
		Response resp = reqs.when().post("maps/api/place/add/json").
		then().spec(res).extract().response();
		
		System.out.println(resp.asString());
		
	}

}
