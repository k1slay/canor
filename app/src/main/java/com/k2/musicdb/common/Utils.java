package com.k2.musicdb.common;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.util.TypedValue;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.browser.customtabs.CustomTabsIntent;

/**
 * Copyright (C) 2019 K2 CODEWORKS
 * All rights reserved
 *
 * @author Kislay
 * @since 2/10/2019
 */
public class Utils {

    public static String dateToLocalString(String dateString, DateFormat dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_DEFAULT);
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            // handle exception here !
        }
        dateString = dateFormat.format(date);
        return dateString;
    }

    public static DateFormat getLongDateFormat(Context context) {
        return android.text.format.DateFormat.getLongDateFormat(context);
    }

    public static int dpToPixels(Resources r, int dp) {
        float px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                r.getDisplayMetrics()
        );
        return (int) px;
    }

    public static boolean isImageDefaultAvatar(String url) {
        return url == null || url.isEmpty() || url.startsWith("https://assets.genius.com/images/default");
    }

    public static void openLink(Context context, String url) {
        openLink(context, url, false);
    }

    public static void openLink(Context context, String url, boolean useChrome) {
        startChromeTabs(context, url, useChrome);
    }

    private static boolean hasChromeCustomTabs(Context context) {
        boolean hasChrome = false;
        PackageInfo pi = getPackageInfo(context, Constants.CHROME_PACKAGE);
        if (pi != null && pi.activities != null) {
            for (ActivityInfo ai : pi.activities) {
                if (ai.name.equals("org.chromium.chrome.browser.customtabs.CustomTabActivity")) {
                    hasChrome = true;
                    break;
                }
            }
        }
        return hasChrome;
    }

    private static boolean isAppInstalled(Context context, String uri) {
        return getPackageInfo(context, uri) != null;
    }

    private static PackageInfo getPackageInfo(Context context, String appPackage) {
        PackageInfo pi = null;
        PackageManager pm = context.getPackageManager();
        try {
            pi = pm.getPackageInfo(appPackage, PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.getMessage();
        }
        return pi;
    }

    public static void startChromeTabs(Context context, String url, boolean forceChrome) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        Uri uri = Uri.parse(url);
        if (hasChromeCustomTabs(context) && forceChrome)
            customTabsIntent.intent.setPackage(Constants.CHROME_PACKAGE);
        try {
            customTabsIntent.launchUrl(context, uri);
        } catch (Exception e) {
            Toast.makeText(context, "Failed to launch browser", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }


}
