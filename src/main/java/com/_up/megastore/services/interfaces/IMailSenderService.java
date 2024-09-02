package com._up.megastore.services.interfaces;

import com._up.megastore.data.model.User;
import org.springframework.scheduling.annotation.Async;

public interface IMailSenderService {

  @Async
  void sendHtmlMail(String to, String subject, String text);

  @Async
  void sendWelcomeEmail(User user);
}
