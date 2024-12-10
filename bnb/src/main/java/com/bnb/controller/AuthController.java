package com.bnb.controller;


import com.bnb.payload.ErrorDetails;
import com.bnb.payload.JWTToken;
import com.bnb.payload.LoginDto;
import com.bnb.payload.UserDto;
import com.bnb.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
     private UserService userService;

     @Autowired
     public AuthController(UserService userService) {
         this.userService = userService;
     }

    @PostMapping("/createuser")
    public ResponseEntity<?> createUser(
         @Valid @RequestBody UserDto user,
         BindingResult res
    ){
        if(res.hasErrors()){
            return new ResponseEntity<>(
                    new ErrorDetails(
                            new Date(),
                            "Error",
                            res.getFieldError().getDefaultMessage()
                    ),
                    HttpStatus.BAD_REQUEST
            );
        }
        UserDto opUsername=this.userService.findByusername(user.getUsername());
        if(opUsername!=null){
            return new ResponseEntity<>("Username already Present!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        UserDto opEmail=this.userService.findByEmail(user.getEmail());
        if(opEmail!=null){
            return new ResponseEntity<>("Email already Present!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        UserDto opMobile=this.userService.findByMobile(user.getMobile());
        if(opMobile!=null) {
            return new ResponseEntity<>("Mobile already Present!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        user.setRole("ROLE_USER");
      UserDto dto= this.userService.addUser(user);
        return new ResponseEntity<>(dto,HttpStatus.CREATED);
    }
    @PostMapping("/createpropertyowner")
    public ResponseEntity<?> createPropertyOwner(
            @Valid @RequestBody UserDto user,
            BindingResult res
    ){
        if(res.hasErrors()){
            return new ResponseEntity<>(
                    new ErrorDetails(
                            new Date(),
                            "Error",
                            res.getFieldError().getDefaultMessage()
                    ),
                    HttpStatus.BAD_REQUEST
            );
        }
        UserDto opUsername=this.userService.findByusername(user.getUsername());
        if(opUsername!=null){
            return new ResponseEntity<>("Username already Present!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        UserDto opEmail=this.userService.findByEmail(user.getEmail());
        if(opEmail!=null){
            return new ResponseEntity<>("Email already Present!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        UserDto opMobile=this.userService.findByMobile(user.getMobile());
        if(opMobile!=null) {
            return new ResponseEntity<>("Mobile already Present!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        user.setRole("ROLE_OWNER");
        UserDto dto= this.userService.addUser(user);
        return new ResponseEntity<>(dto,HttpStatus.CREATED);
    }

    @PostMapping("/createpropertymanager")
    public ResponseEntity<?> createPropertyManager(
            @Valid @RequestBody UserDto user,
            BindingResult res
    ){
        if(res.hasErrors()){
            return new ResponseEntity<>(
                    new ErrorDetails(
                            new Date(),
                            "Error",
                            res.getFieldError().getDefaultMessage()
                    ),
                    HttpStatus.BAD_REQUEST
            );
        }
        UserDto opUsername=this.userService.findByusername(user.getUsername());
        if(opUsername!=null){
            return new ResponseEntity<>("Username already Present!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        UserDto opEmail=this.userService.findByEmail(user.getEmail());
        if(opEmail!=null){
            return new ResponseEntity<>("Email already Present!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        UserDto opMobile=this.userService.findByMobile(user.getMobile());
        if(opMobile!=null) {
            return new ResponseEntity<>("Mobile already Present!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        user.setRole("ROLE_MANAGER");
        UserDto dto= this.userService.addUser(user);
        return new ResponseEntity<>(dto,HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> verifyLogin(
            @Valid   @RequestBody LoginDto loginDto,
            BindingResult res
            ){
         if(res.hasErrors()){
             return new ResponseEntity<>(
                     res.getFieldError().getDefaultMessage(),
                     HttpStatus.BAD_REQUEST
             );
         }
       String token=this.userService.verifyUser(loginDto);
        JWTToken jwtToken=new JWTToken();
        jwtToken.setToken(token);
        jwtToken.setType("jwt");
       if(token!=null){
           return new ResponseEntity<>(jwtToken, HttpStatus.OK);
       }
       return new ResponseEntity<>("Invalid Credentials!", HttpStatus.UNAUTHORIZED);
    }


}
