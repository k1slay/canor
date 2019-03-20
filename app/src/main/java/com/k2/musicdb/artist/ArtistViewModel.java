package com.k2.musicdb.artist;

import com.k2.musicdb.common.BaseViewModel;
import com.k2.musicdb.data.models.Artist;

/**
 * Copyright (C) 2019 K2 CODEWORKS
 * All rights reserved
 *
 * @author Kislay
 * @since 2/6/2019
 */

public class ArtistViewModel extends BaseViewModel<Artist> {

    public void fetchArtist(String id) {
        dataRepository.getArtist(id, getDataListener());
    }

}
