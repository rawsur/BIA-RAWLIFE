package com.rawsurlife.certificate.repositories;

import java.util.List;

import com.rawsurlife.certificate.models.Subscriber;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SubscriberRepo  extends JpaRepository<Subscriber, Long> {
    List <Subscriber> findByNameContainingIgnoreCase( String name );
}
