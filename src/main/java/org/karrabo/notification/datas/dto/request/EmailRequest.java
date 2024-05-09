package org.karrabo.notification.datas.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data @Builder
public class EmailRequest {
    private String userEmail;
    private Map<String, Object> model;
    private String subject;
}
