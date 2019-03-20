package com.k2.musicdb;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


/**
 * Copyright (C) 2019 K2 CODEWORKS
 * All rights reserved
 *
 * @author Kislay
 * @since 2/28/2019
 */

@RunWith(MockitoJUnitRunner.class)
public class MockTests {

    private static final String APP_NAME = "Canor";

    @Mock
    Context mockContext;

    @Before
    public void initMocks() {
        when(mockContext.getString(R.string.app_name)).thenReturn(APP_NAME);
    }

    @Test
    public void runMockTests() {
        // Verify app name
        String appName = mockContext.getString(R.string.app_name);
        assertEquals(appName, APP_NAME);
    }

}
