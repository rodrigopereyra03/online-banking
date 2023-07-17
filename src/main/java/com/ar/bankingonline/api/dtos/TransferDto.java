package com.ar.bankingonline.api.dtos;


import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TransferDto {

    private Long id;

    private Long origin;

    private Long target;

    private Date date;

    private BigDecimal amount;
}
