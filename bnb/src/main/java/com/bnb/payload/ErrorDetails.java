package com.bnb.payload;

import java.util.Date;

public class ErrorDetails {

    private Date date;
    private String message;
    private String desp;

    public ErrorDetails(Date date, String message,String desp) {
        this.date = date;
        this.desp=desp;
        this.message = message;
    }
    public void setDesp(String desp){
        this.desp = desp;
    }
    public String getDesp(){
        return this.desp;
    }
    public void setDate(Date date){
        this.date = date;
    }
    public void setMessage(String message ){
        this.message=message;
    }
    public Date getDate(){
        return this.date;
    }
    public String getMessage(){
        return this.message;
    }
}
