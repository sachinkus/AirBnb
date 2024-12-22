package com.bnb.repository;

import com.bnb.entity.Booking;
import com.bnb.entity.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface BookingRepository extends JpaRepository<Booking, Long> {
//    @Query("Select r from Room r where r.property.id=:propertyId And r.type=:roomType And r.date=:date")
//    Rooms findByPropertyIdAndTypeAndDate(
//            @Param("propertyId") long propertyId,
//            @Param("roomType") String roomType,
//            @Param("date") LocalDate date
//    );

}