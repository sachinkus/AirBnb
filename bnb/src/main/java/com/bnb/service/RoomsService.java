package com.bnb.service;

import com.bnb.entity.Property;
import com.bnb.entity.Rooms;
import com.bnb.payload.RoomsDto;
import com.bnb.repository.PropertyRepository;
import com.bnb.repository.RoomsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomsService {
    private ModelMapper modelMapper;
    private RoomsRepository roomsRepository;
    private PropertyRepository propertyRepository;
    public RoomsService(ModelMapper modelMapper, RoomsRepository roomsRepository, PropertyRepository propertyRepository) {
        this.modelMapper = modelMapper;
        this.roomsRepository = roomsRepository;
        this.propertyRepository = propertyRepository;
    }

    public List<RoomsDto> addRoom(long propertyId,List<RoomsDto> list) {
        Optional<Property> opProperty = this.propertyRepository.findById(propertyId);
        if (opProperty.isPresent()) {
            List<Rooms> listRooms=new ArrayList<>();
            for(RoomsDto roomsDto:list){
                Rooms rooms = mapToRooms(roomsDto);
                rooms.setProperty(opProperty.get());
                listRooms.add(rooms);
            }
            List<Rooms> resRooms = this.roomsRepository.saveAll(listRooms);
            return resRooms.stream().map(x->mapToRoomsDto(x)).collect(Collectors.toList());
        }
        return null;
    }
    public RoomsDto removeRoom(long id) {
        Optional<Rooms> rooms=this.roomsRepository.findById(id);
        if(rooms.isPresent())
            this.roomsRepository.deleteById(id);
        return null;
    }

    public RoomsDto getRoomById(long id){
        Optional<Rooms> rooms=this.roomsRepository.findById(id);
        if(rooms.isPresent())
            return mapToRoomsDto(rooms.get());
        return null;
    }
    public List<RoomsDto> getAllRooms(long propertyId){
        Optional<Property> opProperty=this.propertyRepository.findById(propertyId);
        if(opProperty.isPresent()){
            List<Rooms> list=this.roomsRepository.findByProperty(opProperty.get());
            return list.stream().map(x->mapToRoomsDto(x)).collect(Collectors.toList());
        }
        return null;
    }


    public RoomsDto mapToRoomsDto(Rooms rooms){
        return this.modelMapper.map(rooms,RoomsDto.class);
    }
    public Rooms mapToRooms(RoomsDto roomsDto){
        return this.modelMapper.map(roomsDto, Rooms.class);
    }
}
