package com.bnb.repository;

import com.bnb.entity.Property;
import com.bnb.entity.Review;
import com.bnb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    //Select * from review where property="?" and user="?";
    @Query("Select r from Review r where r.property=:property and r.user=:user")
    Optional<Review> findByUserAndProperty(
            @Param("user") User user,
            @Param("property") Property property
            );

    //Select * from review where user=?;
    @Query("Select r from Review r where r.user=:user")
    List<Review> findReviewByUser(@Param("user")  User user);

}