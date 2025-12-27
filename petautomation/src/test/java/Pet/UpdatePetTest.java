package Pet;

import client.UpdatePet;
import configutartion.Config;
import io.restassured.response.Response;
import org.apache.maven.surefire.shared.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import static helper.HttpHelper.isSuccess;

public class UpdatePetTest extends Setup {

    UpdatePet updatePet = new UpdatePet();
    int id = (int) (Math.random() * (400 - 40 + 1) + 40);
    String name = RandomStringUtils.random(10, true, false);

    @Test(description = "update pet with put method")
    public void updatePet() {
        Response updateResponse = updatePet.sendRequest(id, name);
        Assert.assertTrue(isSuccess(updateResponse.statusCode()), String.format("%s %s in update api", Config.getConfig().getValidationMessagePositive(), updateResponse.getStatusCode()));
        Assert.assertNotEquals(Config.getConfig().getResponseCodePositive(), null, updateResponse.getBody().prettyPrint());
    }

    @Test(description = "when pet name is null, response will be fail")
    public void updatePetWithNoName() {
        Response updateResponse = updatePet.sendRequest(id, null);
        Assert.assertFalse(isSuccess(updateResponse.statusCode()), String.format("%s %d in update api", Config.getConfig().getValidationMessageNegative(), updateResponse.getStatusCode()));
        Assert.assertNotEquals(Config.getConfig().getResponseCodeNegative(), null, updateResponse.getBody().prettyPrint());
    }

    @Test(description = "when pet id is zero, response will be fail")
    public void updatePetWithZeroId() {
        Response updateResponse = updatePet.sendRequest(0, name);
        Assert.assertFalse(isSuccess(updateResponse.statusCode()), String.format("%s %d in update api", Config.getConfig().getValidationMessageNegative(), updateResponse.getStatusCode()));
        Assert.assertNotEquals(Config.getConfig().getResponseCodeNegative(), null, updateResponse.getBody().prettyPrint());
    }

    @Test(description = "when pet id is not found in system, response will be fail")
    public void updatePetWithNotExistingId() {
        Response updateResponse = updatePet.sendRequest(1232131232, name);
        Assert.assertFalse(isSuccess(updateResponse.statusCode()), String.format("%s %d in update api", Config.getConfig().getValidationMessageNegative(), updateResponse.getStatusCode()));
        Assert.assertNotEquals(Config.getConfig().getResponseCodeNegative(), null, updateResponse.getBody().prettyPrint());
    }

    @Test(description = "when pet id is negative, response will be fail")
    public void updatePetWithNegativeId() {
        Response updateResponse = updatePet.sendRequest(-1, name);
        Assert.assertFalse(isSuccess(updateResponse.statusCode()), String.format("%s %d in update api", Config.getConfig().getValidationMessageNegative(), updateResponse.getStatusCode()));
        Assert.assertNotEquals(Config.getConfig().getResponseCodeNegative(), null, updateResponse.getBody().prettyPrint());
    }
}
