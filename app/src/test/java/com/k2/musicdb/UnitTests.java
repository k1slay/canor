package com.k2.musicdb;

import com.k2.musicdb.common.Utils;

import org.junit.Test;

import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UnitTests {

    @Test
    public void utilsTest() {
        assertTrue(Utils.isImageDefaultAvatar("https://assets.genius.com/images/default_avatar_300.png?1550878007"));
        String date = Utils.dateToLocalString("1992-11-24", new SimpleDateFormat("dd MMM yyyy"));
        assertEquals(date, "24 Nov 1992");
    }

}