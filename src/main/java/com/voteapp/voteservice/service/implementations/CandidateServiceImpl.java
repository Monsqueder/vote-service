package com.voteapp.voteservice.service.implementations;

import com.voteapp.voteservice.dto.CandidateRequestDto;
import com.voteapp.voteservice.dto.CandidateResponseDto;
import com.voteapp.voteservice.exception.AlreadyExistsException;
import com.voteapp.voteservice.exception.NotFoundException;
import com.voteapp.voteservice.model.Candidate;
import com.voteapp.voteservice.model.Voter;
import com.voteapp.voteservice.repository.CandidateRepository;
import com.voteapp.voteservice.service.interfaces.CandidateService;
import com.voteapp.voteservice.service.interfaces.VoterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CandidateServiceImpl implements CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Override
    public List<CandidateResponseDto> getAllCandidatesAsCandidateResponseDto() {
        List<Candidate> candidates = candidateRepository.findAll();
        return candidates.stream().map(this::getCandidateResponseDtoFromCandidate).collect(Collectors.toList());
    }

    private CandidateResponseDto getCandidateResponseDtoFromCandidate(Candidate candidate) {
        CandidateResponseDto candidateResponseDto = new CandidateResponseDto();
        candidateResponseDto.setId(candidate.getId());
        candidateResponseDto.setName(candidate.getName());
        candidateResponseDto.setVoteCount(candidate.getVoteCount());
        return candidateResponseDto;
    }

    @Override
    public void addCandidate(CandidateRequestDto candidateRequestDto) {
        validateCandidate(candidateRequestDto);
        Candidate candidate = new Candidate();
        candidate.setName(candidateRequestDto.getName());
        candidate.setVoteCount(0);
        candidateRepository.save(candidate);
        sendAllCandidatesAsCandidateResponseDto();

    }

    private void validateCandidate(CandidateRequestDto candidateRequestDto) {
        String name = candidateRequestDto.getName();
        boolean exists = candidateRepository.existsByName(name);
        if (exists) throw new AlreadyExistsException("Candidate with name: " + name + " already exists");
    }

    @Override
    @Transactional
    public void deleteCandidate(Long id) {
        Candidate candidate = getCandidate(id);
        candidateRepository.delete(candidate);
        sendAllCandidatesAsCandidateResponseDto();
    }

    @Override
    public List<Voter> getCandidateVoters(Long id) {
        return getCandidate(id).getVoters();
    }

    @Override
    public void sendAllCandidatesAsCandidateResponseDto() {
        List<CandidateResponseDto> candidateResponseDtos = getAllCandidatesAsCandidateResponseDto();
        simpMessagingTemplate.convertAndSend("/topic/candidate", candidateResponseDtos);
    }

    @Override
    public Candidate getCandidate(Long candidateId) {
        return candidateRepository.findById(candidateId).orElseThrow(() ->
                new NotFoundException("Candidate with id: " + candidateId + " not found!"));
    }

    @Override
    @Transactional
    public void decrementVoteCount(Long id) {
        Candidate candidate = getCandidate(id);
        candidate.setVoteCount(candidate.getVoteCount() - 1);
        sendAllCandidatesAsCandidateResponseDto();
    }
}
