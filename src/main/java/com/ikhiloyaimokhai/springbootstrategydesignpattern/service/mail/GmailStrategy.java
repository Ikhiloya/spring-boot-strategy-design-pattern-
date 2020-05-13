package com.ikhiloyaimokhai.springbootstrategydesignpattern.service.mail;

import com.ikhiloyaimokhai.springbootstrategydesignpattern.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * Created by Ikhiloya Imokhai on 5/7/20.
 */
@Service
public class GmailStrategy implements MailStrategy {
    private final Logger log = LoggerFactory.getLogger(GmailStrategy.class);

    private static final String USER = "user";

    private static final String BASE_URL = "baseUrl";


    private final JavaMailSender javaMailSender;

    private final MessageSource messageSource;

    private final SpringTemplateEngine templateEngine;

    private final Environment environment;


    public GmailStrategy(Environment environment, JavaMailSender javaMailSender,
                         MessageSource messageSource, SpringTemplateEngine templateEngine) {

        this.environment = environment;
        this.javaMailSender = javaMailSender;
        this.messageSource = messageSource;
        this.templateEngine = templateEngine;
    }


    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.info("Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
                isMultipart, isHtml, to, subject, content);

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
            message.setTo(to);
            message.setFrom(environment.getRequiredProperty("ADDRESS"));
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
            log.info("Sent email to User '{}'", to);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.warn("Email could not be sent to user '{}'", to, e);
            } else {
                log.warn("Email could not be sent to user '{}': {}", to, e.getMessage());
            }
        }
    }


    public void sendEmailFromTemplate(User user, String templateName, String titleKey) {
        Locale locale = Locale.getDefault();
        Context context = new Context();
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, environment.getRequiredProperty("BASE_URL"));
        String content = templateEngine.process(templateName, context);
        String subject = environment.getRequiredProperty(titleKey);
        sendEmail(user.getEmail(), subject, content, false, true);

    }

    @Override
    public void sendActivationEmail(User user) {
        log.info("GmailStrategy====> Sending activation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/activationEmail", "EMAIL_ACTIVATION");

    }

    @Override
    public void sendCreationEmail(User user) {
        log.info("GmailStrategy==> Sending creation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/creationEmail", "EMAIL_CREATION");

    }

    @Override
    public void sendPasswordResetMail(User user) {
        log.debug("GmailStrategy==> Sending password reset email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/passwordResetEmail", "EMAIL_RESET");
    }
}
