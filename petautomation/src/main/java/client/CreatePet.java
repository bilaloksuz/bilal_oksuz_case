package client;

import configutartion.Config;
import helper.HttpHelper;
import io.restassured.response.Response;
import model.Category;
import org.json.JSONObject;

import java.util.ArrayList;

public class CreatePet {
    private int id;
    Category category;
    private String name;
    ArrayList<String> photoUrls = new ArrayList();
    ArrayList<Category> tags = new ArrayList();
    private String status;
    transient HttpHelper httpHelper = new HttpHelper();

    public CreatePet() {

    }

    public String requestBody(int id, String name) {
        Category category = new Category(id, name);

        setId(id);
        setCategory(new Category(id, name));
        setName(name);
        setStatus("available");

        ArrayList<Category> tags = new ArrayList();
        tags.add(category);
        setTags(tags);

        ArrayList<String> url = new ArrayList();
        url.add(name);
        setPhotoUrls(url);
        return new JSONObject(this).toString();
    }

    public Response sendRequest(int id, String name) {
        return httpHelper.SendRequestPost(Config.getConfig().getPetBaseURL() + Config.getConfig().getPetBasePath(), requestBody(id, name));
    }

    public int getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCategory(Category categoryObject) {
        this.category = categoryObject;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<String> getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(ArrayList<String> photoUrls) {
        this.photoUrls = photoUrls;
    }

    public ArrayList<Category> getTags() {
        return tags;
    }

    public void setTags(ArrayList<Category> tags) {
        this.tags = tags;
    }
}
