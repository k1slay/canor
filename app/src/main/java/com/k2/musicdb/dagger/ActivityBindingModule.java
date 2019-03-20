package com.k2.musicdb.dagger;

import com.k2.musicdb.MainActivity;

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
public abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = {MainModule.class})
    abstract MainActivity bindMainActivity();

}
