package com.fitness.aiservice.service;

import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import com.fitness.aiservice.dto.RecommendationResponseDTO;
import com.fitness.aiservice.model.Recommendation;
import com.fitness.aiservice.repository.RecommendationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    private final RecommendationRepository recommendationRepository;

    public List<Recommendation> getUserRecommendations(String userId){
        // Implementation logic to fetch and return recommendations for the user


        throw new UnsupportedOperationException("Unimplemented method 'getUserRecommendations'");
    }

    public List<Recommendation> getActivityRecommendations(String activityId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getActivityRecommendations'");
    }
}
