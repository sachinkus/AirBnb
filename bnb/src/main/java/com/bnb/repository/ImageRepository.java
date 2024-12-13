package com.bnb.repository;

import com.bnb.entity.Image;
import com.bnb.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findImagesByPropertyId(long propertyId);
    @Query("Select i from Image i where i.property=:property And i.url=:url")
    Optional<Image> findByPropertyAndUrl(@Param("property") Property property,@Param("url") String url);
}
