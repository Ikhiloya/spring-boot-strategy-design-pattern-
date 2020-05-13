package com.ikhiloyaimokhai.springbootstrategydesignpattern.service.mail;

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
public class MailFactory {
    private final Logger log = LoggerFactory.getLogger(MailFactory.class);

    private final Environment environment;
    private final GmailStrategy gmailStrategy;
    private final SendGridMailStrategy sendGridMailStrategy;

    public MailFactory(Environment environment, GmailStrategy gmailStrategy, SendGridMailStrategy sendGridMailStrategy) {
        this.environment = environment;
        this.gmailStrategy = gmailStrategy;
        this.sendGridMailStrategy = sendGridMailStrategy;
    }

    public MailStrategy createStrategy() {
        String[] activeProfiles = environment.getActiveProfiles();
        log.info("Active profiles '{}'", Arrays.toString(activeProfiles));

        //Check if Active profiles contains "local" or "test"
        if (Arrays.stream(environment.getActiveProfiles()).anyMatch(
                env -> (env.equalsIgnoreCase(Constant.DEV_PROFILE)))) {
            return this.gmailStrategy;
        } else if (Arrays.stream(environment.getActiveProfiles()).anyMatch(
                env -> (env.equalsIgnoreCase(Constant.PROD_PROFILE)))) {
            return this.sendGridMailStrategy;
        } else {
            return this.gmailStrategy;
        }
    }
}
