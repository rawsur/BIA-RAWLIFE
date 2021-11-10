package com.rawsurlife.certificate.controllers.ResourceController;

import java.util.List;

import com.rawsurlife.certificate.models.Account;
import com.rawsurlife.certificate.models.Currency;
import com.rawsurlife.certificate.repositories.AccountRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/account/")
@CrossOrigin(origins = "*" , allowedHeaders = "*" )
public class AccountController {
    
    @Autowired
    private AccountRepo repo;

    @GetMapping("list")
    private List<Account> getAccounts() {
        return this.repo.findAll();
    }


    @GetMapping("get/{id}")
    private Account getAccount( @PathVariable Long id ) {
        return this.repo.findById(id).orElseThrow();
    }

    @PostMapping( "save" )
    private Account saveAccount( @Validated @RequestBody Account acct  ) {
        return this.repo.save( acct );
    }

    @GetMapping(value="get/code/{codiso}")
    public List<Account> getAccountByCurrency( @RequestBody Currency currency) {
        return this.repo.findByCurrency(currency);
    }

    @PutMapping("update/{id}")
    public Account updateCurrency( @PathVariable( value = "id" ) Long id, @RequestBody Account acct ) {

        Account account = this.repo.findById(id)
                    .orElseThrow( ()-> new RuntimeException("Account with Id " +id+ " does not exist. " ) );

        account.setAccountNumber( acct.getAccountNumber() );
        account.setCreatedAt( acct.getCreatedAt() );
        account.setCurrency( acct.getCurrency() );
        account.setUser_update( acct.getUser_update() );
        return this.repo.save(account);
    }


    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deletePolicy( @PathVariable( value = "id" ) Long id ) {

        Account account = this.repo.findById(id)
                    .orElseThrow( ()-> new RuntimeException("Account with Id " +id+ " does not exist. " ) );

        this.repo.delete(account);
        return ResponseEntity.ok().build();
    }

}
