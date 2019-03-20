package com.k2.musicdb.data;

import com.google.gson.Gson;
import com.k2.musicdb.common.Constants;
import com.k2.musicdb.data.source.remote.GeniusService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Copyright (C) 2019 K2 CODEWORKS
 * All rights reserved
 *
 * @author Kislay
 * @since 2/5/2019
 */

@Module
public class DataModule {

    @Provides
    public Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Constants.Genius.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    public Gson provideGson() {
        return new Gson();
    }

    @Provides
    public GeniusService provideGeniusService(Retrofit retrofit) {
        return retrofit.create(GeniusService.class);
    }

}
