package com.project.appproject.utilities;

import android.content.Context;
import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSONObject;
import com.project.appproject.Lesson;
import com.project.appproject.database.Room;
import com.project.appproject.database.StudyGroup;
import com.project.appproject.database.Subject;
import com.project.appproject.database.Teacher;
import com.project.appproject.database.TimetableDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
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

    public static void main(String[] args) {
        //new NetworkUtils().setup();
    }

    static String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static String buildJSONString(String id, String methodName, Object parameters) {
        JSONObject object = new JSONObject();
        object.put("id", id);
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
        params.put("client", "BNVWSPVZZHR75XN4");
        String json = buildJSONString("ID", "authenticate", params);
        try {
            response = post(URL, json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public void setup(Context context) {
        String authentication = authenticate();
        identifySessionID(authentication);
        Cookie sessionCookie = createCookie();
        setCookie(sessionCookie);

        Subject[] subjects = toJavaObject((com.alibaba.fastjson.JSON) getResponseData("getSubjects", ""), Subject[].class);
        TimetableDatabase.getInstance(context).subjectDao().insertAll(subjects);
        Room[] rooms = toJavaObject((com.alibaba.fastjson.JSON) getResponseData("getRooms", ""), Room[].class);
        //keine Rechte für Teachers
        //TODO insertAll bei allen verwenden
        Teacher[] teachers = toJavaObject((com.alibaba.fastjson.JSON) getResponseData("getTeachers","" ), Teacher[].class);
        StudyGroup[] studyGroups = toJavaObject((com.alibaba.fastjson.JSON) getResponseData("getKlassen", ""), StudyGroup[].class);
        for (StudyGroup studyGroup : studyGroups) {
            TimetableDatabase.getInstance(context).studyGroupDao().insert(studyGroup);
        }
        System.out.println("ende");

    }

    public Lesson[] getLessons(StudyGroup studyGroup){
        Lesson[] lessons;
        String params = "{\"id\": " + studyGroup.getId() + ",\"type\":1}";
        Object data = getResponseData("getTimetable", params);
        lessons = toJavaObject((com.alibaba.fastjson.JSON) data, Lesson[].class);
        //TODO: lessons in Datenbank speichern?
        return lessons;
    }

    private Object getResponseData(String methodName, String parameters) {
        try {
            String response = post(URL, buildJSONString(sessionID,methodName, parameters));
            JSONResponse jsonResponse = parseObject(response, JSONResponse.class);
            return jsonResponse.getResult();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void identifySessionID(String authentication) {
        JSONAuthenticationResponse response = parseObject(authentication, JSONAuthenticationResponse.class);
        this.sessionID = response.getResult().getSessionId();
    }

    @NonNull
    private Cookie createCookie() {
        return new Cookie.Builder()
                    .domain("untis.othr.de")
                    .path("/")
                    .name("JSESSIONID")
                    .value(sessionID)
                    .httpOnly()
                    .secure()
                    .build();
    }


    private void setCookie(final Cookie cookie) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cookieJar(new CookieJar() {
            @Override
            public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl httpUrl) {
                ArrayList<Cookie> list = new ArrayList<>();
                list.add(cookie);
                return list;
            }
        });
        client = builder.build();
        client.cookieJar().loadForRequest(HttpUrl.parse(URL));
    }


}
