package com.pandey.apurv.mindvalleyhttplibrary.utils;

/**
 * Created by Apurv Pandey on 18/06/17.
 * apurvpandey@rocektmail.com
 * Rewardz Pte Ltd.
 * Contact No. - +91-8377887369
 */
public interface CacheManagerInterface<T> {
    public void addDataToCache(String key, T data);
    public void removeDataFromCache(String key);
    public T getDataFromCache(String key);
    public void evictUnused();

}
