package com.example.demo.controller;

import com.example.demo.entity.UserActivity;
import com.example.demo.service.UserActivityService;
import com.example.demo.vo.web.SuccessfullyResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "使用者 tracking")
@RestController
@RequestMapping("/activity")
public class UserActivityController {

    @Autowired
    private UserActivityService userActivityService;

    @PostMapping("/save")
    public SuccessfullyResponse<String> recordActivity(@RequestBody UserActivity userActivity) {
        userActivityService.saveLog(userActivity);
        return new SuccessfullyResponse<>("ok", null);
    }

    @GetMapping("/list/all")
    public SuccessfullyResponse<?> recordActivity() {
        return new SuccessfullyResponse<>("ok", userActivityService.listAll());
    }
}
