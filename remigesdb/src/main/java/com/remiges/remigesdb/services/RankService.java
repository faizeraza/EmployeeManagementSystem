package com.remiges.remigesdb.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.remiges.remigesdb.dto.RankDTO;
import com.remiges.remigesdb.mapper.RankMapper;
import com.remiges.remigesdb.models.Rank;
import com.remiges.remigesdb.repositories.RankRepository;

@Service
public class RankService {

    @Autowired
    RankRepository rankRepository;
    @Autowired
    RankMapper rankMapper;

    public void addRank(Rank rank) {
        rankRepository.save(rank);
    }

    public void addRanks(List<Rank> ranks) {
        rankRepository.saveAll(ranks);
    }

    public List<RankDTO> getRanks() {
        List<RankDTO> dtos = new ArrayList<>();
        for (Rank rank : rankRepository.findAll()) {
            dtos.add(rankMapper.toDto(rank));
        }
        return dtos;
    }
}
