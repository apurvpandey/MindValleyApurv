package com.pandey.apurv.mindvalleyhttplibrary.models;

/**
 * Created by Apurv Pandey on 18/06/17.
 * apurvpandey@rocektmail.com
 * Rewardz Pte Ltd.
 * Contact No. - +91-8377887369
 */
public class HeaderParameter {
    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public HeaderParameter setKey(String key) {
        this.key = key;
        return this;
    }

    public String getValue() {
        return value;
    }

    public HeaderParameter setValue(String value) {
        this.value = value;
        return this;
    }
}
