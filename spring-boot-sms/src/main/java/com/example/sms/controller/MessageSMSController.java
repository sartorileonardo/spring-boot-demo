package com.example.sms.controller;

import com.example.sms.model.MessageSMS;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("message")
public class MessageSMSController {

    @PostMapping("{originNumber}/{destinationNumber}")
    public ResponseEntity sendSMSMessage(@RequestBody MessageSMS message, @PathVariable String originNumber, @PathVariable String destinationNumber){
        Message msg = Message.creator(new PhoneNumber(destinationNumber), new PhoneNumber(originNumber), message.getText()).create();
        log.info("Message sended!\n".concat(msg.getSid()));
        return msg.getErrorCode() == null ? ResponseEntity.ok(message) : ResponseEntity.badRequest().build();
    }
}
