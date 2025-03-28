package com.example.newMock.controller;

import com.example.newMock.model.RequestDTO;
import com.example.newMock.model.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RestController
public class MainController {

    private final Logger log = LoggerFactory.getLogger(MainController.class);

    private final ObjectMapper mapper = new ObjectMapper();

    @PostMapping(
            value = "/info/postBalances",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Object postBalances(@RequestBody RequestDTO requestDTO) {
        try {
            String clientId = requestDTO.getClientId();
            char firstDigit = clientId.charAt(0);
            String currency;
            BigDecimal maxLimit;

            if (firstDigit == '8') {
                currency = "US";
                maxLimit = new BigDecimal("2000.00");
            } else if (firstDigit == '9') {
                currency = "EU";
                maxLimit = new BigDecimal("1000.00");
            } else {
                currency = "RUB";
                maxLimit = new BigDecimal("10000.00");
            }

            BigDecimal balance = maxLimit.multiply(BigDecimal.valueOf(Math.random()))
                    .setScale(2, RoundingMode.HALF_UP);

            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setRqUID(requestDTO.getRqUID());
            responseDTO.setClientId(clientId);
            responseDTO.setAccount(requestDTO.getAccount());
            responseDTO.setCurrency(currency);
            responseDTO.setBalance(balance);
            responseDTO.setMaxLimit(maxLimit);

            log.info("RequestDTO: {}", mapper.writeValueAsString(requestDTO));
            log.info("ResponseDTO: {}", mapper.writeValueAsString(responseDTO));

            return responseDTO;

        } catch (Exception e) {
            log.error("Error processing request", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}