package com.bnb.repository;

import com.bnb.entity.Property;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import java.util.List;
@EnableJpaRepositories
public interface PropertyRepository extends JpaRepository<Property, Long> {
//    @Query("SELECT P from Property P join City C ON P.city=C.id where C.name=:city")
//    List<Property> searchProperty(@Param("city")  String city);

//    @Query("SELECT P from Property P join P.city C where C.name=:name")
//    List<Property> searchProperty(@Param("name") String name);
    @Query("SELECT P from Property P join P.city C join P.country Co where C.name=:name OR Co.name=:name")
    List<Property> searchProperty(@Param("name") String name);
}