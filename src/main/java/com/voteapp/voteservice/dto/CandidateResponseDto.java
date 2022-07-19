package com.voteapp.voteservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CandidateResponseDto {

    private Long id;

    private String name;

    private int voteCount;

}
