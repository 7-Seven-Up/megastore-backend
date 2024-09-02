package com._up.megastore.services.implementations;

import com._up.megastore.data.model.User;
import com._up.megastore.services.interfaces.IMailSenderService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService implements IMailSenderService {

  private final JavaMailSender javaMailSender;

  public MailSenderService(JavaMailSender javaMailSender) {
    this.javaMailSender = javaMailSender;
  }

  @Override
  public void sendHtmlMail(String to, String subject, String htmlContent) {

    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

    try {
      helper.setText(htmlContent, true);
      helper.setTo(to);
      helper.setSubject(subject);

      javaMailSender.send(mimeMessage);
    } catch (MessagingException e) {
      throw new RuntimeException("Error while sending email", e);
    }
  }

  @Override
  public void sendWelcomeEmail(User user) {
    String htmlContent = "<table style='width:100%; height:100%;'>"
        + "<tr>"
        + "<td style='width:100%; height:100%; text-align:center; vertical-align:middle;'>"
        + "<div style='display:inline-block;'>"
        + "<h1>Welcome to Megastore</h1>"
        + "<h3>Hey " + user.getFullName() + "!</h3>"
        + "<h4>Thanks for joining us!</h4>"
        + "</div>"
        + "</td>"
        + "</tr>"
        + "</table>";

    sendHtmlMail(user.getEmail(), "Welcome to Megastore",
        htmlContent);
  }
}
