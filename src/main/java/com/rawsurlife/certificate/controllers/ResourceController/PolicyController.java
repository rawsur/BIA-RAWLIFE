package com.rawsurlife.certificate.controllers.ResourceController;

import java.util.List;

import com.rawsurlife.certificate.models.Policy;
import com.rawsurlife.certificate.models.Subscriber;
import com.rawsurlife.certificate.repositories.PolicyRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("api/v1/policy/")
@CrossOrigin(origins = "*" , allowedHeaders = "*" )
public class PolicyController {

    @Autowired
    private PolicyRepo repo;

    @PostMapping("save")
    public Policy savePolicy( @RequestBody Policy policy ) {
        return this.repo.save(policy);
    }


    


    @PutMapping("update/{id}")
    public Policy updatePolicy( @PathVariable( value = "id" ) Long id, @RequestBody Policy pol ) {

        Policy po = this.repo.findById(id)
                    .orElseThrow( ()-> new RuntimeException("Policy with Id " +id+ " does not exist. " ) );

        po.setCustomer(pol.getCustomer());
        po.setEffect_date(pol.getEffect_date());
        po.setExpiringDate(pol.getExpiringDate());
        po.setPkge(pol.getPkge());
        po.setSubscriber(pol.getSubscriber());
        po.setTypeofGarantie( pol.getTypeofGarantie() );
        
        
        return this.repo.save(po);
    }


    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deletePolicy( @PathVariable( value = "id" ) Long id ) {

        Policy pol = this.repo.findById(id)
                    .orElseThrow( ()-> new RuntimeException("Policy with Id " +id+ " does not exist. " ) );

        this.repo.delete(pol);
        return ResponseEntity.ok().build();
    }


    @GetMapping( "list" )
    public List<Policy>  getPackages() {
        return repo.findAll() ;
    }

    @GetMapping(value="get/{id}")
    public Policy getPolicyById(@PathVariable( value = "id" ) Long id) {
        
        return this.repo.findById(id).orElseThrow(
            () -> new RuntimeException("Policy with id " +id+ " does not exist." )
        );
        
    }



    @GetMapping(value="get/subscriber")
    public List<Policy> getPolicyByAgency( @RequestBody Subscriber subscriber ) {
        return this.repo.findBySubscriber( subscriber );
    }

    
}
