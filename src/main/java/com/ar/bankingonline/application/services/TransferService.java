package com.ar.bankingonline.application.services;

import com.ar.bankingonline.api.dtos.TransferDto;
import com.ar.bankingonline.api.mappers.TransferMapper;
import com.ar.bankingonline.api.exceptions.NotFoundException;
import com.ar.bankingonline.domain.models.Account;
import com.ar.bankingonline.domain.models.Transfers;
import com.ar.bankingonline.infrastructure.repositories.AccountRepository;
import com.ar.bankingonline.infrastructure.repositories.TransfersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransferService {
    @Autowired
    private TransfersRepository repository;

    @Autowired
    private AccountRepository accountRepository;

    public TransferService(TransfersRepository repository){
        this.repository = repository;
    }


    public List<TransferDto> getTransfer(){
        List<Transfers> transfers = repository.findAll();
        return transfers.stream()
                .map(TransferMapper::transferToDto).
                collect(Collectors.toList());
    }

    public TransferDto getTransferById(Long id){
        Transfers transfers = repository.findById(id).orElseThrow(()-> new NotFoundException("Transfer not found with id: " + id));
        return TransferMapper.transferToDto(transfers);
    }

    /*
    public TransferDto createTransfer(TransferDto transferDto){
        //Creamos un objeto del tipo Date para obtener la fecha actual
        Date date = new Date();
        //Seteamos la fecha en el transferDto
        transferDto.setDate(date);
        Transfers transfer = TransferMapper.dtoToTransfer(transferDto);
        return TransferMapper.transferToDto(repository.save(transfer));
    }*/

    public TransferDto updateTransfer(Long id,TransferDto transferDto){
        Transfers transfers = repository.findById(id).orElseThrow(()-> new NotFoundException("Transfer not found with id: " + id));
        Transfers updateTransfer = TransferMapper.dtoToTransfer(transferDto);
        updateTransfer.setId(transfers.getId());
        return TransferMapper.transferToDto(repository.save(updateTransfer));
        //return AccountMapper.accountToDto(repository.save(AccountMapper.dtoToAccount(account)));
    }

    public String deleteTransfer(Long id){
        if(repository.existsById(id)){
            repository.deleteById(id);
            return "Se ha eliminado la transferencia";
        }else {
            return "No se ha eliminado la transferencia";
        }

    }

    //Logica para la realizacion de las transferencias

    @Transactional
    public TransferDto performTransfer(TransferDto dto){
        //Comprobar si las cuentas de origen y destino existen
        Account originAccount = accountRepository.findById(dto.getOrigin()).orElseThrow(()->new NotFoundException("Account not found with id: " + dto.getOrigin()));
        Account destinationAccount = accountRepository.findById(dto.getTarget()).orElseThrow(()->new NotFoundException("Account not found with id: " + dto.getTarget()));

        //Comprobar si la cuenta de origin tiene fondos suficientes

        if(originAccount.getAmount().compareTo(dto.getAmount())<0){
            throw new NotFoundException("Insufficient funds in the account with id: "+ dto.getOrigin());
        }

        //Realizar transferencia
        originAccount.setAmount(originAccount.getAmount().subtract(dto.getAmount()));
        destinationAccount.setAmount(destinationAccount.getAmount().add(dto.getAmount()));

        //Guardar las dos cuentas actualizadas
        accountRepository.save(originAccount);
        accountRepository.save(destinationAccount);

        //Crear la transferencia y guardarla en la base de datos
        Transfers transfers = new Transfers();
        Date date = new Date();
        transfers.setDate(date);
        transfers.setOrigin(originAccount.getId());
        transfers.setTarget(destinationAccount.getId());
        transfers.setAmount(dto.getAmount());
        transfers = repository.save(transfers);

        //Devolver el Dto de la transferencia realizada
        return TransferMapper.transferToDto(transfers);


    }

}
