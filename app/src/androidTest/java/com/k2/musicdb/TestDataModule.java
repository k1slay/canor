package com.k2.musicdb;

import com.k2.musicdb.common.Constants;
import com.k2.musicdb.data.DataModule;
import com.k2.musicdb.data.source.remote.GeniusService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

/**
 * Copyright (C) 2019 K2 CODEWORKS
 * All rights reserved
 *
 * @author Kislay
 * @since 28/02/19
 */

public class TestDataModule extends DataModule {

    @Override
    public Retrofit getRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.Genius.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NetworkBehavior behavior = NetworkBehavior.create();
        MockRetrofit mockRetrofit = new MockRetrofit.Builder(retrofit)
                .networkBehavior(behavior)
                .build();

        return mockRetrofit.retrofit();
    }

    @Override
    public GeniusService provideGeniusService(Retrofit retrofit) {
        return retrofit.create(MockGeniusService.class);
    }

}
