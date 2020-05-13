package com.ikhiloyaimokhai.springbootstrategydesignpattern;

import com.ikhiloyaimokhai.springbootstrategydesignpattern.config.FileStorageProperties;
import com.ikhiloyaimokhai.springbootstrategydesignpattern.entity.User;
import com.ikhiloyaimokhai.springbootstrategydesignpattern.service.mail.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(FileStorageProperties.class)
public class SpringBootStrategyDesignPatternApplication implements CommandLineRunner {
    private final Logger log = LoggerFactory.getLogger(SpringBootStrategyDesignPatternApplication.class);
    private final MailService mailService;

    public SpringBootStrategyDesignPatternApplication(MailService mailService) {
        this.mailService = mailService;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootStrategyDesignPatternApplication.class, args);
    }


    @Override
    public void run(String... arg0) {
        log.info("*********************Application started*********************");
        User user = new User();
        user.setFirstName("Ikhiloya");
        user.setLastName("Imokhai");
        user.setEmail("imokhaiikhiloya@gmail.com");
        this.mailService.sendActivationEmail(user);
    }

}
