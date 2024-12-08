package com.remiges.remigesdb.models;

import java.util.ArrayList;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class DataList {

    @Valid
    ArrayList<@Digits(integer = 10, fraction = 2, message = "Each number must be a valid float with up to 2 decimal places.") Float> dataList;
    String operator;
    String result;
}
