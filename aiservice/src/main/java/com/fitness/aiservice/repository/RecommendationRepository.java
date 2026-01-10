package com.fitness.aiservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fitness.aiservice.model.Recommendation;

@Repository
public interface RecommendationRepository extends MongoRepository<Recommendation, String> {
    
}
