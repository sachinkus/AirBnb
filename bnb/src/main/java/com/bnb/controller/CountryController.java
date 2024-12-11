package com.bnb.controller;

import com.bnb.payload.CityDto;
import com.bnb.payload.CountryDto;
import com.bnb.service.CityService;
import com.bnb.service.CountryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/country")
public class CountryController {
    private CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService=countryService;
    }

    @PostMapping("/addCountry")
    public ResponseEntity<?> addCountry(@RequestBody CountryDto countryDto){
        CountryDto resDto=this.countryService.addCountry(countryDto);
        if(resDto!=null){
            return new ResponseEntity<>(resDto, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Error in Saving Country", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/updateCountry")
    public ResponseEntity<?>  updateCountry(
            @RequestBody CountryDto countryDto,
            @RequestParam long id
    ){
        countryDto.setId(id);
        CountryDto resDto=this.countryService.addCountry(countryDto);
        if(resDto!=null){
            return new ResponseEntity<>(resDto,HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Error in Updating Country", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/removeCountry")
    public ResponseEntity<?>  removeCountry(
            @RequestParam long id
    ){
        CountryDto resDto=this.countryService.removeCountry(id);
        if(resDto!=null){
            return new ResponseEntity<>(resDto,HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Error in deleting Country", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/getCountryById")
    public ResponseEntity<?>  getCountryById(
            @RequestParam long id
    ){
        CountryDto resDto=this.countryService.getCountryById(id);
        if(resDto!=null){
            return new ResponseEntity<>(resDto,HttpStatus.OK);
        }
        return new ResponseEntity<>("Country Not Found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?>  getAllCountries(){
        List<CountryDto> countries=this.countryService.getAllCity();
        if(countries!=null){
            return new ResponseEntity<>(countries, HttpStatus.OK);
        }
        return new ResponseEntity<>("No Country Found", HttpStatus.NOT_FOUND);

    }
}
