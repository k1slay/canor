package com.k2.musicdb.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import io.reactivex.Observable;

/**
 * Copyright (C) 2019 K2 CODEWORKS
 * All rights reserved
 *
 * @author Kislay
 * @since 2/9/2019
 */

public class ImageUtils {

    /**
     * @param src    Bitmap
     * @param radius The blur radius
     * @return Get a blurred version of a bitmap
     */
    public static Bitmap createBitmapWithIntrinsicBlur(Context context, Bitmap src, float radius) {

        //Radius range (0 < r <= 25)
        if (radius <= 0) {
            radius = 0.1f;
        } else if (radius > 25) {
            radius = 25.0f;
        }

        Bitmap bitmap = Bitmap.createBitmap(
                src.getWidth(), src.getHeight(),
                Bitmap.Config.ARGB_8888);

        RenderScript renderScript = RenderScript.create(context);

        Allocation blurInput = Allocation.createFromBitmap(renderScript, src);
        Allocation blurOutput = Allocation.createFromBitmap(renderScript, bitmap);

        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(renderScript,
                Element.U8_4(renderScript));
        blur.setInput(blurInput);
        blur.setRadius(radius);
        blur.forEach(blurOutput);

        blurOutput.copyTo(bitmap);
        renderScript.destroy();
        return bitmap;
    }

    public static Observable<Bitmap> loadBitmapFromUrl(Context context, final String imageUrl) {
        return Observable.create(emitter -> {
            Bitmap b = Glide.with(context).asBitmap().load(imageUrl).submit().get();
            if (!emitter.isDisposed()) {
                emitter.onNext(b);
                emitter.onComplete();
            }
        });
    }

    public static void loadImageIntoView(ImageView imageView, String url, int placeHolder) {
        loadImageIntoView(imageView, url, -1, placeHolder);
    }

    public static void loadImageIntoView(ImageView imageView, int resource) {
        loadImageIntoView(imageView, null, resource, -1);
    }

    private static void loadImageIntoView(ImageView imageView, String url, int resource, int placeHolder) {
        GlideRequests glideRequests = GlideApp.with(imageView);
        GlideRequest<Drawable> glideRequest = null;
        if (url != null)
            glideRequest = glideRequests.load(url);
        else if (resource > -1)
            glideRequest = glideRequests.load(resource);
        if (glideRequest != null) {
            if (placeHolder > -1) {
                glideRequest = glideRequest.error(placeHolder);
                glideRequest = glideRequest.placeholder(placeHolder);
            }
            glideRequest.into(imageView);
        }
    }

}
