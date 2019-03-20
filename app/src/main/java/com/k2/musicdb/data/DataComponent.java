package com.k2.musicdb.data;

import com.k2.musicdb.MainActivity;
import com.k2.musicdb.data.source.DataRepository;

/**
 * Copyright (C) 2019 K2 CODEWORKS
 * All rights reserved
 *
 * @author Kislay
 * @since 2/5/2019
 */

//@Component(modules = {DataModule.class})
@Deprecated
public interface DataComponent {

    DataRepository dataRepository();

    void inject(MainActivity activity);

}
