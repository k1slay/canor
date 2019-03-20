package com.k2.musicdb.song;

import com.k2.musicdb.common.BaseViewModel;
import com.k2.musicdb.data.models.Song;

/**
 * Copyright (C) 2019 K2 CODEWORKS
 * All rights reserved
 *
 * @author Kislay
 * @since 2/5/2019
 */
public class SongViewModel extends BaseViewModel<Song> {

    public void fetchSong(String id) {
        dataRepository.getSong(id, getDataListener());
    }

}
