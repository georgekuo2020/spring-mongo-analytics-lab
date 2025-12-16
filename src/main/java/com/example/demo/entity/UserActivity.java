package com.example.demo.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Document(collection = "user_activity")
public class UserActivity {

    @Id
    private String id;

    private String userId;

    private String action;      // CLICK, VIEW, LOGIN

    private String targetId;    // 文章 ID 或 頁面 ID

    @CreatedDate
    private LocalDateTime createdDate;

    // 存儲各種不同事件的特有資訊
    private Map<String, Object> properties;
}
