package com.voteapp.voteservice.service.interfaces;

import com.voteapp.voteservice.dto.VoterRequestDto;
import com.voteapp.voteservice.dto.VoterResponseDto;
import com.voteapp.voteservice.model.Voter;

import java.util.List;

public interface VoterService {
    List<VoterResponseDto> getAllVotersAsVoterResponseDto();

    void addVoter(VoterRequestDto voterRequestDto);

    void deleteVoter(Long id);

    void sendAllVotersAsVoterResponseDto();

    Voter getVoter(Long voterId);

    void removeCandidateFromVoter(Long voterId);
}
