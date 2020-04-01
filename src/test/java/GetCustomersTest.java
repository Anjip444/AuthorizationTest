import GenerateToken.Token;
import io.restassured.internal.http.Status;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class GetCustomersTest {
    Util util = new Util();

    String phone="9972939567";

    private static final Logger LOGGER = LoggerFactory.getLogger(GetCustomersTest.class);

    @BeforeClass
    public String getTokenVal(){
        Token token = new Token();
        return token.generateToken();
    }

    @Test
    public void getCustomersTest(){
        int expectedCustomers =3;
        LOGGER.info("Get customers test started");
        Response response = util.getAllUsers(getTokenVal());
        response.then().statusCode(HttpStatus.SC_OK);
        ArrayList<String> arrayList = response.then().extract().body().jsonPath().getJsonObject("");
        Assert.assertEquals(expectedCustomers,arrayList.size());
        String phoneN = response.then().extract().body().jsonPath().getString("[1].phone");
        Assert.assertEquals(phone,phoneN);
        LOGGER.info("Get customers test ended");
    }

    @Test
    public void invalidAuthTokenTest(){
        LOGGER.info("Invalid auth token test started");
        Response response = util.getAllUsers(getTokenVal()+1);
        response.then().statusCode(HttpStatus.SC_UNAUTHORIZED);
        LOGGER.info("Invalid auth token test ended");
    }

    @Test
    public void getCustomerByPhoneNumberTest(){
        int expectedCustomers =1;
        LOGGER.info("Get customers by phone test started");
        Response response = util.getUsersByPhone(getTokenVal(),phone);
        response.then().statusCode(HttpStatus.SC_OK);
        ArrayList<String> arrayList = response.then().extract().body().jsonPath().getJsonObject("");
        Assert.assertEquals(arrayList.size(),expectedCustomers);
        LOGGER.info("Get customers by phone test ended");
    }

    @Test
    public void getCustomerByInvalidPhoneNumberTest(){
        LOGGER.info("Get customers by Invalid phone test started");
        Response response = util.getUsersByPhone(getTokenVal(),phone+1);
        response.then().statusCode(HttpStatus.SC_BAD_REQUEST);
        LOGGER.info("Get customers by Invalid phone test ended");
    }

    @Test
    public void invalidAuth_getCustomerByPhoneNumberTest(){
        LOGGER.info("invalid auth Get customers by phone test started");
        Response response = util.getUsersByPhone(getTokenVal()+1,phone);
        response.then().statusCode(HttpStatus.SC_UNAUTHORIZED);
        LOGGER.info("Invalid auth Get customers by phone test ended");
    }
}
