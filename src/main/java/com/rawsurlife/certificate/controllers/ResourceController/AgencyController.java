package com.rawsurlife.certificate.controllers.ResourceController;

import java.util.List;

import com.rawsurlife.certificate.models.Agency;
import com.rawsurlife.certificate.models.Subscriber;
import com.rawsurlife.certificate.repositories.AgencyRepo;

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
@RequestMapping("api/v1/agency/")
@CrossOrigin(origins = "*" , allowedHeaders = "*" )
public class AgencyController {
    
    @Autowired
    private AgencyRepo repo;

    @GetMapping("list")
    private List<Agency> getAccounts() {
        return this.repo.findAll();
    }


    @GetMapping("get/{id}")
    private Agency getAccount( @PathVariable Long id ) {
        return this.repo.findById(id).orElseThrow();
    }

    @PostMapping( "save" )
    private Agency saveAccount( @Validated @RequestBody Agency age  ) {
        return this.repo.save( age );
    }

    @GetMapping(value="get/code/{codeAgence}")
    public List<Agency> getByCodeAgency( @RequestBody Agency age) {
        return this.repo.findByCodeAgenceLike(age.getCodeAgence());
    }

    @GetMapping(value="get/name")
    public List<Agency> getAgencyByNameLike( @RequestBody String subscriber) {
        return this.repo.findBySubscriberNameLike( subscriber );
    }


    @GetMapping(value="get/subscriber")
    public List<Agency> getAgencyBySubscriber( @RequestBody Subscriber subscriber) {
        return this.repo.findBySubscriber( subscriber );
    }

    @PutMapping("update/{id}")
    public Agency updateAgency( @PathVariable( value = "id" ) Long id, @RequestBody Agency age ) {

        Agency agency = this.repo.findById(id)
                    .orElseThrow( ()-> new RuntimeException("Agency with Id " +id+ " does not exist. " ) );

        agency.setCodeAgence( age.getCodeAgence() );
        agency.setName( age.getName() );
        agency.setSubscriber( age.getSubscriber() );
        agency.setUpdatedAt( age.getUpdatedAt() );   

        return this.repo.save(agency);
    }


    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteAgency( @PathVariable( value = "id" ) Long id ) {

        Agency agency = this.repo.findById(id)
                    .orElseThrow( ()-> new RuntimeException("Agency with Id " +id+ " does not exist. " ) );

        this.repo.delete(agency);
        return ResponseEntity.ok().build();
    }
    
}
