package com.k2.musicdb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.k2.musicdb.common.BackdropHolder;
import com.k2.musicdb.common.BackdropImageStore;
import com.k2.musicdb.common.ImageUtils;
import com.k2.musicdb.common.Utils;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends DaggerAppCompatActivity implements BackdropHolder {

    private static final String IMAGE_STORE = "image_store";

    @BindView(R.id.back_drop)
    ImageView backDrop;

    CompositeDisposable subscriptions;
    private BackdropImageStore imageStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        subscriptions = new CompositeDisposable();
        if (savedInstanceState == null) {
            imageStore = new BackdropImageStore();
            setBackdrop(R.drawable.bg_default);
        } else {
            imageStore = savedInstanceState.getParcelable(IMAGE_STORE);
            if (imageStore == null) {
                imageStore = new BackdropImageStore();
            } else {
                imageStore.restore(this);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(IMAGE_STORE, imageStore);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void setBackdrop(String imageUrl) {
        if (!imageStore.needsChange(imageUrl))
            return;
        if (Utils.isImageDefaultAvatar(imageUrl)) {
            setBackdrop(R.drawable.bg_default);
            return;
        }
        imageStore.setImageUrl(imageUrl);
        subscriptions.add(ImageUtils.loadBitmapFromUrl(getApplicationContext(), imageUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bitmap -> setBackdrop(bitmap)));
    }

    @Override
    public void setBackdrop(@DrawableRes int resource) {
        if (!imageStore.needsChange(resource))
            return;
        imageStore.setImageRes(resource);
        Bitmap b = BitmapFactory.decodeResource(getResources(), resource);
        setBackdrop(b);
    }

    private void setBackdrop(final Bitmap b) {
        final Bitmap blurred = ImageUtils.createBitmapWithIntrinsicBlur(getApplicationContext(), b, 5);
        b.recycle();
        Animation fadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
        Animation fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        backDrop.startAnimation(fadeOut);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                backDrop.setVisibility(View.INVISIBLE);
                backDrop.setImageDrawable(null);
                backDrop.setVisibility(View.VISIBLE);
                Glide.with(getApplicationContext()).load(blurred).into(backDrop);
                backDrop.startAnimation(fadeIn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        subscriptions.clear();
    }

}
