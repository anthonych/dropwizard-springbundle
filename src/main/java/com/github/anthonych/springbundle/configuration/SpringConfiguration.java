package com.github.anthonych.springbundle.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by anthonychen on 10/8/14.
 */
public class SpringConfiguration extends Configuration {

    @NotEmpty
    @JsonProperty
    protected String[] applicationContext;

    public String[] getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(String[] applicationContext) {
        this.applicationContext = applicationContext;
    }
}
