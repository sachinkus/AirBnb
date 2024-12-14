package com.bnb.repository;

import com.bnb.entity.Property;
import com.bnb.entity.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RoomsRepository extends JpaRepository<Rooms, Long> {

    //Checking rooms are available or not available
//    @Query("Select r from Room r where r.property.id=:propertyId And r.type=:roomType And r.date=:date")
//    Rooms findByPropertyIdAndTypeAndDate(
//            @Param("propertyId") long propertyId,
//            @Param("roomType") String roomType,  // The parameter name should match the query
//            @Param("date") LocalDate date       // Make sure the type is correct
//    );
    List<Rooms> findByProperty(Property property);
}