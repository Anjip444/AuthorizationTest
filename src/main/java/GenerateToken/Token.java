package GenerateToken;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Token {

    private static final Logger LOGGER = LoggerFactory.getLogger(Token.class);

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;

    public static String getBaseUrl() {
        return baseUrl;
    }

    private static String baseUrl ="http://13.126.80.194:8080";
    private String userName ="rupeek";
    private String pwd ="password";

    public String generateToken(){
        try{
            JSONObject bodys = createAuthJsonBody(userName,pwd);
            String token = RestAssured.given().body(bodys.toJSONString()).contentType("application/json").when()
                    .post(baseUrl+"/authenticate").then().statusCode(HttpStatus.SC_OK).extract().body().jsonPath().getString("token");
            LOGGER.info("Token generated Successfully");
            setToken(token);
        }catch(Exception e){
            LOGGER.info("Token generation failed :"+e.getMessage());
            return null;
        }
        return token;

    }

    public JSONObject createAuthJsonBody(String uName, String pwd){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username",uName);
        jsonObject.put("password",pwd);
        return jsonObject;

    }

}
