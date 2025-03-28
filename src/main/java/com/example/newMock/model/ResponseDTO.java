package com.example.newMock.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {

    private String rqUID;
    private String clientId;
    private String account;
    private String currency;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal balance;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal maxLimit;
}