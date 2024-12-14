package com.bnb.controller;

import com.bnb.payload.MultiDateRoomsDTo;
import com.bnb.payload.RoomsDto;
import com.bnb.service.RoomsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomsController {
    private RoomsService roomsService;

    public RoomsController(RoomsService roomsService) {
        this.roomsService = roomsService;
    }

    @PostMapping("/addroom")
    public ResponseEntity<?> addRoom(
            @RequestParam long propertyId,
            @RequestBody MultiDateRoomsDTo mdrDto
    ){
        List<RoomsDto> list=new ArrayList<>();
        while(!mdrDto.getStartDate().isAfter(mdrDto.getEndDate())){
            RoomsDto roomsDto=new RoomsDto();
            roomsDto.setCount(mdrDto.getCount());
            roomsDto.setType(mdrDto.getType());
            roomsDto.setPrice(mdrDto.getPrice());
            roomsDto.setDate(mdrDto.getStartDate());
            list.add(roomsDto);
            mdrDto.setStartDate(mdrDto.getStartDate().plusDays(1));
        }
        List<RoomsDto> dtoList=this.roomsService.addRoom(propertyId,list);
       if(dtoList==null){
           return new ResponseEntity<>("Error while adding data.",HttpStatus.INTERNAL_SERVER_ERROR);
       }
       return new ResponseEntity<>(dtoList, HttpStatus.CREATED);
    }
    @PostMapping("/removeroom")
    public ResponseEntity<?> removeRoom(
         @RequestParam long id
    ){
        RoomsDto roomsDto=this.roomsService.removeRoom(id);
        if(roomsDto!=null){
            return new ResponseEntity<>(roomsDto,HttpStatus.OK);
        }
        return null;
    }

    @GetMapping("/allrooms")
    public ResponseEntity<?> getAllRooms(
            @RequestParam long propertyId
    ){
        List<RoomsDto> list=this.roomsService.getAllRooms(propertyId);
        if(list==null){
            return new ResponseEntity<>("No Records Found!",HttpStatus.NOT_FOUND);
        }
        return null;
    }
    @GetMapping("/roombyid")
    public ResponseEntity<?> getRoomById(
            @RequestParam long id
    ){
        RoomsDto roomsDto=this.roomsService.getRoomById(id);
        if(roomsDto==null){
            return new ResponseEntity<>("No record Found!",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(roomsDto,HttpStatus.OK);
    }




}
