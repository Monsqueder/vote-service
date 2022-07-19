package com.voteapp.voteservice.controller.rest;

import com.voteapp.voteservice.dto.CandidateRequestDto;
import com.voteapp.voteservice.dto.CandidateResponseDto;
import com.voteapp.voteservice.service.interfaces.CandidateService;
import com.voteapp.voteservice.service.interfaces.VoterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("candidate")
public class CandidateController {
    @Autowired
    private CandidateService candidateService;

    @Autowired
    private VoterService voterService;

    @GetMapping
    public List<CandidateResponseDto> getAllCandidates() {
        return candidateService.getAllCandidatesAsCandidateResponseDto();
    }

    @PostMapping
    public void addCandidate(@RequestBody CandidateRequestDto candidateRequestDto) {
        candidateService.addCandidate(candidateRequestDto);
    }

    @DeleteMapping("{id}")
    public void deleteCandidate(@PathVariable("id") Long id) {
        candidateService.getCandidateVoters(id).forEach((voter) -> voterService.removeCandidateFromVoter(voter.getId()));
        voterService.sendAllVotersAsVoterResponseDto();
        candidateService.deleteCandidate(id);
    }
}
