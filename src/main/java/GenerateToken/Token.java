package GenerateToken;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.json.simple.JSONObject;

public class Token {

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;
    private String baseUrl ="http://13.126.80.194:8080";
    private String userName ="rupeek";
    private String pwd ="password";

    public void generateToken(){
        JSONObject bodys = createAuthJsonBody(userName,pwd);
        String token = RestAssured.given().body(bodys.toJSONString()).contentType("application/json").when()
                .post(baseUrl+"/authenticate").then().statusCode(200).extract().body().jsonPath().getString("token");
        setToken(token);
        System.out.println(token);
    }

    public JSONObject createAuthJsonBody(String uName, String pwd){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username",uName);
        jsonObject.put("password",pwd);
        return jsonObject;

    }

    public static void main(String[] args) {
        Token token = new Token();
        token.generateToken();
    }

}
