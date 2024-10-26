package com._up.megastore.utils;

import com._up.megastore.data.enums.State;
import com._up.megastore.data.model.Order;
import com._up.megastore.data.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class EmailBuilder {

    @Value("${frontend.url}")
    private String frontendURL;

    private final Map<State, String> STATE_MESSAGES = Map.of(
            State.IN_PROGRESS, " has been created.",
            State.FINISHED, " has been finished.",
            State.IN_DELIVERY, " is now in delivery.",
            State.DELIVERED, " has been delivered."
    );

    public String buildActivationEmailBody(User user, UUID activationToken) {
        String activationUrl = frontendURL + "/auth/activate?userId="
                + user.getUserId() + "&activationToken=" + activationToken;

        return "<table style='width:100%; height:100%;'>"
                + "<tr>"
                + "<td style='width:100%; height:100%; text-align:center; vertical-align:middle;'>"
                + "<div style='display:inline-block;'>"
                + "<h1>Welcome to Megastore</h1>"
                + "<h3>Hey " + user.getFullName() + "!</h3>"
                + "<h4>Thanks for joining us!</h4>"
                + "<p>Click the link below to activate your account</p>"
                + "<a href=\""
                + activationUrl
                + "\">Activate account</a>"
                + "</div>"
                + "</td>"
                + "</tr>"
                + "</table>";
    }

    public String buildRecoverPasswordEmail(User user) {
        String recoverPasswordURL = frontendURL + "/auth/recover-password?token=" + user.getRecoverPasswordToken();

        return "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "    <meta charset=\"UTF-8\">\n"
                + "    <title>Password Recovery</title>\n"
                + "    <style>\n"
                + "        body { font-family: Arial, sans-serif; }\n"
                + "        .container { width: 80%; margin: 0 auto; }\n"
                + "        .header { background-color: #f4f4f4; padding: 20px; text-align: center; }\n"
                + "        .content { padding: 20px; }\n"
                + "        .footer { background-color: #f4f4f4; padding: 10px; text-align: center; font-size: 12px; }\n"
                + "        .button {\n"
                + "            display: inline-block;\n"
                + "            padding: 10px 20px;\n"
                + "            font-size: 16px;\n"
                + "            color: #fff;\n"
                + "            background-color: #1ae866;\n"
                + "            text-decoration: none;\n"
                + "            border-radius: 5px;\n"
                + "            text-align: center;\n"
                + "        }\n"
                + "        .button:hover {\n"
                + "            background-color: #15d67c;\n"
                + "        }\n"
                + "    </style>\n"
                + "</head>\n"
                + "<body>\n"
                + "    <div class=\"container\">\n"
                + "        <div class=\"header\">\n"
                + "            <h1>Password Recovery</h1>\n"
                + "        </div>\n"
                + "        <div class=\"content\">\n"
                + "            <p>Hello " + user.getUsername() + ",</p>\n"
                + "            <p>We received a request to reset your password. Click the button below to create a new password:</p>\n"
                + "            <p style=\"display: flex; flex: content; justify-content: center;\"><a href=\" " + recoverPasswordURL + "\" class=\"button\">Reset Password</a></p>\n"
                + "            <p>If you did not request this change, please ignore this email.</p>\n"
                + "            <p>Best regards,<br>The Support Team</p>\n"
                + "        </div>\n"
                + "        <div class=\"footer\">\n"
                + "            <p>Megastore, Villa Maria, Cordoba, Argentina</p>\n"
                + "        </div>\n"
                + "    </div>\n"
                + "</body>\n"
                + "</html>";
    }

    public String buildWelcomeEmail(User user) {
        return "<table style='width:100%; height:100%;'>"
                + "<tr><td style='width:100%; height:100%; text-align:center; vertical-align:middle;'>"
                + "<div style='display:inline-block;'>"
                + "<h1>Welcome to Megastore</h1>"
                + "<h3>Hey " + user.getFullName() + "!</h3>"
                + "<h4>Your account has been activated.</h4>"
                + "<p>Enjoy shopping with us!</p>"
                + "</div></td></tr></table>";
    }

    public String buildNewActivationEmail(User user, UUID activationToken) {
        String activationUrl = frontendURL + "/auth/activate?userId="
                + user.getUserId() + "&activationToken=" + activationToken;

        return "<table style='width:100%; height:100%;'>"
                + "<tr>"
                + "<td style='width:100%; height:100%; text-align:center; vertical-align:middle;'>"
                + "<div style='display:inline-block;'>"
                + "<h2>Activation Token Request</h2>"
                + "<h3>Hello " + user.getFullName() + "!</h3>"
                + "<h4>We've received a request for a new activation token.</h4>"
                + "<p>If you requested this, click the link below to activate your account:</p>"
                + "<a href=\"" + activationUrl + "\">Activate account</a>"
                + "<p>If you did not request this, please ignore this email.</p>"
                + "</div>"
                + "</td>"
                + "</tr>"
                + "</table>";
    }

    public String buildOrderEmail(Order order, String subject) {
        String orderDetailUrl = frontendURL + "/orders/" + order.getOrderId();

        return "<table style='width:100%; height:100%;'>"
                + "<tr><td style='width:100%; height:100%; text-align:center; vertical-align:middle;'>"
                + "<div style='display:inline-block;'>"
                + "<h1>" + subject + "</h1>"
                + "<h3>Hey " + order.getUser().getFullName() + "!</h3>"
                + "<h4>Your order of the day " + order.getDate() + STATE_MESSAGES.get(order.getState()) + "</h4>"
                + "<p>If you want to see the details, please go to the link below:</p>"
                + "<a href=\"" + orderDetailUrl + "\">Order detail</a>"
                + "<p>Regards, megastore support team.</p>"
                + "</div></td></tr></table>";
    }
}