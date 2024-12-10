package com.bnb.payload;


import jakarta.validation.constraints.*;

public class UserDto {

    private long id;
    @NotNull(message="Name cannot be Blank")
    @NotEmpty(message="Name cannot be Empty")
    @NotBlank(message="Name cannot be Blank")
    private String name;
    @NotNull(message="username cannot be Blank")
    @NotEmpty(message="username cannot be Empty")
    @NotBlank(message="username cannot be Blank")
    private String username;
    @Size(min=10,max=10,message="Mobile number must contain 10 digits.")
    private String mobile;
    @Size(min=6,message = "Password contain atleast 6 character")
    private String password;
    @Email(message="Enter a valid Email Address.")
    private String email;
    private String role;

    public String getRole(){
        return this.role;
    }
    public void setRole(String role) {
        this.role=role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
