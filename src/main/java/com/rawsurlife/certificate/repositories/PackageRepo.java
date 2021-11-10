package com.rawsurlife.certificate.repositories;

import java.util.List;
import java.util.Optional;

import com.rawsurlife.certificate.models.Package;
import com.rawsurlife.certificate.models.Subscriber;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PackageRepo extends JpaRepository<Package, Long> {

    Optional<Package> findById( Long id );

    Package findByName( String name );

    List <Package> findByNameContainingIgnoreCase( String name );

    Package findByCapital( double capital );

    List <Package> findBySubscriber( Subscriber subscriber );
    
}
