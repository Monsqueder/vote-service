package com.voteapp.voteservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class VoterAlreadyVoted extends RuntimeException{
    public VoterAlreadyVoted(String message) {
        super(message);
    }
}
