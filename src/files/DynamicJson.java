package files;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DynamicJson {
	@Test(dataProvider = "BooksData")
	public void addBook(String isbn, String aisle)
	{
		RestAssured.baseURI= "http://216.10.245.166";
		System.out.println("Git Demo Branch");
		String response = given().header("Content-Type","application/json").
		body(payload.AddBook(isbn,aisle)).
		when().
		post("Library/Addbook.php").
		then().assertThat().statusCode(200).
		extract().response().asString();
		JsonPath js = ReUsableMethods.rawToJson(response);
		String id = js.get("ID");
		System.out.println(id);
		
		//Delete books data
		/*
		 * given().header("Content-Type","application/json").
		 * when().post("Library/DeleteBook.php"). then().assertThat().statusCode(200);
		 */
	}
	
	@DataProvider(name="BooksData")
	public Object[][] getData()
	{
		//array - collection of the elements
		//multidimensional array - collection of arrays
		return new Object[][] {{"lev2","235"}, {"lev3","236"}, {"lev4","237"}, {"lev5","238"}};
				
	}

}
