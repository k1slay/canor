package com.k2.musicdb.common;

import com.k2.musicdb.data.models.DataObject;
import com.k2.musicdb.data.source.DataRepository;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.support.DaggerFragment;

/**
 * Copyright (C) 2019 K2 CODEWORKS
 * All rights reserved
 *
 * @author Kislay
 * @since 2/6/2019
 */

public abstract class BaseFragment<T extends BaseViewModel, M extends DataObject> extends DaggerFragment {

    private T viewModel;

    public M data;

    protected void initViewModel(Class<T> tClass, DataRepository dataRepository) {
        viewModel = ViewModelProviders.of(this).get(tClass);
        viewModel.setDataRepository(dataRepository);
    }

    protected T getViewModel() {
        if (viewModel == null)
            throw new IllegalStateException("Call initViewModel first!");
        return viewModel;
    }

    @Nullable
    protected <A extends FragmentActivity> A getParentActivity() {
        A activity = null;
        FragmentActivity a = getActivity();
        if (a != null)
            activity = (A) a;
        return activity;
    }

    public abstract String getTagId();

    public void replaceAll(FragmentManager fragmentManager, @IdRes int layout) {
        fragmentManager.beginTransaction().replace(layout, this, getTagId()).commit();
    }

    public boolean isAttached(FragmentManager fragmentManager) {
        return fragmentManager.findFragmentByTag(getTagId()) != null;
    }

    public void addToBackStack(FragmentManager fragmentManager, @IdRes int layout) {
        fragmentManager.beginTransaction().replace(layout, this, getTagId()).addToBackStack(getTag()).commit();
    }

    protected abstract void fetchData(String id);

    protected abstract void showData(M m);

    protected abstract void showError(Throwable error);

}
