package org.karrabo.notification.controllers;

import lombok.AllArgsConstructor;
import org.karrabo.notification.datas.dto.request.EmailRequest;
import org.karrabo.notification.datas.dto.response.EmailResponse;
import org.karrabo.notification.exceptions.EmailServiceException;
import org.karrabo.notification.sercvices.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/api/v1")
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/send-mail")
    public ResponseEntity<EmailResponse>sendMail(@RequestBody  EmailRequest emailRequest) {
        try {
            return ResponseEntity.ok(emailService.sendMail(emailRequest));
        }catch (EmailServiceException e){
            return ResponseEntity.badRequest().body(EmailResponse.builder().message(e.getMessage()).build());
        }
    }
}