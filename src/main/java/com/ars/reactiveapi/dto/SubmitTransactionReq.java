package com.ars.reactiveapi.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SubmitTransactionReq {

    private Long userId;
    private String paymentMethod;

}
