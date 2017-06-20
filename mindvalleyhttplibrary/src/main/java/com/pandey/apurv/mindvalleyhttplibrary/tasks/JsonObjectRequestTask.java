package com.pandey.apurv.mindvalleyhttplibrary.tasks;

import org.json.JSONObject;

import java.util.ArrayList;

import com.pandey.apurv.mindvalleyhttplibrary.MindValleyHTTP;
import com.pandey.apurv.mindvalleyhttplibrary.listeners.HttpListener;
import com.pandey.apurv.mindvalleyhttplibrary.models.HeaderParameter;
import com.pandey.apurv.mindvalleyhttplibrary.models.RequestParameter;
import com.pandey.apurv.mindvalleyhttplibrary.models.Response;

/**
 * Created by Apurv Pandey on 18/06/17.
 * apurvpandey@rocektmail.com
 * Rewardz Pte Ltd.
 * Contact No. - +91-8377887369
 */
public class JsonObjectRequestTask extends BaseTask<String, Void, JSONObject> {
    private MindValleyHTTP.Method mMethod;
    private String mUrl;
    private HttpListener<JSONObject> mCallback;
    private boolean error=false;
    private ArrayList<RequestParameter> params;
    private ArrayList<HeaderParameter> headers;

    public JsonObjectRequestTask(MindValleyHTTP.Method method, String url, ArrayList<RequestParameter> params, ArrayList<HeaderParameter> headers, HttpListener<JSONObject> callback){
        this.mUrl=url;
        this.mMethod=method;
        this.mCallback=callback;
        this.params=params;
        this.headers=headers;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(String... urls) {
        try {
            Response response=makeRequest(mUrl,mMethod,params,headers);
            JSONObject json= new JSONObject(response.getDataAsString());
            if(this.mCacheManager!=null){
                if(this.mCacheManager.getDataFromCache(mUrl)==null)
                    this.mCacheManager.addDataToCache(mUrl,json);
            }
            return json;

        } catch (Exception e) {
            e.printStackTrace();
            error=true;
        }

        return null;
    }

    @Override
    protected void onPostExecute(JSONObject data) {
        super.onPostExecute(data);
        if(!error)
            this.mCallback.onResponse(data);
        else
            this.mCallback.onError();
    }

    /**
     * Sometimes users may cancel at almost end, so lets remove if data is in cache
     */
    @Override
    protected void onCancelled() {
        super.onCancelled();
        if(this.mCacheManager!=null){
            this.mCacheManager.removeDataFromCache(mUrl);
        }
    }
}
