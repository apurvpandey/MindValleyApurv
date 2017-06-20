package com.pandey.apurv.mindvalleyhttplibrary.datatypes;

import android.graphics.Bitmap;

import com.pandey.apurv.mindvalleyhttplibrary.utils.CacheManagerInterface;

import java.util.ArrayList;

import com.pandey.apurv.mindvalleyhttplibrary.MindValleyHTTP;
import com.pandey.apurv.mindvalleyhttplibrary.listeners.HttpListener;
import com.pandey.apurv.mindvalleyhttplibrary.models.HeaderParameter;
import com.pandey.apurv.mindvalleyhttplibrary.models.RequestParameter;
import com.pandey.apurv.mindvalleyhttplibrary.tasks.BitmapRequestTask;

/**
 * Created by Apurv Pandey on 18/06/17.
 * apurvpandey@rocektmail.com
 * Rewardz Pte Ltd.
 * Contact No. - +91-8377887369
 */
public class BitmapType extends Type<Bitmap> {
    private String mUrl;
    private HttpListener<Bitmap> mListener;
    private MindValleyHTTP.Method mMethod;
    private ArrayList<RequestParameter> params;
    private ArrayList<HeaderParameter> headers;
    private BitmapRequestTask mTask;
    private CacheManagerInterface<Bitmap> mCacheManager;

    /**
     * Constructor to load json datatyes
     * @param url
     * @param params
     * @param headers
     */
    public BitmapType(MindValleyHTTP.Method m, String url, ArrayList<RequestParameter> params, ArrayList<HeaderParameter> headers){
        this.mUrl=url;
        this.mMethod=m;
        this.headers=headers;
        this.params=params;
    }

    /**
     *
     * Sets future callback after Http response is received
     * @param listener
     */
    public BitmapType setCallback(HttpListener<Bitmap> listener){
        this.mListener=listener;
        mListener.onRequest();
        Bitmap data;
        if(mCacheManager!=null) {
            data = mCacheManager.getDataFromCache(mUrl);
            if (data != null) {
                mListener.onResponse(data);
                return this;
            }
        }

        mTask = new BitmapRequestTask(mMethod, mUrl, params, headers, mListener);
        mTask.setmCachemanager(mCacheManager);
        mTask.execute();
        return this;
    }

    /**
     * Cancels the current request
     * @return True if cancelled
     */
    public boolean cancel(){
        if(mTask!=null){
            mTask.cancel(true);
            if(mTask.isCancelled()) {
                mListener.onCancel();
                return true;
            }
            else
            {
                return false;
            }
        }

        return false;
    }

    /**
     * Lets depend on abstraction
     * Sets CacheManager for this
     * @param cache
     * @return JsonObjectType
     */
    public BitmapType setCacheManager(CacheManagerInterface<Bitmap> cache){
        this.mCacheManager=cache;
        return this;
    }


}
