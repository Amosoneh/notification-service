package org.karrabo.notification.datas.dto.response;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class EmailResponse {
    private String message;
}
