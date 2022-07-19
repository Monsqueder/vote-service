package com.voteapp.voteservice.service.interfaces;

import com.voteapp.voteservice.dto.VoteRequestDto;

public interface VoteService {
    void vote(VoteRequestDto voteRequestDto);
}
