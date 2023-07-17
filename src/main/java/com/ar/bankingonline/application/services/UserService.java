package com.ar.bankingonline.application.services;


import com.ar.bankingonline.api.dtos.AccountDto;
import com.ar.bankingonline.api.mappers.UserMapper;
import com.ar.bankingonline.api.exceptions.NotFoundException;
import com.ar.bankingonline.domain.models.Account;
import com.ar.bankingonline.domain.models.User;
import com.ar.bankingonline.api.dtos.UserDto;
import com.ar.bankingonline.infrastructure.repositories.AccountRepository;
import com.ar.bankingonline.infrastructure.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    //Declaro una instancia del repositorio con @AUTOWIRED y sin la anotacion
    @Autowired
    private UserRepository repository;
    @Autowired
    private final AccountRepository accountRepository;


    public UserService(UserRepository repository, AccountRepository accountRepository){
        this.repository=repository;
        this.accountRepository=accountRepository;
    }

    // Primero generar los metodos del CRUD

    public List<UserDto> getUsers(){
        List<User> users = repository.findAll();
        return users.stream()
                .map(UserMapper::userMapToDto)
                .toList();
    }

    // TODO: Refactor
    public UserDto getUserById(Long id){
        return UserMapper.userMapToDto(repository.findById(id).get());
    }


    public UserDto createUser(UserDto user){
        return UserMapper.userMapToDto(repository.save(UserMapper.dtoToUser(user)));
    }

    public UserDto update(Long id, UserDto user){

        Optional<User> userCreated = repository.findById(id);

        if(userCreated.isPresent()){
            User entity = userCreated.get();

            User accountUpdate = UserMapper.dtoToUser(user);

            accountUpdate.setAccounts(entity.getAccounts());

            if(user.getIdAccounts()!=null){
                List<Account> accountList = accountRepository.findAllById(user.getIdAccounts());
                List<Account> accountListFilter = accountList.stream().filter(e->!entity.getAccounts().contains(e)).toList();
                accountUpdate.getAccounts().addAll(accountListFilter);
                accountUpdate.setAccounts(accountList);
            }
            accountUpdate.setId(entity.getId());

            User saved = repository.save(accountUpdate);


            return UserMapper.userMapToDto(saved);

        }else {
            throw new NotFoundException("User not found with id: " +id);
        }
        //return UserMapper.userMapToDto(repository.save(UserMapper.dtoToUser(user)));
    }

    public String deleteUser(Long id){
        if(repository.existsById(id)){
            repository.deleteById(id);
            return "Se ha eliminado el usuario";
        }else {
            return "No se ha eliminado el usuario";
        }

    }

    public UserDto addAccountToUser(AccountDto account, Long id){
        return null;
    }
}
