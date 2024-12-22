package com.bnb.service;

import com.bnb.config.TwilioConfig;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WhatsappService {

    @Value("${twilio.whatsapp.number}")
    private String twilioConfig;

    @Value("${twilio.whatsapp.id}")
    private String twilioConfigId;

    public void sendWhatsAppMessage(String toPhoneNumber, String messageBody) {
        Message message = Message.creator(
                twilioConfigId,
                new PhoneNumber("whatsapp:+91" + toPhoneNumber),  // Recipient phone number (in WhatsApp format)
                new PhoneNumber(twilioConfig),

                messageBody
        ).create();

        System.out.println("WhatsApp SID: " + message.getSid());  // Prints the SID of the sent message
    }

}
