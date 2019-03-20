package com.k2.musicdb.dagger;

import com.k2.musicdb.album.AlbumFragment;
import com.k2.musicdb.artist.ArtistFragment;
import com.k2.musicdb.dagger.FragmentScoped;
import com.k2.musicdb.search.SearchFragment;
import com.k2.musicdb.song.SongFragment;
import com.k2.musicdb.song.SongModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Copyright (C) 2019 K2 CODEWORKS
 * All rights reserved
 *
 * @author Kislay
 * @since 2/4/2019
 */

@Module
public abstract class MainModule {

    @FragmentScoped
    @ContributesAndroidInjector(modules = SongModule.class)
    abstract SongFragment songFragment();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract AlbumFragment albumFragment();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract ArtistFragment artistFragment();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract SearchFragment searchFragment();

}
