package com.example.dbtest.domain.service;

import com.example.dbtest.domain.entity.UserInfo;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class SendVerifyMailServiceImpl implements SendVerifyMailService {
    private final JavaMailSender mailSender;

    public SendVerifyMailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void execute(UserInfo userInfo) {
        mailSender.send(mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.name());
            helper.setFrom("XXX Symtem <info@hoge.info>");
            helper.setTo(userInfo.getEmail());
            helper.setSubject("Please verify your email.");

            String text = "<html><body><div>Hi " + userInfo.getUsername() + ", welcome to XXX System!</div>" +
                    "<div><a href='http://localhost:8080/register/verify?id=" + userInfo.getTempkey() + "'>Verify</a></div>" +
                    "</body></html>";

            helper.setText(text, true);
        });
    }

}
