package com.k2.musicdb.dagger;

import android.content.Context;

import com.k2.musicdb.App;

import dagger.Binds;
import dagger.Module;

/**
 * Copyright (C) 2019 K2 CODEWORKS
 * All rights reserved
 *
 * @author Kislay
 * @since 2/4/2019
 */

@Module
public abstract class AppModule {

    @Binds
    abstract Context bindContext(App application);

}
