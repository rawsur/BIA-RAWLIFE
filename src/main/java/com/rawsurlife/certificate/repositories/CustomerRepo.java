package com.rawsurlife.certificate.repositories;

import java.util.List;

import com.rawsurlife.certificate.models.Agency;
import com.rawsurlife.certificate.models.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {

    Customer findByFullname( String fname );
    List <Customer> findByFullnameContainingIgnoreCase( String name );

    List <Customer> findByAgency( Agency agency );
    
}
