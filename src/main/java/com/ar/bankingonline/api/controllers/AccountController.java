package com.ar.bankingonline.api.controllers;

import com.ar.bankingonline.api.dtos.AccountDto;
import com.ar.bankingonline.api.dtos.UserDto;
import com.ar.bankingonline.application.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountController {

    private AccountService service;
    @Autowired
    public AccountController(AccountService service){
        this.service = service;
    }


    //Metodos HTTP
    //GET
    @GetMapping(value = "/accounts")
    public ResponseEntity<List<AccountDto>> getAccounts(){

        // 1) Obtener la lista de todos los DTO user de la DB
        // Agregar el servicio a la implementación del método del controlador
        List<AccountDto> accounts = service.getAccounts();

        // 2) Devolver la lista y enviar como respuesta
        return ResponseEntity.status(HttpStatus.OK).body(accounts);
    }

    // GET USER
    @GetMapping(value = "/accounts/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAccountById(id));
    }


    //POST
    @PostMapping(value = "/accounts")
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createAccount(dto));
    }

    // PUT
    @PutMapping(value = "/accounts/{id}")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable Long id,@RequestBody AccountDto account){
        return ResponseEntity.status(HttpStatus.OK).body(service.updateAccount(id,account));
    }

    @DeleteMapping(value = "/accounts/{id}")
    public ResponseEntity<String> deleteAcount(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.deleteAccount(id));
    }

}
