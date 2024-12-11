package com.bnb.service;

import com.bnb.entity.City;
import com.bnb.entity.Country;
import com.bnb.entity.Property;
import com.bnb.payload.PropertyDto;
import com.bnb.repository.CityRepository;
import com.bnb.repository.CountryRepository;
import com.bnb.repository.PropertyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PropertyService {
   private PropertyRepository propertyRepository;
   private CityRepository cityRepository;
   private CountryRepository countryRepository;
   private ModelMapper modelMapper;
   public PropertyService(PropertyRepository propertyRepository, CityRepository cityRepository, CountryRepository countryRepository, ModelMapper modelMapper) {
       this.propertyRepository = propertyRepository;
       this.cityRepository = cityRepository;
       this.countryRepository = countryRepository;
       this.modelMapper = modelMapper;
   }

    public PropertyDto addProperty(PropertyDto dto, long cityId, long countryId){
        Optional<City> city=this.cityRepository.findById(cityId);
        if(!city.isPresent()){
            return null;
        }
        Optional<Country> country=this.countryRepository.findById(countryId);
        if(!country.isPresent()){
            return null;
        }
        dto.setCity(city.get());
        dto.setCountry(country.get());
        Property property=mapToProperty(dto);
       Property resProperty=this.propertyRepository.save(property);
       return mapToPropertyDto(resProperty);
    }

    public List<PropertyDto> searchProperty(String city){
       List<Property> list=this.propertyRepository.searchProperty(city);
       if(list==null || list.isEmpty())
           return null;
       return list.stream().map(x->mapToPropertyDto(x)).collect(Collectors.toList());
    }

    public PropertyDto updateProperty(PropertyDto dto){
       Property property=mapToProperty(dto);
       Property resProperty=this.propertyRepository.save(property);
       return mapToPropertyDto(resProperty);
    }

    public PropertyDto getPropertyById(long id){
       return mapToPropertyDto(this.propertyRepository.findById(id).orElse(null));
    }
    public PropertyDto removePropertyById(long id){
       Optional<Property> property=this.propertyRepository.findById(id);
       if(property.isPresent()){
           this.propertyRepository.deleteById(id);
           return mapToPropertyDto(property.get());
       }
       return null;
    }
    public List<PropertyDto> getAll(){
       List<Property> list=this.propertyRepository.findAll();
       if(list!=null && list.isEmpty()==false){
           return list.stream().map(x->mapToPropertyDto(x)).collect(Collectors.toList());
       }
       return null;
    }

    public PropertyDto mapToPropertyDto(Property property){
       return modelMapper.map(property,PropertyDto.class);
    }
    public Property mapToProperty(PropertyDto dto){
       return modelMapper.map(dto,Property.class);
    }
}
