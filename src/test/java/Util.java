import GenerateToken.Token;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Util {

    private static final Logger LOGGER = LoggerFactory.getLogger(Util.class);

    private String getUsersUrl ="/api/v1/users ";

    public Response getAllUsers(String token){
        Response resp = RestAssured.given()
                .header("Authorization","Bearer "+token)
                .when().get(Token.getBaseUrl()+getUsersUrl);
        return resp;
    }

    public Response getUsersByPhone(String token, String param){
        Response resp = RestAssured.given().param("phone",param)
                .header("Authorization","Bearer "+token)
                .when().get(Token.getBaseUrl()+getUsersUrl);
        return resp;
    }

}
