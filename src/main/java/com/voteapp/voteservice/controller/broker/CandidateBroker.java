package com.voteapp.voteservice.controller.broker;

import com.voteapp.voteservice.service.interfaces.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class CandidateBroker {

    @Autowired
    private CandidateService candidateService;

    @MessageMapping("/candidate")
    public void getAllCandidates() {
        candidateService.sendAllCandidatesAsCandidateResponseDto();
    }

}
