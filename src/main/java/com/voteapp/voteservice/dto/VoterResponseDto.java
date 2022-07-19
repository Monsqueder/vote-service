package com.voteapp.voteservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoterResponseDto {

    private Long id;

    private String name;

    private boolean hasVoted;
}
