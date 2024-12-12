package com.bnb.controller;

import com.bnb.entity.Property;
import com.bnb.entity.Review;
import com.bnb.entity.User;
import com.bnb.payload.ReviewDto;
import com.bnb.service.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.oauth2.resourceserver.OpaqueTokenDsl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {
    private ReviewService reviewService;
    private final ModelMapper modelMapper;

    public ReviewController(ReviewService reviewService,
                            ModelMapper modelMapper) {
        this.reviewService = reviewService;
        this.modelMapper = modelMapper;
    }

    @RequestMapping("/createreview")
    public ResponseEntity<?> createReview(
            @RequestBody ReviewDto reviewDto,
            @AuthenticationPrincipal User user,
            @RequestParam long propertyId
            ){
        System.out.println(user.getName());
        System.out.println(user.getEmail());
        Optional<Review> opReview=this.reviewService.findByUserAndProperty(user, propertyId);
        if(opReview.isPresent()){
            return new ResponseEntity<>("Reviewed Already",HttpStatus.ALREADY_REPORTED);
        }
        ReviewDto resReviewDto=this.reviewService.addReview(reviewDto,user,propertyId);

        if(resReviewDto!=null){
            return new ResponseEntity<>(resReviewDto,HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Error while adding data!", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/userreviews")
    public ResponseEntity<?> getUserReviews(
            @AuthenticationPrincipal User user
    ){
        List<Review> list=this.reviewService.findReviewsByUser(user);
        if(list!=null && !list.isEmpty()){
            return new ResponseEntity<>(
                    list.stream().map(x->this.reviewService.mapToReviewDto(x)).collect(Collectors.toList()),
                    HttpStatus.OK
            );
        }
        return new ResponseEntity<>("Review Not Found!",HttpStatus.NOT_FOUND);
    }
}
