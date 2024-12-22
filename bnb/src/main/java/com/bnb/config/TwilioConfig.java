package com.bnb.config;

import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class TwilioConfig {

    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;

    @Value("${twilio.phoneNumber}")
    private String twilioPhoneNumber;

    @Value("${twilio.whatsapp.number}")
    private String whatsappNumber;

    @Bean
    public void init() {
        Twilio.init(accountSid, authToken);  // Initializes Twilio with your SID and Auth Token
    }
}
