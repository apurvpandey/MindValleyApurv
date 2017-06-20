package com.pandey.apurv.mindvalley.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.pandey.apurv.mindavalley.R;
import com.pandey.apurv.mindvalley.constants.Constants;

import org.json.JSONArray;

import com.pandey.apurv.mindvalleyhttplibrary.MindValleyHTTP;
import com.pandey.apurv.mindvalleyhttplibrary.datatypes.Type;
import com.pandey.apurv.mindvalleyhttplibrary.listeners.HttpListener;
import com.pandey.apurv.mindvalleyhttplibrary.utils.CacheManager;

/**
 * Created by Apurv Pandey on 18/06/17.
 * apurvpandey@rocektmail.com
 * Rewardz Pte Ltd.
 * Contact No. - +91-8377887369
 */

public class MainActivity extends AppCompatActivity {
    final String TAG=getClass().getSimpleName();
    Type<Bitmap> bitmap;
    CacheManager<JSONArray> jsonCacheManager;
    CacheManager<Bitmap> bitmapCacheManager;
    ImageView imgData;
    ProgressBar imgLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imgData= (ImageView) findViewById(R.id.ivUser);
        imgLoading =(ProgressBar) findViewById(R.id.progressBar);
        imgLoading.setVisibility(View.GONE);


        jsonCacheManager=new CacheManager<JSONArray>(40*1024*1024);
        bitmapCacheManager= new CacheManager<>(40*1024*1024);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void btnImageRequestClicked(View v){

        bitmap=MindValleyHTTP
                .from(MainActivity.this)
                .load(MindValleyHTTP.Method.GET, Constants.SINGLE_IMAGE_URL)
                .asBitmap()
                .setCacheManager(bitmapCacheManager)
                .setCallback(new HttpListener<Bitmap>() {
                    @Override
                    public void onRequest() {
                        imgLoading.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onResponse(Bitmap data) {
                        if(data!=null){
                            imgData.setImageBitmap(data);
                            imgLoading.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError() {
                        imgLoading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancel() {
                        imgLoading.setVisibility(View.GONE);
                    }
                });
    }

    public void btnClearImage(View v) {
        imgData.setImageResource(R.drawable.placeholder);
    }

    public void btnClearCache(View v) {
        bitmapCacheManager.removeDataFromCache(Constants.SINGLE_IMAGE_URL);
    }

    public void btnLoadApiClicked(View v) {
        Intent intent= new Intent(this, UsersListActivity.class);
        startActivity(intent);
    }


}
