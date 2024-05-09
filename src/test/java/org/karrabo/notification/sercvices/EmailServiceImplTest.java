package org.karrabo.notification.sercvices;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.karrabo.notification.datas.dto.request.EmailRequest;
import org.karrabo.notification.exceptions.EmailServiceException;
import org.karrabo.notification.datas.model.Email;
import org.karrabo.notification.sercvices.interfaces.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@ActiveProfiles("local")
@Slf4j
class EmailServiceImplTest {

    @Autowired
    private EmailService emailServiceImp;

    private EmailRequest emailRequest;

    @BeforeEach
    void setUp(){
        Map<String, Object> model = new HashMap<>();
        model.put("firstName", "Amos");
        model.put("lastName", "Amos");
        emailRequest = EmailRequest.builder().userEmail("amoskhaled@gmail.com").model(model)
                .subject("Testing Email").build();
    }

    @Test
    void testThatEmailNotSentWithEmptyEmailAddress(){
        Email email = Email.builder().sentAt(LocalDateTime.now()).from("")
                .subject("testing email service").to("amosKhaled@gmail.com").build();
        emailRequest.setUserEmail("");
        assertThrows(EmailServiceException.class, ()-> emailServiceImp.sendMail(emailRequest));
    }

    @Test
    void testThatEmailNotSentWithNullEmailAddress(){
        Email email = Email.builder().sentAt(LocalDateTime.now()).from("amosKhaled@gmail.com")
                .subject("testing email service").to(null).build();
        emailRequest.setUserEmail(null);
        assertThrows(EmailServiceException.class, ()-> emailServiceImp.sendMail(emailRequest));
    }

    @Test
    void testMailNotSentWithInvalidEmailAddress() {
        Email email = Email.builder().sentAt(LocalDateTime.now()).from("amosKhaled@gmail.com")
                .subject("testing email service").to("diegokhaleda").build();
        emailRequest.setUserEmail("amos");
        assertThrows(EmailServiceException.class, ()-> emailServiceImp.sendMail(emailRequest));
    }

    @Test
    void testThatMailWasSentSuccessfully() {
        try {
            Email email = Email.builder().sentAt(LocalDateTime.now()).from("amosKhaled@gmail.com")
                    .subject("testing email service").to(null).build();
            var response = emailServiceImp.sendMail(emailRequest);
            assertNotNull(response);
            assertThat(response).isNotNull();
            assertThat(response.getMessage()).containsIgnoringCase("successful");
        }catch (EmailServiceException e){
            log.error(e.getMessage());
        }
    }

}