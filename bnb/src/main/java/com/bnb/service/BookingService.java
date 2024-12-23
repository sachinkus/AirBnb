package com.bnb.service;

import com.bnb.entity.Booking;
import com.bnb.entity.Property;
import com.bnb.entity.Rooms;
import com.bnb.payload.BookingDto;
import com.bnb.repository.BookingRepository;
import com.bnb.repository.PropertyRepository;
import com.bnb.repository.RoomsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    private ModelMapper modelMapper;
    private PropertyRepository propertyRepository;
    private BookingRepository bookingRepository;
    private RoomsRepository roomsRepository;


    public BookingService(ModelMapper modelMapper, PropertyRepository propertyRepository, BookingRepository bookingRepository, RoomsRepository roomsRepository) {
        this.modelMapper = modelMapper;
        this.propertyRepository = propertyRepository;
        this.bookingRepository = bookingRepository;
        this.roomsRepository = roomsRepository;
    }
    public Rooms roomsAvailable(
        long propertyId,
        String roomType,
        LocalDate date
    ){
        Rooms rooms=roomsRepository.findRoomsByPropertyAndTypeAndDate(propertyId,roomType,date);
        return rooms;
    }

    public BookingDto addBookings( long propertyId,BookingDto bookingDto){
        Optional<Property> opProperty=this.propertyRepository.findById(propertyId);
        if(opProperty.isPresent()){
            Property property=opProperty.get();
            Booking booking=mapToBooking(bookingDto);
           booking.setProperty(property);
           Booking resBooking=this.bookingRepository.save(booking);
           return mapToBookingDto(resBooking);
        }
        return null;
    }


    public BookingDto mapToBookingDto(Booking booking){
        return modelMapper.map(booking,BookingDto.class);
    }
    public Booking mapToBooking(BookingDto bookingDto)
    {
        return modelMapper.map(bookingDto, Booking.class);
    }

}
