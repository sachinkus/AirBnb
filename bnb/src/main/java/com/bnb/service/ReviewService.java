package com.bnb.service;

import com.bnb.entity.Property;
import com.bnb.entity.Review;
import com.bnb.entity.User;
import com.bnb.payload.ReviewDto;
import com.bnb.repository.PropertyRepository;
import com.bnb.repository.ReviewRepository;
import jakarta.persistence.PrePersist;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    private ReviewRepository reviewRepository;
    private PropertyRepository propertyRepository;
    private ModelMapper modelMapper;

    public ReviewService(ReviewRepository reviewRepository, PropertyRepository propertyRepository, ModelMapper modelMapper) {
        this.reviewRepository = reviewRepository;
        this.propertyRepository = propertyRepository;
        this.modelMapper = modelMapper;
    }

    public ReviewDto addReview(ReviewDto reviewDto , User user ,long propertyId) {
        Optional<Property> opProperty = this.propertyRepository.findById(propertyId);
        if(opProperty.isPresent()) {
            Property property=opProperty.get();
            reviewDto.setProperty(property);
            reviewDto.setUser(user);
            Review review=mapToReview(reviewDto);
            Review resReview=this.reviewRepository.save(review);
            return mapToReviewDto(resReview);
        }
        return null;
    }
    public Optional<Review> findByUserAndProperty(User user,long propertyId){
        Property property=this.propertyRepository.findById(propertyId).orElse(null);
            return this.reviewRepository.findByUserAndProperty(user,property);
    }

    public List<Review>  findReviewsByUser(User user){
        return this.reviewRepository.findReviewByUser(user);
    }


    public ReviewDto mapToReviewDto(Review review){
         return modelMapper.map(review,ReviewDto.class);
    }
    public Review mapToReview(ReviewDto dto){
        return modelMapper.map(dto,Review.class);
    }

}
