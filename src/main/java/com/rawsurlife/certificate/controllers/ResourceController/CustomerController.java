package com.rawsurlife.certificate.controllers.ResourceController;

import com.rawsurlife.certificate.models.Agency;
import com.rawsurlife.certificate.models.Customer;
import com.rawsurlife.certificate.repositories.CustomerRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("api/v1/customer/")
@CrossOrigin(origins = "*" , allowedHeaders = "*" )
public class CustomerController {

    @Autowired
    private CustomerRepo repo;


    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteCustomer( @PathVariable( value = "id" ) Long id ) {

        Customer cust = this.repo.findById(id)
                    .orElseThrow( ()-> new RuntimeException("Customer with Id " +id+ " does not exist. " ) );

        this.repo.delete(cust);
        return ResponseEntity.ok().build();
    }


    @PostMapping("save")
    public Customer saveCustomer( @RequestBody Customer custom ) {
        return this.repo.save(custom);
    }


    @GetMapping( "list" )
    public List<Customer>  getCustomers() {
        return repo.findAll() ;
    }

    @GetMapping(value="get/{id}")
    public Customer getCustomerById(@PathVariable( value = "id" ) Long id) {
        return this.repo.findById(id).orElseThrow(
            () -> new RuntimeException("Customer with id " +id+ " does not exist." )
        );
    }


    @GetMapping(value="get/agency/")
    public List<Customer> getCustomerByAgency( @RequestBody Agency agency ) {
        return this.repo.findByAgency( agency );
    }


    @GetMapping(value="get/fname/{name}")
    public Customer getCustomerByName(@PathVariable( value = "name" ) String name) {
        return this.repo.findByFullname(name);
    }
    
    
}
