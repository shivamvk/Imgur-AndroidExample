package io.shivamvk.imgur_androidexample.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseModel{

    @SerializedName("data")
    private List<ImageModel> data;
    @SerializedName("success")
    private boolean isSuccessful;
    @SerializedName("status")
    private int code;

    public List<ImageModel> getData() {
        return data;
    }

    public void setData(List<ImageModel> data) {
        this.data = data;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
