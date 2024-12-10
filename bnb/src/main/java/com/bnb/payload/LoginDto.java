package com.bnb.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class LoginDto {

    @NotEmpty(message = "User cannot be Empty!")
    @NotBlank(message="User cannot be Blank or contain only Blank Space!")
    @NotNull(message="User cannót be Null!")
    private String username;
    @Size(min=6,message = "Password must contain atleast 6 characters!")
    private String password;

    public LoginDto(String username,String password){
        this.username = username;
        this.password = password;
    }
    public LoginDto(){

    }
    public String getUsername(){
        return this.username;
    }
    public String getPassword(){
        return this.password;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public void setPassword(String password){
        this.password = password;
    }
}
