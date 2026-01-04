package com.fitness.activityservice.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserValidationService {
    private final WebClient userServiceWebClient;

    public boolean validateUser(String userId) {
        try {
            return userServiceWebClient.get()
                .uri("/api/users/{userId}/validate", userId)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();    
        } catch (WebClientResponseException e) {
            if (HttpStatus.NOT_FOUND.equals(e.getStatusCode())) {
                throw new RuntimeException("User not found"+ userId);
            } else if(HttpStatus.BAD_REQUEST.equals(e.getStatusCode())){

                throw new RuntimeException("Invalid user ID: " + userId);

            }
        }
                return false;
        
    }
}
