package com.example.demo.service;

import com.example.demo.entity.UserActivity;
import com.example.demo.repository.UserActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class UserActivityService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserActivityRepository repository;

    public UserActivity saveLog(UserActivity userActivity) {
        return repository.save(userActivity);
    }

    public List<UserActivity> listAll() {
        return repository.findAll();
    }

    public List<Map> getTopArticles() {
        Aggregation aggregation = Aggregation.newAggregation(
//                Aggregation.match(Criteria.where("action").is("CLICK")), // 1. 只看點擊
                Aggregation.group("action").count().as("actionCount"), // 2. 按文章ID分組並計數
                Aggregation.sort(Sort.Direction.DESC, "actionCount"),    // 3. 排序
                Aggregation.limit(5)                                    // 4. 取前 5
        );

        AggregationResults<Map> results = mongoTemplate.aggregate(
                aggregation, "user_activity", Map.class
        );

        return results.getMappedResults();
    }
}
