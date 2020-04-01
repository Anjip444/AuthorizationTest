package GenerateToken;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
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

    public Response generateToken(String user, String password){
        Response resp;
        try{
            JSONObject bodys = createAuthJsonBody(user,password);
            resp = RestAssured.given().body(bodys.toJSONString()).contentType("application/json").when()
                    .post(baseUrl+"/authenticate");
        }catch(Exception e){
            LOGGER.info("Token generation failed :"+e.getMessage());
            return null;
        }
        return resp;

    }

    public JSONObject createAuthJsonBody(String uName, String pwd){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username",uName);
        jsonObject.put("password",pwd);
        return jsonObject;

    }

}
