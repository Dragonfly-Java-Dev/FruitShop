package com.isuru.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailService {

    private static Properties appProps = new Properties();

    static {
        try (InputStream input = EmailService.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.err.println("Unable to find config.properties");
            } else {
                appProps.load(input);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Sends an email with the bill details.
     *
     * @param toAddress Recipient email address
     * @param subject   Email subject
     * @param body      Email body (the bill)
     * @throws MessagingException If sending fails
     */
    public void sendEmail(String toAddress, String subject, String body) throws MessagingException {
        final String username = appProps.getProperty("MAIL_USERNAME");
        final String password = appProps.getProperty("MAIL_PASSWORD");
        final String host = appProps.getProperty("MAIL_HOST");
        final String port = appProps.getProperty("MAIL_PORT");
        final String from = appProps.getProperty("MAIL_FROM_ADDRESS");

        if (username == null || password == null) {
            throw new MessagingException("Email configuration missing in config.properties");
        }

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        if ("465".equals(port)) {
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.socketFactory.port", port);
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        } else {
            props.put("mail.smtp.starttls.enable", "true");
        }

        props.put("mail.smtp.ssl.trust", host);

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        Message message = new MimeMessage(session);

        String fromName = appProps.getProperty("MAIL_FROM_NAME");
        if (fromName == null || fromName.isEmpty()) {
            fromName = "Dragonfly Fruit Shop"; // Default name if config is missing
        }

        try {
            message.setFrom(new InternetAddress(from, fromName));
        } catch (java.io.UnsupportedEncodingException e) {
            // If the name has weird characters, fall back to just the email
            message.setFrom(new InternetAddress(from));
        }
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
        message.setSubject(subject);
        message.setText(body);

        Transport.send(message);
    }
}
