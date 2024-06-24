package org.karrabo.notification.listeners;

import lombok.RequiredArgsConstructor;
import org.apache.pulsar.client.api.schema.GenericRecord;
import org.apache.pulsar.common.schema.SchemaType;
import org.karrabo.notification.datas.dto.request.EmailRequest;
import org.karrabo.notification.exceptions.EmailServiceException;
import org.karrabo.notification.sercvices.email.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.pulsar.annotation.PulsarListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class NotificationServiceListener {
    private static final Logger log = LoggerFactory.getLogger(NotificationServiceListener.class);
    private final EmailService emailService;

    @PulsarListener(topics = "user-welcome-topic", subscriptionName = "user-welcome-subscription", schemaType = SchemaType.AUTO_CONSUME)
    public void handleOrderConfirmation(GenericRecord record) {
        try {
            String email = record.getField("email").toString();

            String subject = "Welcome Message";
            Map<String, String> model = new HashMap<>();
            EmailRequest request = EmailRequest.builder()
                    .model(model).subject(subject).userEmail(email).build();
            emailService.sendMail(request);
        }catch (EmailServiceException e){
            log.error(e.getMessage());
        }
    }
}
