package com.example.demo.scheduler;

import com.example.demo.entity.UserActivity;
import com.example.demo.service.UserActivityService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Log4j2
public class DataSimulator {

    @Autowired
    private UserActivityService userActivityService;

    // 用於遞增 userId
    private final AtomicInteger counter = new AtomicInteger(1);

    // 用於隨機選取 action
    private final String[] actions = {"CLICK", "VIEW", "LOGIN"};
    private final Random random = new Random();

    @Scheduled(fixedRate = 300000)
    public void simulateActivity() {
        UserActivity userActivity = new UserActivity();

        // 1. userId 遞增 (user_001, user_002...)
        userActivity.setUserId(String.format("user_%03d", counter.getAndIncrement()));

        // 2. action 從清單隨機選取
        userActivity.setAction(actions[random.nextInt(actions.length)]);

        // 3. targetId 使用 UUID (去除橫槓 -)
        userActivity.setTargetId(UUID.randomUUID().toString().replace("-", ""));

        // 4. 動態生成不一樣的 properties (模擬非結構化資料)
        Map<String, Object> dynamicProps = new HashMap<>();
        dynamicProps.put("browser", "Chrome");
        dynamicProps.put("device", random.nextBoolean() ? "Desktop" : "Mobile");

        // 隨機加入一些特有的欄位，模擬 Schema-less 特性
        int type = random.nextInt(3);
        if (type == 0) {
            dynamicProps.put("stay_duration", random.nextInt(1000)); // 停留時間
        } else if (type == 1) {
            dynamicProps.put("button_color", "blue"); // 按鈕顏色
            dynamicProps.put("is_first_visit", true);
        } else {
            dynamicProps.put("ip_address", "192.168.1." + random.nextInt(255));
        }

        userActivity.setProperties(dynamicProps);

        // 5. 執行儲存
        userActivityService.saveLog(userActivity);

        log.info(">>> [排程器] 已自動生成日誌: " + userActivity.getUserId() + " | Action: " + userActivity.getAction());
    }
}
