package com._up.megastore.services.implementations;

import com._up.megastore.exception.custom_exceptions.EmailSendingException;
import com._up.megastore.services.interfaces.IEmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements IEmailService {

  private final JavaMailSender javaMailSender;

  public EmailService(JavaMailSender javaMailSender) {
    this.javaMailSender = javaMailSender;
  }

  @Override
  public void sendEmail(String to, String subject, String content) {
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

    try {
      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText(content, true);
      javaMailSender.send(mimeMessage);
    } catch (MessagingException exception) {
      throw new EmailSendingException("Failed to send e-mail: " + exception.getMessage());
    }
  }
}
