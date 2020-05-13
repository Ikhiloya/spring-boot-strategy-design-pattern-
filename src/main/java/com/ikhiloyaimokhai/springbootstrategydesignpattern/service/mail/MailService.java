package com.ikhiloyaimokhai.springbootstrategydesignpattern.service.mail;

import com.ikhiloyaimokhai.springbootstrategydesignpattern.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * Created by Ikhiloya Imokhai on 5/7/20.
 */
@Service
public class MailService {
    private final Logger log = LoggerFactory.getLogger(MailService.class);

    private final MailStrategy mailStrategy;

    public MailService(MailFactory mailFactory) {
        this.mailStrategy = mailFactory.createStrategy();
    }

    public void sendActivationEmail(User user) {
        log.info("Sending activation email to '{}'", user.getEmail());
        mailStrategy.sendActivationEmail(user);
    }

    public void sendCreationEmail(User user) {
        log.info("Sending creation email to '{}'", user.getEmail());
        mailStrategy.sendCreationEmail(user);

    }

    public void sendPasswordResetMail(User user) {
        log.debug("Sending password reset email to '{}'", user.getEmail());
        mailStrategy.sendPasswordResetMail(user);
    }
}
