package Pet;

import client.CreatePet;
import client.UpdatePet;
import client.UriForApis;
import configutartion.Config;
import io.restassured.response.Response;
import org.apache.maven.surefire.shared.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import static helper.HttpHelper.isSuccess;

public class DeletePetTest extends Setup {
    UriForApis deleteApi = new UriForApis();
    CreatePet createPet = new CreatePet();
    int id = (int) (Math.random() * (400 - 40 + 1) + 40);
    String name = RandomStringUtils.random(10, true, false);

    @Test(description = "delete pet with delete method")
    public void deletePet() {
        createPet.sendRequest(id, name);
        Response deleteResponse = deleteApi.deleteById(id);
        Assert.assertTrue(isSuccess(deleteResponse.statusCode()), String.format("%s %s in update api", Config.getConfig().getValidationMessagePositive(), deleteResponse.getStatusCode()));
        Assert.assertNotEquals(Config.getConfig().getResponseCodePositive(), null, deleteResponse.getBody().prettyPrint());
    }

    @Test(description = "when pet id zero, response will be fail")
    public void deletePetWithZeroId() {
        Response deleteResponse = deleteApi.deleteById(0);
        Assert.assertFalse(isSuccess(deleteResponse.statusCode()), String.format("%s %d in update api", Config.getConfig().getValidationMessageNegative(), deleteResponse.getStatusCode()));
        Assert.assertNotEquals(Config.getConfig().getResponseCodeNegative(), null, deleteResponse.getBody().prettyPrint());
    }


    @Test(description = "when pet id is not found in system, response will be fail")
    public void deletePetWithNotExistingId() {
        Response deleteResponse = deleteApi.deleteById(1231321);
        Assert.assertFalse(isSuccess(deleteResponse.statusCode()), String.format("%s %d in update api", Config.getConfig().getValidationMessageNegative(), deleteResponse.getStatusCode()));
        Assert.assertNotEquals(Config.getConfig().getResponseCodeNegative(), null, deleteResponse.getBody().prettyPrint());
    }
}
