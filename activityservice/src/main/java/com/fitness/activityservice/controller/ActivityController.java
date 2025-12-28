package com.fitness.activityservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/activities")
public class ActivityController {

    private ActivityService activityService;
    @PostMapping
    public ResponseEntity<ActivityResponseDTO> trackActivity(@RequestBody ActivityRequestDTO request ) {
        //TODO: process POST request
        
        return ResponseEntity.ok(activityService.trackActivity);
    }
    
}
