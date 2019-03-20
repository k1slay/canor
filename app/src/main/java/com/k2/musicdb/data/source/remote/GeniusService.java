package com.k2.musicdb.data.source.remote;

import com.k2.musicdb.common.Constants;
import com.k2.musicdb.data.models.ApiResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Copyright (C) 2019 K2 CODEWORKS
 * All rights reserved
 *
 * @author Kislay
 * @since 2/2/2019
 */
public interface GeniusService {

    @GET("{path}/{id}")
    Call<ApiResponse> getData(
            @Header(Constants.Genius.AUTH_HEADER_KEY) String token,
            @Path("path") String path,
            @Path("id") String id,
            @QueryMap Map<String, String> options
    );

    @GET("{path}")
    Call<ApiResponse> query(
            @Header(Constants.Genius.AUTH_HEADER_KEY) String token,
            @Path("path") String path,
            @QueryMap Map<String, String> options
    );

}
