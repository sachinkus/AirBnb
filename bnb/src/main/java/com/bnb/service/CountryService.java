package com.bnb.service;

import com.bnb.entity.City;
import com.bnb.entity.Country;
import com.bnb.payload.CityDto;
import com.bnb.payload.CountryDto;
import com.bnb.repository.CityRepository;
import com.bnb.repository.CountryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CountryService {


    private CountryRepository countryRepository;
    private ModelMapper modelMapper;

    public CountryService(CountryRepository countryRepository, ModelMapper modelMapper) {
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
    }

    public CountryDto addCountry(CountryDto countryDto){
        Country country=mapToCountry(countryDto);
        Country resCountry=this.countryRepository.save(country);
        return mapToCountryDto(resCountry);
    }

    public CountryDto removeCountry(long id){
        Optional<Country> country=this.countryRepository.findById(id);
        if(country.isPresent()){
            this.countryRepository.delete(country.get());
        }
        return mapToCountryDto(country.get());
    }
    public CountryDto updateCountry(CountryDto countryDto){
        Country country=mapToCountry(countryDto);
        Country resCountry=this.countryRepository.save(country);
        return mapToCountryDto(resCountry);
    }
    public CountryDto getCountryById(long id){
        Optional<Country> country=this.countryRepository.findById(id);
        if(country.isPresent()){
            return mapToCountryDto(country.get());
        }
        return null;
    }
    public List<CountryDto> getAllCity(){
        List<Country> countries=this.countryRepository.findAll();
        if(countries==null || countries.isEmpty())
            return null;
        return countries.stream().map(x->mapToCountryDto(x)).collect(Collectors.toList());
    }

    public CountryDto mapToCountryDto(Country country){
        return modelMapper.map(country, CountryDto.class);
    }
    public Country mapToCountry(CountryDto countryDto){
        return modelMapper.map(countryDto,Country.class);
    }


}
