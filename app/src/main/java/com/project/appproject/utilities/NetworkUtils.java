package com.project.appproject.utilities;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.alibaba.fastjson.JSON.*;

public class NetworkUtils {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String URL = "https://untis.othr.de/WebUntis/jsonrpc.do?school=OTH-Regensburg";
    private static OkHttpClient client = new OkHttpClient();
    private static String sessionID = "";


    static String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public String buildJSONString (String id, String methodName, Object parameters) {
        JSONObject object = new JSONObject();
        object.fluentPut("id", id);
        object.put("method", methodName);
        object.put("params", parameters);
        object.put("jsonrpc", "2.0");
        String json = toJSONString(object);
        return json;
    }

    public String authenticate() {
        String response = "";
        JSONObject params = new JSONObject();
        params.put("user", "gast");
        params.put("password", "");
        params.put("client", "VNIV7VJVUGW4BJ2B");
        String json = buildJSONString("ID", "authenticate", params);
        try {
            System.out.println(json);
            response = post(URL, json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public void setup() {
        String authentification = authenticate();
        parseObject(authentification, JSONObject.class);
        //TODO: sessionID herausfinden und speichern
        System.out.println(authentification);

        //System.out.println(sessionID);
        //post(URL, buildJSONString(sessionID,"getSubjects", ""));
        //TODO: alle Daten holen und auslesen/speichern
    }

    public static void main(String[] args) {
        //nur zum Testen!
        new NetworkUtils().setup();


    }


}
