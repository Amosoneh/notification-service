package org.karrabo.notification.datas.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@Data @Builder
@Document
public class Email {
    @Id
    private String id;
    String to;
    String from;
    String subject;
    String content;
    LocalDateTime sentAt = LocalDateTime.now();
    LocalDateTime expiresAt = sentAt.plusMinutes(1);
    private Map< String, Object > model;
    private boolean isSent = false;
}
