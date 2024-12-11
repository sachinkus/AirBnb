package com.bnb.service;

import com.bnb.entity.City;
import com.bnb.payload.CityDto;
import com.bnb.repository.CityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CityService {

    private CityRepository cityRepository;
    private ModelMapper modelMapper;

    public CityService(CityRepository cityRepository, ModelMapper modelMapper) {
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
    }


    public CityDto addCity(CityDto cityDto){
        City city=mapToCity(cityDto);
        City resCity=this.cityRepository.save(city);
        return mapToCityDto(resCity);
    }

    public CityDto removeCity(long id){
        Optional<City> city=this.cityRepository.findById(id);
        if(city.isPresent()){
            this.cityRepository.delete(city.get());
        }
        return mapToCityDto(city.get());
    }
    public CityDto updateCity(CityDto cityDto){
        City city=mapToCity(cityDto);
        City resCity=this.cityRepository.save(city);
        return mapToCityDto(resCity);
    }
    public CityDto getCityById(long id){
        Optional<City> city=this.cityRepository.findById(id);
        if(city.isPresent()){
            return mapToCityDto(city.get());
        }
        return null;
    }
    public List<CityDto> getAllCity(){
        List<City> cities=this.cityRepository.findAll();
        if(cities==null || cities.size()==0)
               return null;
        return cities.stream().map(x->mapToCityDto(x)).collect(Collectors.toList());
    }

    public CityDto mapToCityDto(City city){
        return modelMapper.map(city, CityDto.class);
    }
    public City mapToCity(CityDto cityDto){
        return modelMapper.map(cityDto,City.class);
    }


}
