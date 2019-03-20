package com.k2.musicdb.data.models;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.k2.musicdb.data.models.DataObject;

import androidx.annotation.NonNull;

/**
 * Copyright (C) 2019 K2 CODEWORKS
 * All rights reserved
 *
 * @author Kislay
 * @since 2/2/2019
 */

public class ApiResponse {

    @SerializedName("meta")
    @Expose
    private Meta meta;

    @SerializedName("response")
    @Expose
    private JsonObject response;

    public class Meta {

        @SerializedName("status")
        @Expose
        private int status;

        public int getStatus() {
            return status;
        }
    }

    public <T extends DataObject> T getData(Class<T> tClass, @NonNull String key, Gson gson) {
        if (response.get(key).isJsonArray()) {
            String response = gson.toJson(getResponse());
            return gson.fromJson(response, tClass);
        }
        return gson.fromJson(response.get(key), tClass);
    }

    private JsonObject getResponse() {
        return response;
    }
}
