package com.ar.bankingonline.application.services;

import com.ar.bankingonline.api.dtos.AccountDto;
import com.ar.bankingonline.api.mappers.AccountMapper;
import com.ar.bankingonline.api.exceptions.NotFoundException;
import com.ar.bankingonline.domain.models.Account;
import com.ar.bankingonline.domain.models.User;
import com.ar.bankingonline.infrastructure.repositories.AccountRepository;
import com.ar.bankingonline.infrastructure.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository repository;
    @Autowired
    private UserRepository userRepository;

    public AccountService(AccountRepository repository, UserRepository userRepository){
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Transactional
    public List<AccountDto> getAccounts(){
        List<Account> accounts = repository.findAll();
        return accounts.stream()
                .map(AccountMapper::accountToDto)
                .toList();
    }

    @Transactional
    public AccountDto getAccountById(Long id){
        return AccountMapper.accountToDto(repository.findById(id).get());
    }


    @Transactional
        public AccountDto createAccount(AccountDto account){
            Optional<User> user=userRepository.findById(account.getOwner().getId());
            Account accountModel=AccountMapper.dtoToAccount(account);
            accountModel.setOwner(user.get());
            accountModel=repository.save(accountModel);
            AccountDto dto =AccountMapper.accountToDto(accountModel);
            return dto;
        }


    @Transactional
    public AccountDto updateAccount(Long id,AccountDto account){
        Optional<Account> accountCreated = repository.findById(id);

        if(accountCreated.isPresent()){
            Account entity = accountCreated.get();

            Account accountUpdate = AccountMapper.dtoToAccount(account);

            accountUpdate.setId(entity.getId());

            Account saved = repository.save(accountUpdate);

            return AccountMapper.accountToDto(saved);

        }else {
            throw new NotFoundException("Account not found with id: " +id);
        }
        //return AccountMapper.accountToDto(repository.save(AccountMapper.dtoToAccount(account)));
    }
    @Transactional
    public String deleteAccount(Long id){
        if(repository.existsById(id)){
            repository.deleteById(id);
            return "Se ha eliminado la cuenta";
        }else {
            return "No se ha eliminado la cuenta";
        }

    }


    public BigDecimal withdraw(BigDecimal amount,Long idOrigin){
        //Primero obtenemos la cuenta
        Account account = repository.findById(idOrigin).orElse(null);
        //Segundo: debitamos el valor del amount con el amount de esa cuenta(validar que ambas cuentas existan)
        if (account.getAmount().subtract(amount).intValue()>0){
            account.setAmount(account.getAmount().subtract(amount));
            repository.save(account);
        }
        //tercero: devolvemos esa cantidad
        return  account.getAmount().subtract(amount);
    }
    public BigDecimal addAmountToAccount(BigDecimal amount,Long idOrigin){
        Account account = repository.findById(idOrigin).orElse(null);
        account.setAmount(account.getAmount().add(amount));
        repository.save(account);
        return  amount;
    }

    public Account getAccountByNumber(int number){
        //return repository.getByNumber(number);
        return null;
    }
}
