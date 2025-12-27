package Pet;

import client.CreatePet;
import configutartion.Config;
import io.restassured.response.Response;
import org.apache.maven.surefire.shared.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import static helper.HttpHelper.isSuccess;

public class CreatePetTest extends Setup {

    CreatePet createPet = new CreatePet();
    int id = (int) (Math.random() * (400 - 40 + 1) + 40);
    String name = RandomStringUtils.random(10, true, false);

    @Test(description = "create pet with post method")
    public void createPet() {
        Response createResponse = createPet.sendRequest(id, name);
        Assert.assertTrue(isSuccess(createResponse.statusCode()), String.format("%s %s in create api", Config.getConfig().getValidationMessagePositive(), createResponse.getBody()));
        Assert.assertNotEquals(Config.getConfig().getResponseCodePositive(), null, createResponse.getBody().prettyPrint());
    }

    @Test(description = "when pet name is null, response will be fail")
    public void createPetWithNoName() {
        Response createResponse = createPet.sendRequest(id, null);
        Assert.assertFalse(isSuccess(createResponse.statusCode()), String.format("%s %d in create api", Config.getConfig().getValidationMessageNegative(), createResponse.getStatusCode()));
        Assert.assertNotEquals(Config.getConfig().getResponseCodeNegative(), null, createResponse.getBody().prettyPrint());
    }

    @Test(description = "when pet id is zero, response will be fail")
    public void createPetWithZeroId() {
        Response createResponse = createPet.sendRequest(0, name);
        Assert.assertFalse(isSuccess(createResponse.statusCode()), String.format("%s %d in create api", Config.getConfig().getValidationMessageNegative(), createResponse.getStatusCode()));
        Assert.assertNotEquals(Config.getConfig().getResponseCodeNegative(), null, createResponse.getBody().prettyPrint());
    }
}
