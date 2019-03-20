package com.k2.musicdb;

import com.k2.musicdb.dagger.ActivityBindingModule;
import com.k2.musicdb.dagger.AppComponent;
import com.k2.musicdb.dagger.AppModule;
import com.k2.musicdb.data.DataModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Copyright (C) 2019 K2 CODEWORKS
 * All rights reserved
 *
 * @author Kislay
 * @since 2/28/2019
 */

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        ActivityBindingModule.class,
        DataModule.class
})
public interface TestAppComponent extends AppComponent {

    public void inject(MainActivity mainActivity);

}
