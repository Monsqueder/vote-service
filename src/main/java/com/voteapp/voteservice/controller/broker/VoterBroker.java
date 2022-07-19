package com.voteapp.voteservice.controller.broker;

import com.voteapp.voteservice.service.interfaces.VoterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class VoterBroker {

    @Autowired
    private VoterService voterService;

    @MessageMapping("/voter")
    public void getAllVoters() {
        voterService.sendAllVotersAsVoterResponseDto();
    }
}
