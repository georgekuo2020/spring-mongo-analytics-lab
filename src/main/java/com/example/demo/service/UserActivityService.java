package com.example.demo.service;

import com.example.demo.entity.UserActivity;
import com.example.demo.repository.UserActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserActivityService {

    @Autowired
    private UserActivityRepository repository;

    public UserActivity saveLog(UserActivity userActivity) {
        return repository.save(userActivity);
    }

    public List<UserActivity> listAll() {
        return repository.findAll();
    }
}
