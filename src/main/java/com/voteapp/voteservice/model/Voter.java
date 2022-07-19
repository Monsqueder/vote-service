package com.voteapp.voteservice.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "voter")
public class Voter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private boolean hasVoted;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;
}
