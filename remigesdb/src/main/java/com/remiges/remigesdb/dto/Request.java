package com.remiges.remigesdb.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Request<T> {

    @NotNull(message = "Should Not Blank")
    private String token;
    @Valid
    private T data;
    @NotNull(message = "should not be null")
    private String _reqid;
    private String _client_ts;
    private String _client_type;
}
