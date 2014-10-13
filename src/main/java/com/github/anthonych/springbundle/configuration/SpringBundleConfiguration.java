package com.github.anthonych.springbundle.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by anthonychen on 10/8/14.
 */
public class SpringBundleConfiguration extends Configuration {

    @Valid
    @NotNull
    @JsonProperty("spring") // "spring" configurations from yaml.
    private SpringConfiguration springConfiguration = new SpringConfiguration();

    public SpringConfiguration getSpringConfiguration() {
        return springConfiguration;
    }
}
