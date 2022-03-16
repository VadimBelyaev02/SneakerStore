package com.vadim.sneakerstore.service;

public interface MailSenderService {

    void sendMessage(String email, String message, String subject);
}
