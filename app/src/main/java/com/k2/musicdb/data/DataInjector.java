package com.k2.musicdb.data;

/**
 * Copyright (C) 2019 K2 CODEWORKS
 * All rights reserved
 *
 * @author Kislay
 * @since 2/5/2019
 */

@Deprecated
public class DataInjector {

    private static DataComponent mComponent;

    public static DataComponent getComponent() {
        if (mComponent == null) {
            initComponent();
        }
        return mComponent;
    }

    private static void initComponent() {
        //mComponent = DaggerDataComponent.builder().build();
    }

}
