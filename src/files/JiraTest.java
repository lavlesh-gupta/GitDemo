package files;
import static io.restassured.RestAssured.*;

import java.io.File;

import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

public class JiraTest {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		RestAssured.baseURI = "http://localhost:8080";
		System.out.println("Git Test1");
		System.out.println("Git Test2");
		
		System.out.println("Git Demo Branch");
		
		SessionFilter session = new SessionFilter();//We can use session filters to take session variables for next methods.
		given().header("Content-Type","application/json").body("{ \"username\": \"lavalesh.gupta\", \"password\": \"Jira&&12345\" }")
		.log().all().filter(session).when().post("/rest/auth/1/session").then().log().all().extract().response().asString();
		
		//Add comment
		String expectedMessage = "This is the comment added for the REST API automation.";
		String addCommentResponse = given().pathParam("key", "10026").log().all().header("Content-Type","application/json").body("{\r\n"
				+ "  \"visibility\": {\r\n"
				+ "    \"type\": \"role\",\r\n"
				+ "    \"value\": \"Administrators\"\r\n"
				+ "  },\r\n"
				+ "  \"body\": \""+expectedMessage+"\"\r\n"
				+ "}").filter(session).when().post("/rest/api/2/issue/{key}/comment") //key is path parameter and taken from params.
		.then().log().all().assertThat().statusCode(201).extract().response().asString();
		JsonPath js = new JsonPath(addCommentResponse);
		String commentId = js.getString("id");
		
		//Add attachment
		given().header("X-Atlassian-Token","no-check").filter(session).pathParam("key", "10026").multiPart("file",new File("JiraText.txt")).
		header("Content-Type","multipart/form-data").
		when().post("/rest/api/2/issue/{key}/attachments").
		then().log().all().assertThat().statusCode(200);
		
		//Get issue details
		String issueDetails = given().filter(session).pathParam("key", "10026").queryParam("fields", "comment").
		when().get("/rest/api/2/issue/{key}").
		then().log().all().assertThat().statusCode(200).extract().response().asString();
		System.out.println(issueDetails);
		
		JsonPath js1 = new JsonPath(issueDetails);
		int CommentsCount = js1.getInt("fields.comment.comments.size()");
		for(int i=0; i<CommentsCount; i++)
		{
			String CommentIdIssue = js1.get("fields.comment.comments["+i+"].id").toString();
			
			if(CommentIdIssue.equalsIgnoreCase(commentId))
			{
				String message=js1.get("fields.comment.comments["+i+"].body").toString();
				System.out.println(message);
				Assert.assertEquals(message, expectedMessage);
			}
		}
	}

}
