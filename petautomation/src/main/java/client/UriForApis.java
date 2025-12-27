package client;

import configutartion.Config;
import helper.HttpHelper;
import io.restassured.response.Response;

import java.util.HashMap;

public class UriForApis {

    transient HttpHelper httpHelper = new HttpHelper();

    public UriForApis() {
        super();
    }

    public Response findById(int id) {
        return httpHelper.SendRequestGet(Config.getConfig().getPetBaseURL() + Config.getConfig().getPetBasePath() + "/" + id);
    }

    public Response findByStatus(String status) {
        HashMap<String, Object> statusMap = new HashMap<>();
        statusMap.put("status", status);
        return httpHelper.SendRequestGet(Config.getConfig().getPetBaseURL() + Config.getConfig().getPetBasePath() + Config.getConfig().getPetFindByStatusPath(), statusMap);
    }

    public Response deleteById(int id) {
        return httpHelper.SendRequestDelete(Config.getConfig().getPetBaseURL() + Config.getConfig().getPetBasePath() + "/" + id);
    }
}
