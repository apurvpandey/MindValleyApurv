package com.pandey.apurv.mindvalleyhttplibrary;

import android.content.Context;


import com.pandey.apurv.mindvalleyhttplibrary.datatypes.BitmapType;
import com.pandey.apurv.mindvalleyhttplibrary.datatypes.JsonArrayType;
import com.pandey.apurv.mindvalleyhttplibrary.datatypes.JsonObjectType;
import com.pandey.apurv.mindvalleyhttplibrary.models.HeaderParameter;

import java.util.ArrayList;

import com.pandey.apurv.mindvalleyhttplibrary.models.RequestParameter;

/**
 * Created by Apurv Pandey on 18/06/17.
 * apurvpandey@rocektmail.com
 * Rewardz Pte Ltd.
 * Contact No. - +91-8377887369
 */
public class MindValleyHTTP {
    public static enum Method {
        GET,
        POST,
        PUT,
        DELETE
    }
    private Context mContext;
    private String mUrl;
    private Method mMethod;
    private static MindValleyHTTP instance = null;

    private ArrayList<RequestParameter> params=new ArrayList<>();
    private ArrayList<HeaderParameter> headers=new ArrayList<>();

    public static MindValleyHTTP from(Context c){
        return getInstance(c);
    }

    /**
     * Constructor
     * @param c it is the context
     */
    public MindValleyHTTP(Context c){
        this.mContext =c;
    }

    /**
     * Returns singleton instance for network call
     * @param context it is the context of activity
     * @author Sagar Devkota
     * @return Singleton instance
     */
    public static MindValleyHTTP getInstance(Context context) {
        if (context == null)
            throw new NullPointerException("Can not pass null context in to retrieve MindValleyHTTP instance");

        // lets make it thread safe
        synchronized (MindValleyHTTP.class) {
            if (instance == null)
                instance = new MindValleyHTTP(context);
        }

        return instance;
    }

    /**
     * Assigns Url to be loaded
     * @param method, url
     * @return MindValleyHTTP instance
     */
    public MindValleyHTTP load(Method m, String url){
        this.mUrl = url;
        this.mMethod = m;
        return this;
    }

    /**
     * Sets json datatype for request
     * @return Json Type
     */
    public JsonObjectType asJsonObject(){
        return new JsonObjectType(mMethod, mUrl, params, headers);
    }

    /**
     * Sets json datatype for request
     * @return Json Array Type
     */
    public JsonArrayType asJsonArray(){
        return new JsonArrayType(mMethod, mUrl, params, headers);
    }


    /**
     * Sets bitmap type for request
     * @return Bitmap Type
     */
    public BitmapType asBitmap(){
        return new BitmapType(mMethod, mUrl, params, headers);
    }



    /**
     * Sets request body parameters
     * @param key Parameter key
     * @param value Parameter value
     * @return MindValleyHTTP instance
     */
    public MindValleyHTTP setRequestParameter(String key, String value){
        RequestParameter param=new RequestParameter();
        param.setKey(key);
        param.setValue(value);
        this.params.add(param);
        return this;
    }


    /**
     * Sets request header parameters
     * @param key Parameter key
     * @param value Parameter value
     * @return MindValleyHTTP instance
     */
    public MindValleyHTTP setHeaderParameter(String key, String value){
        HeaderParameter param=new HeaderParameter();
        param.setKey(key);
        param.setValue(value);
        this.headers.add(param);
        return this;
    }



}
