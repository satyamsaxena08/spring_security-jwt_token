package com.airbnb.controller;

import com.airbnb.dto.ReviewDto;
import com.airbnb.entity.Property;
import com.airbnb.entity.PropertyUser;
import com.airbnb.entity.Review;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.repository.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {

    private ReviewRepository reviewRepository;

    private PropertyRepository propertyRepository;
    public ReviewController(ReviewRepository reviewRepository, PropertyRepository propertyRepository) {
        this.reviewRepository = reviewRepository;
        this.propertyRepository = propertyRepository;
    }

    @PostMapping("/addReview/{propertyId}")
    public ResponseEntity<String> addReview(
            @PathVariable long propertyId,
            @RequestBody Review review,
            @AuthenticationPrincipal PropertyUser propertyUser
            ){

        Optional<Property> optionalProperty = propertyRepository.findById(propertyId);
        Property property = optionalProperty.get();
        review.setProperty(property);
        review.setPropertyUser(propertyUser);

        reviewRepository.save(review);

        return new ResponseEntity<>("Review added successfully", HttpStatus.CREATED);

    }
}
