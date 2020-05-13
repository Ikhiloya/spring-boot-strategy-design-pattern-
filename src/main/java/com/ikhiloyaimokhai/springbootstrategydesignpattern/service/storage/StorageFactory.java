package com.ikhiloyaimokhai.springbootstrategydesignpattern.service.storage;

import com.ikhiloyaimokhai.springbootstrategydesignpattern.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Created by Ikhiloya Imokhai on 5/7/20.
 */

@Service
public class StorageFactory {
    private final Logger log = LoggerFactory.getLogger(StorageFactory.class);

    private final Environment environment;
    private final AwsStorageStrategy awsStorageStrategy;
    private final FileStorageStrategy fileStorageStrategy;

    public StorageFactory(Environment environment, AwsStorageStrategy awsStorageStrategy, FileStorageStrategy fileStorageStrategy) {
        this.environment = environment;
        this.awsStorageStrategy = awsStorageStrategy;
        this.fileStorageStrategy = fileStorageStrategy;
    }


    public StorageStrategy createStrategy() {
        String[] activeProfiles = environment.getActiveProfiles();
        log.info("Active profiles '{}'", Arrays.toString(activeProfiles));

        //Check if Active profiles contains "local" or "test"
        if (Arrays.stream(environment.getActiveProfiles()).anyMatch(
                env -> (env.equalsIgnoreCase(Constant.DEV_PROFILE)))) {
            return this.fileStorageStrategy;
        } else if (Arrays.stream(environment.getActiveProfiles()).anyMatch(
                env -> (env.equalsIgnoreCase(Constant.PROD_PROFILE)))) {
            return this.awsStorageStrategy;
        } else {
            return this.fileStorageStrategy;
        }
    }
}
