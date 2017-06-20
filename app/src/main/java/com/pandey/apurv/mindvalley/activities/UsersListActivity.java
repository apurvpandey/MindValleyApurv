package com.pandey.apurv.mindvalley.activities;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pandey.apurv.mindavalley.R;
import com.pandey.apurv.mindvalley.constants.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.pandey.apurv.mindvalleyhttplibrary.MindValleyHTTP;
import com.pandey.apurv.mindvalleyhttplibrary.listeners.HttpListener;
import com.pandey.apurv.mindvalleyhttplibrary.utils.CacheManager;
import com.pandey.apurv.mindvalley.adapters.UserListAdapter;
import com.pandey.apurv.mindvalley.models.User;

/**
 * Created by Apurv Pandey on 18/06/17.
 * apurvpandey@rocektmail.com
 * Rewardz Pte Ltd.
 * Contact No. - +91-8377887369
 */

public class UsersListActivity extends AppCompatActivity {

    RecyclerView list;
    UserListAdapter adapter;
    CacheManager<JSONArray> jsonArrayCacheManager;

    private SwipeRefreshLayout swipeRefreshLayout;
    private Handler handler = new Handler();
    boolean refresh=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        list=(RecyclerView) findViewById(R.id.recyclerViewUserList);
        jsonArrayCacheManager=new CacheManager<>(40*1024*1024); // 40mb

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(llm);

        swipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        adapter=new UserListAdapter(UsersListActivity.this);



        populateItems();





        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                populateItems();
            }
        });

    }

    /**
     * Calls API and populate recyclerview
     * @param v View that is clicked, here Buttton
     */
    public void btnLoadListClicked(View v){
        populateItems();
    }

    void populateItems(){
        MindValleyHTTP
                .from(this)
                .load(MindValleyHTTP.Method.GET, Constants.USERS_LIST_URL)
                .asJsonArray()
                .setCacheManager(jsonArrayCacheManager)
                .setCallback(new HttpListener<JSONArray>() {
                    @Override
                    public void onRequest() {
                        startSync();
                    }

                    @Override
                    public void onResponse(JSONArray data) {
                        if(data!=null){
                            List<User> users=new ArrayList<User>();

                            for(int i=0; i<data.length(); i++){
                                try {
                                    JSONObject jUser=data.getJSONObject(i);
                                    users.add(
                                            new User()
                                                    .name(jUser.getJSONObject("user").get("name").toString())
                                                    .imageUrl(jUser.getJSONObject("user").getJSONObject("profile_image").get("small").toString())
                                    );

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                            adapter.setItems(users);
                            list.setAdapter(adapter);
                            refresh=false;
                        }
                    }

                    @Override
                    public void onError() {
                       refresh=false;
                    }

                    @Override
                    public void onCancel() {
                        refresh=false;
                    }
                });

    }

    public void btnClearListClicked(View v){
        adapter.clear();
    }

    public void startSync(){
        refresh=true;
        if(!swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(true);
        }
        handler.post(refreshing);
        //Toast.makeText(getActivity(),"Sagar ",Toast.LENGTH_LONG).show();
    }

    private final Runnable refreshing = new Runnable(){
        public void run(){
            try {
                if(isRefreshing()){
                    // RE-Run after 1 Second
                    handler.postDelayed(this, 1000);
                }else{
                    // Stop the animation once we are done fetching data.
                    swipeRefreshLayout.setRefreshing(false);
                    /**
                     * You can add code to update your list with the new data.
                     **/
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    boolean isRefreshing(){
        return  refresh;
    }


}
