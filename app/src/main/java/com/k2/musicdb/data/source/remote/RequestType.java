package com.k2.musicdb.data.source.remote;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.StringDef;

/**
 * Copyright (C) 2019 K2 CODEWORKS
 * All rights reserved
 *
 * @author Kislay
 * @since 2/4/2019
 */

@StringDef({
        RequestType.SONG,
        RequestType.ALBUM,
        RequestType.ARTIST,
        RequestType.SEARCH
})
@Retention(RetentionPolicy.SOURCE)
public @interface RequestType {

    public static String SONG = "songs";
    public static String ALBUM = "albums";
    public static String ARTIST = "artists";
    public static String SEARCH = "search";

}
