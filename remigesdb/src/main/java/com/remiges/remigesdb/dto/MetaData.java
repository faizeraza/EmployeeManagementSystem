package com.remiges.remigesdb.dto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class MetaData {

    private HttpStatus status;
    private int status_code;
    private String status_msg;
    private Optional<String> _reqid = Optional.empty();
    private String _server_ts;

    public MetaData(HttpStatus status, int status_code, String status_msg, String _reqid) {
        this.status = status;
        this.status_code = status_code;
        this.status_msg = status_msg;
        this._reqid = Optional.ofNullable(_reqid);
        this._server_ts = LocalDateTime.now(ZoneId.systemDefault()).toString();
    }
}
