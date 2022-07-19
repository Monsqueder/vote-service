package com.voteapp.voteservice.service.implementations;

import com.voteapp.voteservice.dto.VoteRequestDto;
import com.voteapp.voteservice.exception.VoterAlreadyVoted;
import com.voteapp.voteservice.model.Candidate;
import com.voteapp.voteservice.model.Voter;
import com.voteapp.voteservice.repository.CandidateRepository;
import com.voteapp.voteservice.repository.VoterRepository;
import com.voteapp.voteservice.service.interfaces.CandidateService;
import com.voteapp.voteservice.service.interfaces.VoteService;
import com.voteapp.voteservice.service.interfaces.VoterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    private VoterService voterService;
    @Autowired
    private CandidateService candidateService;
    @Override
    @Transactional
    public void vote(VoteRequestDto voteRequestDto) {
        Voter voter = voterService.getVoter(voteRequestDto.getVoterId());
        if (voter.isHasVoted()) throw new VoterAlreadyVoted("Voter with id: " + voter.getId() + " already voted!");
        Candidate candidate = candidateService.getCandidate(voteRequestDto.getCandidateId());

        voter.setCandidate(candidate);
        voter.setHasVoted(true);

        candidate.setVoteCount(candidate.getVoteCount() + 1);

        voterService.sendAllVotersAsVoterResponseDto();
        candidateService.sendAllCandidatesAsCandidateResponseDto();
    }
}
