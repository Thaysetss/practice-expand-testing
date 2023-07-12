package com.practice.expandingtesting.config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.LoadType;
import org.aeonbits.owner.Config.Sources;

@LoadPolicy(LoadType.MERGE)
@Sources("classpath:api.properties")
public interface Configuration extends Config {

    @Key("base.uri")
    String basePath();
}