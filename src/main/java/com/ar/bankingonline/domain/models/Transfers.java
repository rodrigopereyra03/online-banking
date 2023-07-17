package com.ar.bankingonline.domain.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
public class Transfers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transfer_id")
    private Long id;

    private Long origin;

    private Long target;

    private Date date;

    private BigDecimal amount;

}