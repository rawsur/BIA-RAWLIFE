package com.rawsurlife.certificate.repositories;

import java.util.List;

import com.rawsurlife.certificate.models.Policy;
import com.rawsurlife.certificate.models.Subscriber;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PolicyRepo extends JpaRepository<Policy, Long> {

    Policy findByPkge( Package pkg );
    List <Policy> findByTypeofGarantieContainingIgnoreCase( String typeofGarantie );
    List <Policy> findByCustomer( String customer );

    List <Policy> findBySubscriber( Subscriber subscrib );
    
}
