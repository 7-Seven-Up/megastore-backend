package com._up.megastore.services.interfaces;

public interface IEmailService {

  void sendEmail(String to, String subject, String content);
}
