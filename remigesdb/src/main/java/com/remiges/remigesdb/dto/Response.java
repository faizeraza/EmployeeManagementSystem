package com.remiges.remigesdb.dto;

import java.util.Optional;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class Response<T> {

    // because we dont want it to be null
    private Optional<T> data = Optional.empty();
    private MetaData meta_data;

    public Response() {
    }

    public Response(T data, MetaData meta_data) {
        this.data = Optional.ofNullable(data);
        this.meta_data = meta_data;
    }

    // Static factory method for success responses
    public static <T> Response<T> success(T data, String _reqid) {
        MetaData meta_data = new MetaData(
                HttpStatus.OK,
                HttpStatus.OK.value(),
                "Request was successful",
                _reqid
        );
        return new Response<>(data, meta_data);
    }

    // Static factory method for failure responses
    public static <T> Response<T> failure(String errorMsg, int statusCode, String _reqid) {
        MetaData meta_data = new MetaData(
                HttpStatus.valueOf(statusCode),
                statusCode,
                errorMsg,
                _reqid
        );
        return new Response<>(null, meta_data);
    }
}
