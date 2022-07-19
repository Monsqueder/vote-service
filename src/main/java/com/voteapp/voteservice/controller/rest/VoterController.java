package com.voteapp.voteservice.controller.rest;

import com.voteapp.voteservice.dto.VoterRequestDto;
import com.voteapp.voteservice.dto.VoterResponseDto;
import com.voteapp.voteservice.service.interfaces.VoterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("voter")
public class VoterController {
    @Autowired
    private VoterService voterService;

    @GetMapping
    public List<VoterResponseDto> getAllVoters() {
        return voterService.getAllVotersAsVoterResponseDto();
    }

    @PostMapping
    public void addVoter(@RequestBody VoterRequestDto voterRequestDto) {
        voterService.addVoter(voterRequestDto);
    }

    @DeleteMapping("{id}")
    public void deleteVoter(@PathVariable("id") Long id) {
        voterService.deleteVoter(id);
    }
}
