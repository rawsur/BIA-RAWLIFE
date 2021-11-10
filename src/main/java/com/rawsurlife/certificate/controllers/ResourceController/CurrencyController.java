package com.rawsurlife.certificate.controllers.ResourceController;


import java.util.List;

import com.rawsurlife.certificate.repositories.CurrencyRepo;
import com.rawsurlife.certificate.models.Currency;

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
@RequestMapping("api/v1/currency/")
@CrossOrigin(origins = "*" , allowedHeaders = "*" )
public class CurrencyController {
    
    @Autowired
    private CurrencyRepo repo;

    @GetMapping("list")
    private List<Currency> getCurrencies() {
        return this.repo.findAll();
    }


    @GetMapping("get/{id}")
    private Currency getCurrency( @PathVariable Long id ) {
        return this.repo.findById(id).orElseThrow();
    }

    @PostMapping( "save" )
    private Currency saveCurrency( @Validated @RequestBody Currency curr  ) {
        System.err.println("Currency to save");
        System.out.println(curr.getName());
        System.out.println(curr.getUser());
        System.out.println(curr.getUser_update());
        return this.repo.save( curr );
    }

    @GetMapping(value="get/code/{codiso}")
    public List<Currency> getCurrencyByCodIso(@PathVariable( value = "codiso" ) String codiso) {
        return this.repo.findByIsocodeContainingIgnoreCase(codiso);
    }

    @PutMapping("update/{id}")
    public Currency updateCurrency( @PathVariable( value = "id" ) Long id, @RequestBody Currency currency ) {

        Currency curr = this.repo.findById(id)
                    .orElseThrow( ()-> new RuntimeException("Policy with Id " +id+ " does not exist. " ) );

        curr.setIsocode( currency.getIsocode() );
        curr.setName( currency.getName() );
        curr.setUser_update( curr.getUser_update() );
        return this.repo.save(curr);
    }


    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deletePolicy( @PathVariable( value = "id" ) Long id ) {

        Currency curr = this.repo.findById(id)
                    .orElseThrow( ()-> new RuntimeException("Currnecy with Id " +id+ " does not exist. " ) );

        this.repo.delete(curr);
        return ResponseEntity.ok().build();
    }

}

