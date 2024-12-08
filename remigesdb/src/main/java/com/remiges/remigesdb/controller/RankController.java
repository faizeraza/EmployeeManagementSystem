package com.remiges.remigesdb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.remiges.remigesdb.dto.RankDTO;
import com.remiges.remigesdb.models.Rank;
import com.remiges.remigesdb.services.RankService;

@RestController
@RequestMapping()
public class RankController {

    @Autowired
    RankService rankService;

    @GetMapping("/ranks")
    public List<RankDTO> getRanks(@RequestParam("reqID") String id) {
        return rankService.getRanks();
    }

    @PostMapping("/rank/add")
    public String addRanks(@RequestBody List<Rank> ranks) {
        rankService.addRanks(ranks);
        return "done";
    }
}
