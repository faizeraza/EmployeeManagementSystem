package com.remiges.remigesdb.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.remiges.remigesdb.models.Rank;

public interface RankRepository extends JpaRepository<Rank, Long> {

    List<Rank> findByRankdesc(String rankdesc);
}
