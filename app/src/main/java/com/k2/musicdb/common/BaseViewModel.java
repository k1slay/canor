package com.k2.musicdb.common;

import com.k2.musicdb.data.DataListener;
import com.k2.musicdb.data.models.DataObject;
import com.k2.musicdb.data.source.DataRepository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Copyright (C) 2019 K2 CODEWORKS
 * All rights reserved
 *
 * @author Kislay
 * @since 2/4/2019
 */

public abstract class BaseViewModel<T extends DataObject> extends ViewModel {

    protected DataRepository dataRepository;
    private final MutableLiveData<T> liveData = new MutableLiveData<>();
    private final MutableLiveData<Throwable> liveErrorData = new MutableLiveData<>();

    protected void setDataRepository(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    private DataListener<T> dataListener = new DataListener<T>() {
        @Override
        public void onSuccess(T t) {
            liveData.postValue(t);
        }

        @Override
        public void onFailure(Throwable throwable) {
            liveErrorData.postValue(throwable);
        }
    };

    protected DataListener<T> getDataListener() {
        return dataListener;
    }

    public LiveData<Throwable> getErrorObserver() {
        return liveErrorData;
    }

    public LiveData<T> getDataObserver() {
        return liveData;
    }

}
