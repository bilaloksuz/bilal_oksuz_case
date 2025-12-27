package Pet;

import client.CreatePet;
import client.UriForApis;
import configutartion.Config;
import io.restassured.response.Response;
import org.apache.maven.surefire.shared.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import static helper.HttpHelper.isSuccess;

public class GetPetTest extends Setup {
    CreatePet createPet = new CreatePet();
    UriForApis getApis = new UriForApis();
    int id = (int) (Math.random() * (400 - 40 + 1) + 40);
    String name = RandomStringUtils.random(10, true, false);

    @Test(description = "get pet with id")
    public void getPet() {
        createPet.sendRequest(id, name);
        Response getResponse = getApis.findById(id);
        Assert.assertTrue(isSuccess(getResponse.statusCode()), String.format("%s %s in get api", Config.getConfig().getValidationMessagePositive(), getResponse.getStatusCode()));
        Assert.assertNotEquals(Config.getConfig().getResponseCodePositive(), null, getResponse.getBody().prettyPrint());
    }

    @Test(description = "when pet id is zero, response will be fail")
    public void getPetWithZeroId() {
        Response getResponse = getApis.findById(0);
        Assert.assertFalse(isSuccess(getResponse.statusCode()), String.format("%s %s in get api", Config.getConfig().getValidationMessageNegative(), getResponse.getStatusCode()));
        Assert.assertNotEquals(Config.getConfig().getValidationMessageNegative(), null, getResponse.getBody().prettyPrint());
    }

    @Test(description = "get pet with status available")
    public void getPetWithAvailableStatus() {
        Response getResponse = getApis.findByStatus("available");
        Assert.assertTrue(isSuccess(getResponse.statusCode()), String.format("%s %s in getByStatus api", Config.getConfig().getValidationMessagePositive(), getResponse.getStatusCode()));
        Assert.assertNotEquals(Config.getConfig().getResponseCodePositive(), null, getResponse.getBody().prettyPrint());
    }

    @Test(description = "get pet with status available1")
    public void getPetWithNotExistStatus() {
        Response getResponse = getApis.findByStatus("available1");
        Assert.assertFalse(isSuccess(getResponse.statusCode()), String.format("%s %s in getByStatus api", Config.getConfig().getResponseCodeNegative(), getResponse.getStatusCode()));
        Assert.assertNotEquals(Config.getConfig().getResponseCodeNegative(), null, getResponse.getBody().prettyPrint());
    }
}
