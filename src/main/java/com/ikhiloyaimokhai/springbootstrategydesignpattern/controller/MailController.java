package com.ikhiloyaimokhai.springbootstrategydesignpattern.controller;

import com.ikhiloyaimokhai.springbootstrategydesignpattern.entity.User;
import com.ikhiloyaimokhai.springbootstrategydesignpattern.service.mail.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MailController {
    private final Logger log = LoggerFactory.getLogger(MailController.class);
    private MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/send-activation-email")
    @Transactional
    public void uploadInvestigation(@RequestBody User user) {
        log.info("REST request to send activation mail");
        //upload files
        mailService.sendActivationEmail(user);
    }
}
