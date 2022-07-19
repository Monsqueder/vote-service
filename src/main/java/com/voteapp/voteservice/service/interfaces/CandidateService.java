package com.voteapp.voteservice.service.interfaces;

import com.voteapp.voteservice.dto.CandidateRequestDto;
import com.voteapp.voteservice.dto.CandidateResponseDto;
import com.voteapp.voteservice.model.Candidate;
import com.voteapp.voteservice.model.Voter;

import java.util.List;


public interface CandidateService {
    List<CandidateResponseDto> getAllCandidatesAsCandidateResponseDto();

    void addCandidate(CandidateRequestDto candidateRequestDto);

    void deleteCandidate(Long id);

    void sendAllCandidatesAsCandidateResponseDto();

    Candidate getCandidate(Long candidateId);

    void decrementVoteCount(Long id);

    List<Voter> getCandidateVoters(Long id);
}
