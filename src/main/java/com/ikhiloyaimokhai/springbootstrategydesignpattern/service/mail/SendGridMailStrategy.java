package com.ikhiloyaimokhai.springbootstrategydesignpattern.service.mail;

import com.ikhiloyaimokhai.springbootstrategydesignpattern.entity.User;
import com.sendgrid.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

/**
 * Created by Ikhiloya Imokhai on 5/7/20.
 */
@Service
public class SendGridMailStrategy implements MailStrategy {
    private final Logger log = LoggerFactory.getLogger(SendGridMailStrategy.class);

    private final SpringTemplateEngine templateEngine;

    private final Environment environment;
    private static final String USER = "user";

    private static final String BASE_URL = "baseUrl";

    public SendGridMailStrategy(SpringTemplateEngine templateEngine, Environment environment) {
        this.templateEngine = templateEngine;
        this.environment = environment;
    }

    @Override
    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.info("Send email to '{}' with subject '{}' and content={}", to, subject, content);
        String address = environment.getRequiredProperty("ADDRESS");
        Mail mail = new Mail(new Email(address), subject, new Email(to), new Content("text/html", content));
        mail.setReplyTo(new Email(address));
        Request request = new Request();
        Response response = null;
        SendGrid sendGrid = new SendGrid(environment.getRequiredProperty("SENDGRID_API_KEY"));
        try {
            request.method = Method.POST;
            request.endpoint = "mail/send";
            request.body = mail.build();
            response = sendGrid.api(request);

            log.info("Sent email to User '{}'", to);
            log.info("Email Response '{}'", response.toString());
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.warn("Email could not be sent to user '{}'", to, e);
            } else {
                log.warn("Email could not be sent to user '{}': {}", to, e.getMessage());

            }
        }
    }

    @Override
    @Async
    public void sendEmailFromTemplate(User user, String templateName, String titleKey) {
        Context context = new Context();
        String baseUrl = environment.getRequiredProperty("BASE_URL");
        String subject = environment.getRequiredProperty(titleKey);
        context.setVariable(BASE_URL, baseUrl);
        context.setVariable(USER, user);
        String html = templateEngine.process(templateName, context);
        sendEmail(user.getEmail(), subject, html, false, false);
    }

    @Override
    @Async
    public void sendActivationEmail(User user) {
        log.info("SendGridMailStrategy ==> Sending activation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/activationEmail", "EMAIL_ACTIVATION");
    }

    @Override
    @Async
    public void sendCreationEmail(User user) {
        log.info("SendGridMailStrategy ==> Sending creation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/creationEmail", "EMAIL_CREATION");
    }

    @Override
    @Async
    public void sendPasswordResetMail(User user) {
        log.info("SendGridMailStrategy ==> Sending password reset email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/passwordResetEmail", "EMAIL_RESET");
    }

}
