package com.practice.expandingtesting.config;

import lombok.NoArgsConstructor;
import org.aeonbits.owner.ConfigCache;

@NoArgsConstructor
public class ConfigurationManager {

    public static Configuration getConfiguration(){
        return ConfigCache.getOrCreate(Configuration.class);
    }
}
