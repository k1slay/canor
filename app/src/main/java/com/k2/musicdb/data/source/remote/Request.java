package com.k2.musicdb.data.source.remote;

import com.google.gson.Gson;
import com.k2.musicdb.common.Constants;
import com.k2.musicdb.data.DataListener;
import com.k2.musicdb.data.models.ApiResponse;
import com.k2.musicdb.data.models.DataObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Copyright (C) 2019 K2 CODEWORKS
 * All rights reserved
 *
 * @author Kislay
 * @since 2/5/2019
 */

abstract class Request<R extends Request, T extends DataObject> {

    @RequestType
    private String type = null;
    private Map<String, String> queryParams = new HashMap<>();
    private String id = null;
    private final GeniusService geniusService;
    private final Gson gson;
    private DataListener<T> dataListener;

    public Request(GeniusService geniusService, Gson gson) {
        this.geniusService = geniusService;
        this.gson = gson;
    }

    protected String getType() {
        return type;
    }

    protected Map<String, String> getQueryParams() {
        return queryParams;
    }

    protected String getId() {
        return id;
    }

    public R setType(@RequestType String type) {
        this.type = type;
        return (R) this;
    }

    public R setQueryParams(Map<String, String> queryParams) {
        this.queryParams = queryParams;
        return (R) this;
    }

    public R setId(String id) {
        this.id = id;
        return (R) this;
    }

    public void fetch(DataListener<T> listener) {
        this.dataListener = listener;
        if (this.id == null)
            geniusService.query(Constants.Genius.AUTH_TOKEN, this.type, queryParams).enqueue(callback);
        else
            geniusService.getData(Constants.Genius.AUTH_TOKEN, this.type, this.id, queryParams).enqueue(callback);
    }

    abstract Class<T> getDataClass();

    abstract String getDataKey();

    private Callback<ApiResponse> callback = new Callback<ApiResponse>() {
        @Override
        public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
            if (response.isSuccessful())
                dataListener.onSuccess(response.body().getData(getDataClass(), getDataKey(), gson));
            else {
                try {
                    dataListener.onFailure(new Exception(response.errorBody().string()));
                } catch (IOException e) {
                    dataListener.onFailure(e);
                }
            }
        }

        @Override
        public void onFailure(Call<ApiResponse> call, Throwable t) {
            dataListener.onFailure(t);
        }
    };

}
