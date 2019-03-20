package com.k2.musicdb.common;

/**
 * Copyright (C) 2019 K2 CODEWORKS
 * All rights reserved
 *
 * @author Kislay
 * @since 2/2/2019
 */
public class Constants {

    public static final String FACEBOOK_URL_FORMAT = "https://www.facebook.com/%1$s";
    public static final String TWITTER_URL_FORMAT = "https://www.twitter.com/%1$s";
    public static final String INSTAGRAM_URL_FORMAT = "https://www.instagram.com/%1$s";
    public static final String CHROME_PACKAGE = "com.android.chrome";

    public static class Genius {
        public static final String BASE_URL = "https://api.genius.com/";
        public static final String AUTH_HEADER_KEY = "Authorization";
        private static final String AUTH_TOKEN_FORMAT = "Bearer %1$s";
        private static final String AUTH_TOKEN_VALUE = "INSERT_API_KEY_HERE";
        public static final String AUTH_TOKEN = String.format(AUTH_TOKEN_FORMAT, AUTH_TOKEN_VALUE);
    }

    public static final String MEDIA_SPOTIFY = "spotify";
    public static final String MEDIA_YOUTUBE = "youtube";
    public static final String DATE_FORMAT_DEFAULT = "yyyy-MM-dd";
    public static final String KEY_BACKDROP_URL = "backdrop_url";

}
