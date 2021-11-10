package com.rawsurlife.certificate.controllers.ResourceController;

import java.util.List;

import com.rawsurlife.certificate.models.Subscriber;
import com.rawsurlife.certificate.repositories.SubscriberRepo;

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
@RequestMapping("api/v1/subscriber/")
@CrossOrigin(origins = "*" , allowedHeaders = "*" )
public class SubscriberController {
    
    @Autowired
    private SubscriberRepo repo;

    @PostMapping("save")
    public Subscriber saveSubscriber( @RequestBody Subscriber subscriber ) {
        return this.repo.save(subscriber);
    }


    @GetMapping( "list" )
    public List<Subscriber>  getSubscribers() {
        return repo.findAll() ;
    }

    @GetMapping(value="get/{id}")
    public Subscriber getSubscriberById(@PathVariable( value = "id" ) Long id) {
        return this.repo.findById(id).orElseThrow(
            () -> new RuntimeException("Subscriber with id " +id+ " does not exist." )
        );
    }

    @GetMapping(value="get/fname/{name}")
    public List<Subscriber> getSubscriberByName(@PathVariable( value = "name" ) String name) {
        return this.repo.findByNameContainingIgnoreCase(name);
    }

    @PutMapping("update/{id}")
    public Subscriber updatePolicy( @PathVariable( value = "id" ) Long id, @RequestBody Subscriber subscriber ) {

        Subscriber subsc = this.repo.findById(id)
                    .orElseThrow( ()-> new RuntimeException("Subscriber with Id " +subscriber+ " does not exist. " ) );
        
        subsc.setAddress( subscriber.getAddress() );
        subsc.setIdnat( subscriber.getIdnat() );
        subsc.setName( subscriber.getName() );
        subsc.setPhoneNumber( subscriber.getPhoneNumber() );        
        subsc.setRccm( subscriber.getRccm() );
        subsc.setUpdatedAt( subscriber.getUpdatedAt() );
        
        return this.repo.save(subsc);
    }

    @DeleteMapping( value = "delete/{id}" )
    public ResponseEntity<?> deleteSubscriber( @PathVariable( value = "id" ) Long id ){
        Subscriber sub = this.repo.findById(id)
                        .orElseThrow( ()-> new RuntimeException("Subscriber with Id " +id+ " does not exist. " ) );
        System.out.println("Subscriber to Delete : " + sub.getName() );
        repo.delete(sub);
        return ResponseEntity.ok().build();
    }
    
}
