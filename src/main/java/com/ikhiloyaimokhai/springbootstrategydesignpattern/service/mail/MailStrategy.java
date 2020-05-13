package com.ikhiloyaimokhai.springbootstrategydesignpattern.service.mail;

import com.ikhiloyaimokhai.springbootstrategydesignpattern.entity.User;
import org.springframework.scheduling.annotation.Async;

/**
 * Created by Ikhiloya Imokhai on 5/7/20.
 */
public interface MailStrategy {
    @Async
    void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml);

    @Async
    public void sendEmailFromTemplate(User user, String templateName, String titleKey);

    @Async
    public void sendActivationEmail(User user);

    @Async
    public void sendCreationEmail(User user);

    @Async
    public void sendPasswordResetMail(User user);
}
