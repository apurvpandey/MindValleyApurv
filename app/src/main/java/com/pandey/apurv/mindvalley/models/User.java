package com.pandey.apurv.mindvalley.models;

/**
 * Created by Apurv Pandey on 19/06/17.
 * apurvpandey@rocektmail.com
 * Rewardz Pte Ltd.
 * Contact No. - +91-8377887369
 */
public class User {
    private String name;
    private String imageUrl;

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }


    public User name(String name) {
        this.name = name;
        return this;
    }

    public User imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
}
