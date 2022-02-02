import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class eventCreateAndUpdate {

    @Test(priority = 1)
    public void getOrganizationId(ITestContext context) {

        //base url
        RestAssured.baseURI = "https://www.eventbriteapi.com/v3/";

        //initialized the request object
        RequestSpecification httpRequest = RestAssured.given();

        //Bearer Token for the EventBrite account
        String token = "5YYA52A25BC7M2EX7RSO";

        //Passing headers using request object
        httpRequest.header("Authorization","Bearer "+token).header("Content-Type","application/json");

        //Performing a Get Call and storing the response in the response object
        Response response = httpRequest.request(Method.GET,"users/me/organizations/");

        //Stored the response in a map, so we can deseralize it
        Map<Object, Object> jsonResponseAsMap = (Map) response.getBody().as(Map.class);

        //Since the organization object has an array, we cast it into a list
        List<Object> list = (List<Object>) jsonResponseAsMap.get("organizations");

        //There is only one array and it has json key value pairs, so it is casted into a Map
        Map<Object, Object> bahumuta = (Map<Object, Object>) list.get(0);

        //The organization id is extracted which is important for the event creation post call
        String id = (String) bahumuta.get("id");

        //context to share the id value with the other test cases
        context.setAttribute("ID", id);

        //Storing the status code
        int statusCode = response.getStatusCode();

        //Verifying the status code
        Assert.assertEquals(200, statusCode);


    }

    @Test(priority = 2)
    public void createEvent(ITestContext context) {

        RestAssured.baseURI = "https://www.eventbriteapi.com/v3/";

        RequestSpecification httpRequest = RestAssured.given();

        String token = "5YYA52A25BC7M2EX7RSO";

        httpRequest.header("Authorization","Bearer "+token).header("Content-Type","application/json");

        //This is the request body for the post method, creates an event named "SuperHeroes for cool people"
        String payload = "{\r\n"+ "  \"event\": {\r\n"+ "    \"name\": {\r\n"+ "      \"html\": \"<p>SuperHeroes</p>\"\r\n"+ "    },\r\n"
                + "    \"description\": {\r\n"+ "      \"html\": \"<p>Only Cool People</p>\"\r\n"+ "    },\r\n"+ "    \"start\": {\r\n"
                + "      \"timezone\": \"UTC\",\r\n"+ "      \"utc\": \"2022-05-12T02:00:00Z\"\r\n"+ "    },\r\n"+ "    \"end\": {\r\n"
                + "      \"timezone\": \"UTC\",\r\n"+ "      \"utc\": \"2022-05-12T02:00:00Z\"\r\n"	+ "    },\r\n"+ "    \"currency\": \"USD\",\r\n"
                + "    \"online_event\": false,\r\n"+ "    \"organizer_id\": \"\",\r\n"	+ "    \"listed\": true,\r\n"+ "    \"shareable\": false,\r\n"+ "    \"invite_only\": false,\r\n"
                + "    \"show_remaining\": true,\r\n"+ "    \"password\": \"\",\r\n"+ "    \"capacity\": 100,\r\n"+ "    \"is_reserved_seating\": false,\r\n"+ "    \"is_series\": true,\r\n"
                + "    \"show_pick_a_seat\": true,\r\n"+ "    \"show_seatmap_thumbnail\": true,\r\n"+ "    \"show_colors_in_seatmap_thumbnail\": true,\r\n"+ "    \"locale\": \"de_AT\"\r\n"
                + "  }\r\n"+ "}";

        //Passing the organization id to the post url endpoint
        String id = (String) context.getAttribute("ID");
        Response response = httpRequest.body(payload).post("organizations/"+id+"/events/");

        ////Stored the response in a map, so we can deseralize it
        Map <Object, Object> jsonResponseAsMap = (Map) response.getBody().as(Map.class);
        String event_id = (String) jsonResponseAsMap.get("id");

        System.out.println(event_id);

        //Setting the event id, as it is a parameter to the update event post api
        context.setAttribute("event_id", event_id);

        //Storing the status code
        int statusCode = response.getStatusCode();

        //Verifying the status code
        Assert.assertEquals(200, statusCode);

    }

    @Test(priority = 3)
    public void updateEvent(ITestContext context) {

        RestAssured.baseURI = "https://www.eventbriteapi.com/v3/";

        RequestSpecification httpRequest = RestAssured.given();

        String token = "5YYA52A25BC7M2EX7RSO";

        httpRequest.header("Authorization","Bearer "+token).header("Content-Type","application/json");

        //This is the request body for updating the event you created, the villans have taken over
        String payload = "{\r\n"+ "  \"event\": {\r\n"+ "    \"name\": {\r\n"+ "      \"html\": \"<p>Villans</p>\"\r\n"	+ "    },\r\n"	+ "    \"description\": {\r\n"
                + "      \"html\": \"<p>Bad People</p>\"\r\n"+ "    },\r\n"	+ "    \"start\": {\r\n"+ "      \"timezone\": \"UTC\",\r\n"+ "      \"utc\": \"2022-05-12T02:00:00Z\"\r\n"
                + "    },\r\n"+ "    \"end\": {\r\n"+ "      \"timezone\": \"UTC\",\r\n"+ "      \"utc\": \"2022-05-12T02:00:00Z\"\r\n"	+ "    },\r\n"+ "    \"currency\": \"USD\",\r\n"
                + "    \"online_event\": false,\r\n"+ "    \"organizer_id\": \"\",\r\n"	+ "    \"listed\": false,\r\n"	+ "    \"shareable\": false,\r\n"+ "    \"invite_only\": false,\r\n"
                + "    \"show_remaining\": true,\r\n"	+ "    \"password\": \"\",\r\n"	+ "    \"capacity\": 1000,\r\n"	+ "    \"is_reserved_seating\": false,\r\n"	+ "    \"is_series\": false,\r\n"
                + "    \"show_pick_a_seat\": true,\r\n"	+ "    \"show_seatmap_thumbnail\": true,\r\n"+ "    \"show_colors_in_seatmap_thumbnail\": true\r\n"+ "  }\r\n"+ "}";

        //Passing the organization id to the post url endpoint
        String event_id = (String) context.getAttribute("event_id");
        String endpoint = "events/"+event_id+"/";
        Response response = httpRequest.body(payload).post(endpoint);

        //Storing the status code
        int statusCode = response.getStatusCode();

        //Verifying the status code
        Assert.assertEquals(200, statusCode);

    }
}
