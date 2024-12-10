package com.bnb.service;


import com.bnb.entity.User;
import com.bnb.payload.LoginDto;
import com.bnb.payload.UserDto;
import com.bnb.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
       private ModelMapper modelMapper;
       private UserRepository userRepository;
       private JWTService jwtService;

       public UserService(UserRepository userRepository,ModelMapper modelMapper,JWTService jwtService){
           this.userRepository = userRepository;
           this.modelMapper = modelMapper;
           this.jwtService=jwtService;
       }
       public UserDto findByusername(String username){
           Optional<User> user=userRepository.findByUsername(username);
           if(user.isPresent()){
               return  mapToDto(user.get());
           }
           return null;
       }
       public UserDto findByEmail(String email){
           Optional<User> user=userRepository.findByEmail(email);
           if(user.isPresent()){
               return  mapToDto(user.get());
           }
           return null;
       }
       public UserDto findByMobile(String mobile){
           Optional<User> user=userRepository.findByMobile(mobile);
           if(user.isPresent()){
               return  mapToDto(user.get());
           }
           return null;
       }
       public UserDto addUser(UserDto dto){
           //Encrypting password
           dto.setPassword(
                   BCrypt.hashpw(
                           dto.getPassword(),
                           BCrypt.gensalt(10)
                   )
           );
           User user=mapToUser(dto);
           return mapToDto(this.userRepository.save(user));
       }
       public String verifyUser(LoginDto loginDto){
           Optional<User> user=userRepository.findByUsername(loginDto.getUsername());
            if(user.isPresent()){
                //Comparing Encrypted password
                if(BCrypt.checkpw(loginDto.getPassword(),user.get().getPassword())){
                    String result=jwtService.generateToken(loginDto.getUsername());
                    return result;
                }
            }
            return null;
       }


       public UserDto mapToDto(User user){
           UserDto dto=modelMapper.map(user,UserDto.class);
           return dto;
       }

       public  User mapToUser(UserDto dto){
           User user=modelMapper.map(dto,User.class);
           return user;
       }


}
