package com.vadim.sneakerstore.service.impl;

import com.vadim.sneakerstore.exception.MailSendingException;
import com.vadim.sneakerstore.service.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailSenderServiceImpl implements MailSenderService {

    private final JavaMailSender mailSender;

    public MailSenderServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendMessage(String email, String message, String subject) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

            mimeMessageHelper.setTo(email);
            mimeMessage.setText(message);
            mimeMessage.setSubject(subject);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new MailSendingException(e.getMessage(), e);
        }
    }
}
