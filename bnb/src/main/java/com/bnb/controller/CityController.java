package com.bnb.controller;

import com.bnb.entity.City;
import com.bnb.payload.CityDto;
import com.bnb.service.CityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/city")
public class CityController {
    private CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping("/addCity")
    public ResponseEntity<?>  addCity(@RequestBody  CityDto cityDto){
        CityDto resDto=this.cityService.addCity(cityDto);
        if(resDto!=null){
            return new ResponseEntity<>(resDto,HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Error in Saving City", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/updateCity")
    public ResponseEntity<?>  updateCity(
            @RequestBody CityDto cityDto,
            @RequestParam long id
            ){
        cityDto.setId(id);
        CityDto resDto=this.cityService.addCity(cityDto);
        if(resDto!=null){
            return new ResponseEntity<>(resDto,HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Error in Updating City", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/removeCity")
    public ResponseEntity<?>  removeCity(
            @RequestParam long id
    ){
        CityDto resDto=this.cityService.removeCity(id);
        if(resDto!=null){
            return new ResponseEntity<>(resDto,HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Error in deleting City", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/getCityById")
    public ResponseEntity<?>  getCityById(
            @RequestParam long id
    ){
        CityDto resDto=this.cityService.getCityById(id);
        if(resDto!=null){
            return new ResponseEntity<>(resDto,HttpStatus.OK);
        }
        return new ResponseEntity<>("City Not Found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?>  getAllCities(){
        List<CityDto> cities=this.cityService.getAllCity();
        if(cities!=null){
            return new ResponseEntity<>(cities, HttpStatus.OK);
        }
        return new ResponseEntity<>("No City Found", HttpStatus.NOT_FOUND);

    }


}
