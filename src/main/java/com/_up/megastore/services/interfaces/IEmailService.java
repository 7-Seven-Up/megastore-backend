package com._up.megastore.services.interfaces;

import org.springframework.scheduling.annotation.Async;

public interface IEmailService {

  @Async
  void sendEmail(String to, String subject, String content);
}
