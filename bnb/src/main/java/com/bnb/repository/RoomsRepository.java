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
    @Query("SELECT r FROM Rooms r WHERE r.property.id = :propertyId AND r.type = :roomType AND r.date = :date")
    Rooms findRoomsByPropertyAndTypeAndDate(
            @Param("propertyId") long propertyId,
            @Param("roomType") String roomType,
            @Param("date") LocalDate date
    );


    List<Rooms> findByProperty(Property property);
}