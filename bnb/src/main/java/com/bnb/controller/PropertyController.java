package com.bnb.controller;

import com.bnb.payload.PropertyDto;
import com.bnb.service.PropertyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/property")
public class PropertyController {
    private PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @PostMapping("/addProperty")
    public ResponseEntity<?> addProperty(
            @RequestBody PropertyDto propertyDto,
            @RequestParam long cityId,
            @RequestParam long countryId
    ){
        PropertyDto dto=this.propertyService.addProperty(propertyDto, cityId, countryId);
        if(dto!=null){
            return new ResponseEntity<>(dto,HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Error while saving data", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PostMapping("/searchproperty")
    public ResponseEntity<?> searchProperty(
            @RequestParam String location
    )
    {
        List<PropertyDto> list=this.propertyService.searchProperty(location);
        if(list!=null){
            return new ResponseEntity<>(list,HttpStatus.OK);
        }
        return new ResponseEntity<>("No Record Found!",HttpStatus.NOT_FOUND);
    }


    @PostMapping("/updateProperty")
    public ResponseEntity<?> updateProperty(
            @RequestBody PropertyDto propertyDto,
            @RequestParam long id
    ){
        propertyDto.setId(id);
        PropertyDto resDto=this.propertyService.updateProperty(propertyDto);
        if(resDto!=null){
            return new ResponseEntity<>(resDto,HttpStatus.OK);
        }
        return new ResponseEntity<>("Error while updating data", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PostMapping("/deleteProperty")
    public ResponseEntity<?> deletePropertyById(
            @RequestParam long id
    ){
        PropertyDto resDto=this.propertyService.removePropertyById(id);
        if(resDto!=null){
            return new ResponseEntity<>(resDto,HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Error while Deleting Property.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllProperty(   )
    {
        List<PropertyDto> list=this.propertyService.getAll();
        if(list!=null){
            return new ResponseEntity<>(list,HttpStatus.OK);
        }
        return new ResponseEntity<>("No Property Found", HttpStatus.NOT_FOUND);
    }
    @PostMapping("/findPropertyById")
    public ResponseEntity<?> findPropertyById(
            @RequestParam long id
    ){
        PropertyDto resDto=this.propertyService.getPropertyById(id);
        if(resDto!=null){
            return new ResponseEntity<>(resDto,HttpStatus.OK);
        }
        return new ResponseEntity<>("Property Not Found", HttpStatus.NOT_FOUND);
    }
}
