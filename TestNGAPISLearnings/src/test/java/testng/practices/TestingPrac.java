package testng.practices;

import org.testng.annotations.Test;
import io.restassured.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;
import org.hamcrest.*;

public class TestingPrac {

	public RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/")
			.addQueryParam("key", "qaclick123").setContentType(ContentType.JSON).build();
	public ResponseSpecification resp = new ResponseSpecBuilder().expectStatusCode(200)
			.expectContentType(ContentType.JSON).build();

	@Test
	public void googleMapsAPIPost() throws Exception {
		given().spec(req)
				.body("{\r\n" + "  \"location\": {\r\n" + "    \"lat\": -38.383494,\r\n" + "    \"lng\": 33.427362\r\n"
						+ "  },\r\n" + "  \"accuracy\": 50,\r\n" + "  \"name\": \"Frontline house\",\r\n"
						+ "  \"phone_number\": \"(+91) 983 893 3937\",\r\n"
						+ "  \"address\": \"29, side layout, cohen 09\",\r\n" + "  \"types\": [\r\n"
						+ "    \"shoe park\",\r\n" + "    \"shop\"\r\n" + "  ],\r\n"
						+ "  \"website\": \"http://google.com\",\r\n" + "  \"language\": \"French-IN\"\r\n" + "}\r\n"
						+ "\r\n" + "")
				.when().post("maps/api/place/add/json").then().spec(resp).log().all().extract().asString();

	}

	@Test
	public void googleMapsAPIGet() throws Exception {
		String extractDetails = given().spec(req)
				.body("{\r\n" + "  \"location\": {\r\n" + "    \"lat\": -38.383494,\r\n" + "    \"lng\": 33.427362\r\n"
						+ "  },\r\n" + "  \"accuracy\": 50,\r\n" + "  \"name\": \"Frontline house\",\r\n"
						+ "  \"phone_number\": \"(+91) 983 893 3937\",\r\n"
						+ "  \"address\": \"29, side layout, cohen 09\",\r\n" + "  \"types\": [\r\n"
						+ "    \"shoe park\",\r\n" + "    \"shop\"\r\n" + "  ],\r\n"
						+ "  \"website\": \"http://google.com\",\r\n" + "  \"language\": \"French-IN\"\r\n" + "}\r\n"
						+ "\r\n" + "")
				.when().post("maps/api/place/add/json").then().spec(resp).extract().asString();
		JsonPath jsonPath = new JsonPath(extractDetails);
		String placeId = jsonPath.getString("place_id");
		System.out.println(placeId);
		given().spec(req).log().all().queryParam("place_id", placeId).when().get("maps/api/place/get/json").then().spec(resp).log().all().extract().asString();

	}
	@Test
	public void googleMapsAPIsUpdate() throws Exception {
		String extractDetails = given().spec(req)
				.body("{\r\n" + "  \"location\": {\r\n" + "    \"lat\": -38.383494,\r\n" + "    \"lng\": 33.427362\r\n"
						+ "  },\r\n" + "  \"accuracy\": 50,\r\n" + "  \"name\": \"Frontline house\",\r\n"
						+ "  \"phone_number\": \"(+91) 983 893 3937\",\r\n"
						+ "  \"address\": \"29, side layout, cohen 09\",\r\n" + "  \"types\": [\r\n"
						+ "    \"shoe park\",\r\n" + "    \"shop\"\r\n" + "  ],\r\n"
						+ "  \"website\": \"http://google.com\",\r\n" + "  \"language\": \"French-IN\"\r\n" + "}\r\n"
						+ "\r\n" + "")
				.when().post("maps/api/place/add/json").then().spec(resp).extract().asString();
		JsonPath jsonPath = new JsonPath(extractDetails);
		String placeId = jsonPath.getString("place_id");
		given().spec(req).queryParam("place_id", placeId).body("{\r\n"
				+ "\"place_id\":\""+placeId+"\",\r\n"
				+ "\"address\":\"70 Summer walk, USA\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}\r\n"
				+ "").when().put("maps/api/place/update/json").then().spec(resp).log().all().extract().asString();
		
	}

}
