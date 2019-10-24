import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.logging.Logger;
import static io.restassured.RestAssured.given;

public class Get {

    Logger log;

    @BeforeClass
    public static void setBaseUri () {
        RestAssured.baseURI = "http://ergast.com";
    }

    @Test
    public void GetParametresiz(){

        Response res = given().when().get("/api/f1/drivers/alonso/constructors/renault/seasons.json").then()
                .contentType("application/json").extract().response();
        System.out.println(res.asString());

        JsonPath jsonPath = res.jsonPath();
        String limit = jsonPath.get("MRData.limit");
        System.out.println("Limit => " + limit);
    }

    @Test
    public void GetParametreli(){
        Response res = given()
                .queryParam("limit", "30")
                .queryParam("offset", "0")
                .queryParam("total", "6")
                .queryParam("series","f1")
                .queryParam("driverId", "alonso")
                .when()
                .get("/api/f1/drivers/alonso/constructors/renault/seasons.json").then()
                .assertThat().statusCode(200).and().contentType("application/json").extract().response();

        System.out.println(res.asString());
    }

    @Test
    public void Post(){
        String b = "{ " +
                "               \"season\":\"2010\"," +
                "               \"url\":\"https:\\/\\/en.wikipedia.org\\/wiki\\/2006_Formula_One_season\"" +
                "            }";
        Response res = given().body(b).when().post("/api/f1/drivers/alonso/constructors/renault/seasons.json").then().assertThat()
                .statusCode(200).and().contentType("application/json").and()
                .extract().response();
        System.out.println(res.asString());
    }
}
