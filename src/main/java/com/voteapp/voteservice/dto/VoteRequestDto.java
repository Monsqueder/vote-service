package com.voteapp.voteservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoteRequestDto {

    private Long voterId;

    private Long candidateId;
}
