package com.pandey.apurv.mindvalleyhttplibrary.tasks;

import android.net.Uri;
import android.os.AsyncTask;

import com.pandey.apurv.mindvalleyhttplibrary.utils.CacheManagerInterface;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.pandey.apurv.mindvalleyhttplibrary.MindValleyHTTP;
import com.pandey.apurv.mindvalleyhttplibrary.models.HeaderParameter;
import com.pandey.apurv.mindvalleyhttplibrary.models.RequestParameter;
import com.pandey.apurv.mindvalleyhttplibrary.models.Response;

/**
 * Created by Apurv Pandey on 18/06/17.
 * apurvpandey@rocektmail.com
 * Rewardz Pte Ltd.
 * Contact No. - +91-8377887369
 */
public abstract class BaseTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
    final String TAG = getClass().getSimpleName();
    protected CacheManagerInterface<Result> mCacheManager;
    static final int CONN_READ_TIMEOUT=10000;
    static final int CONN_TIMEOUT=15000;
    HttpURLConnection conn;

    // Given a URL, establishes an HttpUrlConnection and retrieves
    // the web page content as a InputStream, which it returns as
    // a string.
    protected Response makeRequest(String url, MindValleyHTTP.Method m, ArrayList<RequestParameter> params, ArrayList<HeaderParameter> headers) throws IOException {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.

        URL mUrl = new URL(url);
        conn = (HttpURLConnection) mUrl.openConnection();
        conn.setReadTimeout(CONN_READ_TIMEOUT /* milliseconds */);
        conn.setConnectTimeout(CONN_TIMEOUT /* milliseconds */);

        switch (m) {
            case GET:
                conn.setRequestMethod("GET");
                break;

            case POST:
                conn.setRequestMethod("POST");
                break;

            case PUT:
                conn.setRequestMethod("PUT");
                break;

            case DELETE:
                conn.setRequestMethod("DELETE");
                break;
        }


        conn.setDoInput(true);
        conn.setDoOutput(m != MindValleyHTTP.Method.GET);


        //write headers if any
        if (headers.size() > 0) {
            for (HeaderParameter header : headers) {
                conn.setRequestProperty(header.getKey(), header.getValue());
            }
        }


        Uri.Builder builder = new Uri.Builder();

        //write request parameters
        if (params.size() > 0) {
            for (RequestParameter itm : params) {
                builder.appendQueryParameter(itm.getKey(), itm.getValue());
            }

            String query = builder.build().getEncodedQuery();

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();
        }


        conn.connect();


        int response = conn.getResponseCode();
        is = conn.getInputStream();

        Response resp = new Response();
        resp.setCode(response);
        resp.setData(is);
        return resp;
    }


    public void setmCachemanager(CacheManagerInterface<Result> cachemanager){
        this.mCacheManager=cachemanager;
    }


}
