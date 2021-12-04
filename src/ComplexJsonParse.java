import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {
	
	public static void main(String[] args)
	{
		JsonPath js = new JsonPath(payload.CoursePrice());
		
		//Print No of courses returned by API
		int count = js.getInt("courses.size()");//size method only applies to array.
		System.out.println(count);
		
		//Print Purchase Amount
		int TotalAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println(TotalAmount);
		
		//Print Title of the first course
		String titleFirstCourse = js.get("courses[0].title");
		System.out.println(titleFirstCourse);
		
		//Print All course titles and their respective Prices
		for(int i=0; i<count; i++)//count will take number dynamically, if we modify json to 4 values, then also it takes count
		{
			String courseTitles = js.get("courses["+i+"].title");
			int price = js.get("courses["+i+"].price");
			System.out.println(courseTitles);
			System.out.println(price);
		}
		
		//Print no of copies sold by RPA Course
		for(int i=0; i<count; i++)
		{
			String courseTitles = js.get("courses["+i+"].title");
			if(courseTitles.equalsIgnoreCase("RPA"))
			{
				int copies = js.get("courses["+i+"].copies");
				System.out.println(copies);
				break;
			}
		}
		
		//Verify if Sum of all Course prices matches with Purchase Amount
		
	}

}
