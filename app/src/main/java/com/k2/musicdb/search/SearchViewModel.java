package com.k2.musicdb.search;

import com.k2.musicdb.common.BaseViewModel;
import com.k2.musicdb.data.models.SearchResponse;

/**
 * Copyright (C) 2019 K2 CODEWORKS
 * All rights reserved
 *
 * @author Kislay
 * @since 2/6/2019
 */

public class SearchViewModel extends BaseViewModel<SearchResponse> {

    public void performSearch(String searchQuery) {
        dataRepository.getSearchResults(searchQuery, getDataListener());
    }

}
