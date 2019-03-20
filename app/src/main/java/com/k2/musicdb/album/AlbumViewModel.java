package com.k2.musicdb.album;

import com.k2.musicdb.common.BaseViewModel;
import com.k2.musicdb.data.models.Album;

/**
 * Copyright (C) 2019 K2 CODEWORKS
 * All rights reserved
 *
 * @author Kislay
 * @since 2/6/2019
 */

public class AlbumViewModel extends BaseViewModel<Album> {

    public void fetchAlbum(String id) {
        dataRepository.getAlbum(id, getDataListener());
    }

}
