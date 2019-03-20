package com.k2.musicdb.data;

import com.k2.musicdb.data.models.DataObject;

/**
 * Copyright (C) 2019 K2 CODEWORKS
 * All rights reserved
 *
 * @author Kislay
 * @since 2/4/2019
 */

public interface DataListener<T extends DataObject> {

    public void onSuccess(T t);

    public void onFailure(Throwable throwable);

}
