package com.voteapp.voteservice.controller.rest;

import com.voteapp.voteservice.dto.VoteRequestDto;
import com.voteapp.voteservice.service.interfaces.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("vote")
public class VoteController {

    @Autowired
    private VoteService voteService;

    @PostMapping
    public void vote(@RequestBody VoteRequestDto voteRequestDto) {
        voteService.vote(voteRequestDto);
    }

}
