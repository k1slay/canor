package com.k2.musicdb.data.source.remote;

import com.google.gson.Gson;
import com.k2.musicdb.data.models.SearchResponse;

import javax.inject.Inject;

/**
 * Copyright (C) 2019 K2 CODEWORKS
 * All rights reserved
 *
 * @author Kislay
 * @since 2/5/2019
 */

public class SearchRequest extends Request<SearchRequest, SearchResponse> {

    public static final String KEY = "hits";
    public static final String QUERY_KEY = "q";

    @Inject
    public SearchRequest(GeniusService geniusService, Gson gson) {
        super(geniusService, gson);
    }

    @Override
    Class<SearchResponse> getDataClass() {
        return SearchResponse.class;
    }

    @Override
    String getDataKey() {
        return KEY;
    }

}
