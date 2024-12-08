package com.remiges.remigesdb.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.remiges.remigesdb.dto.RankDTO;
import com.remiges.remigesdb.models.Rank;
import com.remiges.remigesdb.repositories.RankRepository;

@Component
public class RankMapper {

    @Autowired
    RankRepository rankRepository;

    public RankDTO toDto(Rank rank) {
        RankDTO dto = new RankDTO();
        dto.setRankdesc(rank.getRankdesc());
        dto.setParentRank(rank.getParentrankid() != null ? rank.getParentrankid().getRankdesc() : null);
        return dto;
    }

    public Rank toEntity(RankDTO dto) {
        Rank rank = new Rank();
        rank.setRankdesc(dto.getRankdesc());
        if (dto.getParentRank() != null) {
            rank.setParentrankid(rankRepository.findByRankdesc(dto.getRankdesc()).get(0));
        }
        return rank;
    }
}
