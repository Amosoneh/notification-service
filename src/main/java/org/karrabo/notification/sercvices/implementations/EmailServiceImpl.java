package org.karrabo.notification.sercvices.implementations;

import freemarker.template.Configuration;
import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.karrabo.notification.datas.dto.request.EmailRequest;
import org.karrabo.notification.datas.dto.response.EmailResponse;
import org.karrabo.notification.datas.repositories.EmailRepository;
import org.karrabo.notification.exceptions.EmailServiceException;
import org.karrabo.notification.datas.model.Email;
import org.karrabo.notification.sercvices.interfaces.EmailService;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    @Resource
    private Configuration fmConfiguration;

    private final EmailRepository emailRepository;
    @Override
    public EmailResponse sendMail(EmailRequest request) throws EmailServiceException {
        if(StringUtils.isEmpty(request.getUserEmail())){
            throw new EmailServiceException("Email must not be empty");
        }
        if(!EmailValidator.getInstance().isValid(request.getUserEmail())){
            throw new EmailServiceException("Email provided not a valid email");
        }
        Email mail = Email.builder().to(request.getUserEmail()).subject(request.getSubject())
                .from("amoskhaled@gmail.com").model(request.getModel()).build();

        saveEmail(mail);
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
            mimeMessageHelper.setTo(mail.getTo());
            mimeMessageHelper.setFrom(mail.getFrom());
            mimeMessageHelper.setSubject(mail.getSubject());
            mail.setContent(geContentFromTemplate(mail.getModel()));
            mimeMessageHelper.setText(mail.getContent(), true);

            javaMailSender.send(mimeMessage);
            mail.setSent(true);
            saveEmail(mail);
            return EmailResponse.builder().message(String.format("email sent to %s successfully", mail.getTo())).build();
        } catch (MessagingException | MailException e) {
            log.info("error: " + e.getMessage());
            e.printStackTrace();
            mail.setSent(false);
            saveEmail(mail);

        }
        return EmailResponse.builder().message(String.format("Email sent to %s unsuccessful", mail.getTo())).build();

    }


    private void saveEmail(Email email) {
        emailRepository.save(email);
    }

    private String geContentFromTemplate(Map<String, Object> model) {
        StringBuilder content = new StringBuilder();

        try {
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate("email.ftlh"), model));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

}
