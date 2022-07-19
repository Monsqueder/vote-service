package com.voteapp.voteservice.service.implementations;

import com.voteapp.voteservice.dto.VoterRequestDto;
import com.voteapp.voteservice.dto.VoterResponseDto;
import com.voteapp.voteservice.exception.AlreadyExistsException;
import com.voteapp.voteservice.exception.NotFoundException;
import com.voteapp.voteservice.model.Voter;
import com.voteapp.voteservice.repository.VoterRepository;
import com.voteapp.voteservice.service.interfaces.CandidateService;
import com.voteapp.voteservice.service.interfaces.VoterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoterServiceImpl implements VoterService {
    @Autowired
    private VoterRepository voterRepository;

    @Autowired
    private CandidateService candidateService;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Override
    public List<VoterResponseDto> getAllVotersAsVoterResponseDto() {
        List<Voter> voters = voterRepository.findAll();
        return voters.stream().map(this::getVoterResponseDtoFromVoter).collect(Collectors.toList());
    }

    private VoterResponseDto getVoterResponseDtoFromVoter(Voter voter) {
        VoterResponseDto voterResponseDto = new VoterResponseDto();
        voterResponseDto.setId(voter.getId());
        voterResponseDto.setName(voter.getName());
        voterResponseDto.setHasVoted(voter.isHasVoted());
        return voterResponseDto;
    }

    @Override
    public void addVoter(VoterRequestDto voterRequestDto) {
        validateVoter(voterRequestDto);

        Voter voter = new Voter();
        voter.setName(voterRequestDto.getName());
        voter.setHasVoted(false);
        voterRepository.save(voter);
        sendAllVotersAsVoterResponseDto();
    }

    private void validateVoter(VoterRequestDto voterRequestDto) {
        String name = voterRequestDto.getName();
        boolean exists = voterRepository.existsByName(name);
        if (exists) throw new AlreadyExistsException("Voter with name: " + name + " already exists");
    }

    @Override
    public void deleteVoter(Long id) {
        Voter voter = getVoter(id);
        if (voter.getCandidate() != null) {
            candidateService.decrementVoteCount(voter.getCandidate().getId());
        }
        voterRepository.delete(voter);
        sendAllVotersAsVoterResponseDto();
    }

    @Override
    public void sendAllVotersAsVoterResponseDto() {
        List<VoterResponseDto> voterResponseDtos = getAllVotersAsVoterResponseDto();
        simpMessagingTemplate.convertAndSend("/topic/voter", voterResponseDtos);
    }

    @Override
    public Voter getVoter(Long voterId) {
        return voterRepository.findById(voterId).orElseThrow(() -> new NotFoundException("Voter with id: " + voterId + " not found!"));
    }

    @Override
    @Transactional
    public void removeCandidateFromVoter(Long voterId) {
        Voter voter = getVoter(voterId);
        voter.setHasVoted(false);
        voter.setCandidate(null);
    }
}
