package com.alibou.notification.config; // <--- DEBE EMPEZAR ASÍ

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration // <--- OBLIGATORIO: Le dice a Spring que aquí hay configuraciones
public class BeanConfig {

    @Bean // <--- OBLIGATORIO: Le dice a Spring que guarde este objeto para inyectarlo
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        // Tus credenciales de Mailhog / Mailpit
        mailSender.setHost("localhost");
        mailSender.setPort(1025);
        mailSender.setUsername("alibou");
        mailSender.setPassword("alibou");

        // Propiedades extra
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return mailSender; // Retornamos el objeto ya construido
    }
}