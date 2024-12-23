package com.bnb.service;

import com.twilio.rest.api.v2010.account.Message;

import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {

          // You can also inject this from properties file
     @Value("${twilio.phoneNumber}")
      private String number;
    // Method to send SMS
    public String sendSms(String toPhoneNumber, String messageBody) {
        Message message = Message.creator(
                new PhoneNumber("+91"+toPhoneNumber),    // recipient phone number
                new PhoneNumber(number),  // Twilio phone number
                messageBody // message content
        ).create();

        return message.getSid();  // Returns the unique SID of the sent message
    }

}
