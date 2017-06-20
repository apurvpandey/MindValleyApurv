package com.pandey.apurv.mindvalleyhttplibrary.datatypes;

import com.pandey.apurv.mindvalleyhttplibrary.listeners.HttpListener;
import com.pandey.apurv.mindvalleyhttplibrary.utils.CacheManagerInterface;

/**
 * Created by Apurv Pandey on 18/06/17.
 * apurvpandey@rocektmail.com
 * Rewardz Pte Ltd.
 * Contact No. - +91-8377887369
 */
public abstract class Type<T> {
    public abstract Type setCacheManager(CacheManagerInterface<T> cacheManager);
    public abstract Type setCallback(HttpListener<T> callback);
    public abstract boolean cancel();
}
