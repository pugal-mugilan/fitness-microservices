package com.fitness.activityservice.service;

import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fitness.activityservice.dto.ActivityRequestDTO;
import com.fitness.activityservice.dto.ActivityResponseDTO;
import com.fitness.activityservice.model.Activity;
import com.fitness.activityservice.repository.ActivityRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ActivityService {
    private final ActivityRepository activityRepository;

    public ActivityResponseDTO trackActivity(ActivityRequestDTO request){
        Activity activity = Activity.builder()
                            .userId(request.getUserId())
                            .type(request.getType())
                            .duration(request.getDuration())
                            .caloriesBurned(request.getCaloriesBurned())
                            .startTime(request.getStartTime())
                            .additionalMetrics(request.getAdditionalMetrics())
                            .build();
        Activity savedActivity = activityRepository.save(activity);
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
