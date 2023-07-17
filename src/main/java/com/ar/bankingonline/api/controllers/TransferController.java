package com.ar.bankingonline.api.controllers;


import com.ar.bankingonline.api.dtos.TransferDto;
import com.ar.bankingonline.application.services.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TransferController {


    private TransferService service;


    public TransferController(TransferService service){
        this.service = service;
    }


    //Metodos HTTP


    //GET
    @GetMapping(value = "/transfer")
    public ResponseEntity<List<TransferDto>> getTransfer(){
        List<TransferDto> transfer = service.getTransfer();
        return ResponseEntity.status(HttpStatus.OK).body(transfer);
    }


    //GET BY ID

    @GetMapping(value = "/transfer/{id}")
    public ResponseEntity<TransferDto> getTransferById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.getTransferById(id));
    }

    //POST
    @PostMapping(value = "/transfer")
    public ResponseEntity<TransferDto> performTransfer(@RequestBody TransferDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.performTransfer(dto));
    }

    //PUT
    @PutMapping(value = "/transfer/{id}")
    public ResponseEntity<TransferDto> updateTransfer(@PathVariable Long id,@RequestBody TransferDto transfer){
        return ResponseEntity.status(HttpStatus.OK).body(service.updateTransfer(id,transfer));
    }

    //DELETE
    @DeleteMapping(value = "/transfer/{id}")
    public ResponseEntity<String> deleteTransfer(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.deleteTransfer(id));
    }



}
