package com.remiges.remigesdb.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RankDTO {

    @NotBlank(message = "Rank description cannot be blank")
    String rankdesc;
    String parentRank;
}
