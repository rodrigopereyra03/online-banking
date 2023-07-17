package com.ar.bankingonline.api.mappers;

import com.ar.bankingonline.api.dtos.TransferDto;
import com.ar.bankingonline.domain.models.Transfers;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TransferMapper {

    public Transfers dtoToTransfer(TransferDto dto){
        Transfers transfers = new Transfers();
        transfers.setDate(dto.getDate());
        transfers.setOrigin(dto.getOrigin());
        transfers.setTarget(dto.getTarget());
        transfers.setAmount(dto.getAmount());
        return transfers;
    }

    public TransferDto transferToDto(Transfers transfers){
        TransferDto dto = new TransferDto();
        dto.setDate(transfers.getDate());
        dto.setOrigin(transfers.getOrigin());
        dto.setTarget(transfers.getTarget());
        dto.setAmount(transfers.getAmount());
        dto.setId(transfers.getId());
        return dto;
    }

}
