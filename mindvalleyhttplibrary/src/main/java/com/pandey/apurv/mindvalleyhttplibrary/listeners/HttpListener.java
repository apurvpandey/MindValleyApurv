package com.pandey.apurv.mindvalleyhttplibrary.listeners;

/**
 * Created by Apurv Pandey on 18/06/17.
 * apurvpandey@rocektmail.com
 * Rewardz Pte Ltd.
 * Contact No. - +91-8377887369
 */
public interface HttpListener<T> {
    /**
     * callback for request start
     */
    public void onRequest();

    /**
     *  Callback which is fired after response
     * @param data  it holds respons data
     */
    public void onResponse(T data);

    public void onError();

    public void onCancel();

}
