package com.k2.musicdb;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.k2.musicdb.common.Utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

/**
 * Copyright (C) 2019 K2 CODEWORKS
 * All rights reserved
 *
 * @author Kislay
 * @since 01/03/19
 */

@RunWith(MockitoJUnitRunner.class)
public class MockAndroidTests {

    @Mock
    Resources mockResources;

    @Before
    public void initMocks() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        displayMetrics.density = 2.625F;
        when(mockResources.getDisplayMetrics()).thenReturn(displayMetrics);
    }

    @Test
    public void runMockTests() {
        int pixels = Utils.dpToPixels(mockResources, 24);
        Assert.assertEquals(63, pixels);
    }

}
