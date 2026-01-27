package com.fitness.activityservice.service;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fitness.activityservice.dto.ActivityRequestDTO;
import com.fitness.activityservice.dto.ActivityResponseDTO;
import com.fitness.activityservice.model.Activity;
import com.fitness.activityservice.repository.ActivityRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final UserValidationService userValidationService;
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    public ActivityResponseDTO trackActivity(ActivityRequestDTO request){

        boolean isValidUser = userValidationService.validateUser(request.getUserId());
        if(!isValidUser){
            throw new RuntimeException("Invalid user ID: " + request.getUserId());
        }

        Activity activity = Activity.builder()
                            .userId(request.getUserId())
                            .type(request.getType())
                            .duration(request.getDuration())
                            .caloriesBurned(request.getCaloriesBurned())
                            .startTime(request.getStartTime())
                            .additionalMetrics(request.getAdditionalMetrics())
                            .build();
        Activity savedActivity = activityRepository.save(activity);
        // Publish to rabbitmq if needed
        try {
            rabbitTemplate.convertAndSend(exchange, routingKey, savedActivity);
        } catch (Exception e) {
            // Log the exception (using a logging framework in real applications)
            log.error("Failed to publish activity to RabbitMQ: " + e.getMessage());
        }
        return mapTResponseDTO(savedActivity);
    }

    private ActivityResponseDTO mapTResponseDTO(Activity activity){
        ActivityResponseDTO reponse = new ActivityResponseDTO();
        reponse.setId(activity.getId());
        reponse.setUserId(activity.getUserId());
        reponse.setType(activity.getType());
        reponse.setDuration(activity.getDuration());
        reponse.setCaloriesBurned(activity.getCaloriesBurned());
        reponse.setStartTime(activity.getStartTime());
        reponse.setAdditionalMetrics(activity.getAdditionalMetrics());
        reponse.setCreatedAt(activity.getCreatedAt());
        reponse.setUpdatedAt(activity.getUpdatedAt());
        return reponse;
    }

    public List<ActivityResponseDTO> getUserActivities(String userId) {
        List<Activity> activities = activityRepository.findByUserId(userId);

        return activities.stream()
                .map(this::mapTResponseDTO)
                .toList();
    }

    public ActivityResponseDTO getUserActivityById(String activityId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new RuntimeException("Activity not found"));
        return mapTResponseDTO(activity);
    }
}
