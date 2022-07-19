package com.voteapp.voteservice.repository;

import com.voteapp.voteservice.model.Voter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoterRepository extends JpaRepository<Voter, Long> {
    boolean existsByName(String name);
}
