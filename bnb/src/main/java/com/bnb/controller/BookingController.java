package com.bnb.controller;


import com.bnb.entity.Property;
import com.bnb.entity.Rooms;
import com.bnb.entity.User;
import com.bnb.payload.BookingDto;
import com.bnb.payload.RoomsDto;
import com.bnb.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/booking")
// This controller is responsible for managing bookings. It includes endpoints for creating, updating, deleting, and retrieving bookings.
public class BookingController {
        private BookingService bookingService;
        private PDFService pdfService;
        private RoomsService roomsService;
        private EmailService emailService;
        private TwilioService twilioService;
        private WhatsappService whatsappService;

    public BookingController(BookingService bookingService, PDFService pdfService, RoomsService roomsService, EmailService emailService, TwilioService twilioService, WhatsappService whatsappService) {
        this.bookingService = bookingService;
        this.pdfService = pdfService;
        this.roomsService = roomsService;
        this.emailService = emailService;
        this.twilioService = twilioService;
        this.whatsappService = whatsappService;
    }

    @PostMapping("/createbooking")
        public ResponseEntity<?> createBooking(
                @RequestParam long propertyId,
                @RequestParam String roomType,
                @RequestBody BookingDto bookingDto,
                @AuthenticationPrincipal User user
                ){
         List<Rooms>  roomsList=new ArrayList<>();
        LocalDate currentDate=bookingDto.getCheckInDate();
            while(!currentDate.isAfter(bookingDto.getCheckOutDate())){
                Rooms rooms=this.bookingService.roomsAvailable(
                                propertyId,roomType,currentDate
                        );
                if(rooms==null || rooms.getCount()==0){
                    roomsList.clear();
                    return new ResponseEntity("No available rooms for this date.", HttpStatus.BAD_REQUEST);
                }
                currentDate=currentDate.plusDays(1);
                roomsList.add(rooms);
         }
            double total=0.0;
            for(Rooms r:roomsList){
                total+=r.getPrice();
            }
            bookingDto.setTotalPrice(total);
            bookingDto.setUser(user);
            bookingDto.setTypeOfRoom(roomType);
            BookingDto  resBookingDto=this.bookingService.addBookings(propertyId,bookingDto);
            if(resBookingDto!=null){
                for(Rooms rooms:roomsList){
                    rooms.setCount(rooms.getCount()-1);
                }
                List<RoomsDto> dtoList= roomsList.stream()
                        .map(x->this.roomsService.mapToRoomsDto(x))
                        .collect(Collectors.toList());
                this.roomsService.addRoom(resBookingDto.getProperty().getId(),dtoList);
                //Generating Pdf
                try {
                  String  emailText= pdfService.genertePdf(resBookingDto);
                    File file=new File("D:\\Bnb_pdf_collection"+"\\Booking "+resBookingDto.getId()+".pdf");
                    emailService.sendEmailWithAttachment(
                            resBookingDto.getEmail(),
                            "Booking Successfully Done!",
                           emailText,
                            file
                    );
                    //Whatsapp or Sms intrigration
//                    whatsappService.sendWhatsAppMessage(resBookingDto.getMobile(),emailText);
//                    String str=twilioService.sendSms(resBookingDto.getMobile(),emailText);
//                    System.out.println(str);
                }catch(Exception e){
                    e.printStackTrace();
                }finally {
                    return new ResponseEntity<>(resBookingDto, HttpStatus.CREATED);
                }
            }
                return new ResponseEntity("Failed to create booking.", HttpStatus.INTERNAL_SERVER_ERROR);

        }


}
