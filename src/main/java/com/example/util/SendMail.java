package com.example.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class SendMail {
    @Autowired
    private JavaMailSender mailSender;

    public void sendHtmlEmail(String toEmail, String subject, String htmlContent) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(htmlContent, true); // `true` để gửi HTML

        mailSender.send(message);
    }
    public void sendPasword(String toEmail, String newPassword) {
        String subject = "Khôi phục mật khẩu";
        String body = "<h3>Chào bạn,</h3>"
                + "<p>Bạn vừa yêu cầu lấy lại mật khẩu. Dưới đây là mật khẩu mới của bạn:</p>"
                + "<p style='color: blue; font-weight: bold; font-size: 16px'>" + newPassword + "</p>"
                + "<p>Hãy đăng nhập và đổi lại mật khẩu ngay sau khi đăng nhập để bảo vệ tài khoản.</p>"
                + "<hr><small>Vui lòng không chia sẻ mật khẩu này với bất kỳ ai.</small>";

        try {
            sendHtmlEmail(toEmail, subject, body);
        } catch (MessagingException e) {
            // Log lỗi hoặc throw exception tuỳ bạn xử lý
            e.printStackTrace();
        }
    }
}
