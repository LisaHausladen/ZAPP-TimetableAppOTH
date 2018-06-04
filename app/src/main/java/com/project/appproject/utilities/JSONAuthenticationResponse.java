package com.project.appproject.utilities;

public class JSONAuthenticationResponse extends JSONResponse {
    private Authentication result;

    @Override
    public Authentication getResult() {
        return result;
    }

    public void setResult(Authentication result) {
        this.result = result;
    }
}
