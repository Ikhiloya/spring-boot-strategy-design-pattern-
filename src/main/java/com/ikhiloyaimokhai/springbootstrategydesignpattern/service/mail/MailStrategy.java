package com.ikhiloyaimokhai.springbootstrategydesignpattern.service.mail;

import com.ikhiloyaimokhai.springbootstrategydesignpattern.entity.User;
import org.springframework.scheduling.annotation.Async;

/**
 * Created by Ikhiloya Imokhai on 5/7/20.
 */
public interface MailStrategy {
    @Async
    void sendActivationEmail(User user);

    @Async
    void sendCreationEmail(User user);

    @Async
    void sendPasswordResetMail(User user);
}
