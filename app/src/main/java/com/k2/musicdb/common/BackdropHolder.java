package com.k2.musicdb.common;

import androidx.annotation.DrawableRes;

/**
 * Copyright (C) 2019 K2 CODEWORKS
 * All rights reserved
 *
 * @author Kislay
 * @since 2/9/2019
 */

public interface BackdropHolder {

    public void setBackdrop(@DrawableRes int resource);

    public void setBackdrop(String imageUrl);

}
