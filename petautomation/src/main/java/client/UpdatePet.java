package client;

import configutartion.Config;
import io.restassured.response.Response;
import model.Category;
import org.json.JSONObject;

import java.util.ArrayList;

public class UpdatePet extends CreatePet {


    public String requestBody(int id, String name, String status) {
        Category category = new Category(id, name);

        setId(id);
        setCategory(new Category(id, name));
        setName(name);
        setStatus(status);

        ArrayList<Category> tags = new ArrayList();
        tags.add(category);
        setTags(tags);

        ArrayList<String> url = new ArrayList();
        url.add(name);
        setPhotoUrls(url);
        return new JSONObject(this).toString();
    }

    public Response sendRequest(int id, String name) {
        return httpHelper.SendRequestPut(Config.getConfig().getPetBaseURL() + Config.getConfig().getPetBasePath(), requestBody(id, name));
    }

    public Response updateStatus(int id, String name, String status) {
        return httpHelper.SendRequestPut(Config.getConfig().getPetBaseURL() + Config.getConfig().getPetBasePath(), requestBody(id, name, status));

    }
}
