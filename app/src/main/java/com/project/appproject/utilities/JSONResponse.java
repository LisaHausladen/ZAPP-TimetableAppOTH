package com.project.appproject.utilities;

public class JSONResponse {

    //{"jsonrpc":"2.0","id":"ID","result":RESULT}
    private String jsonrpc;
    private String id;
    private Object result;

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
