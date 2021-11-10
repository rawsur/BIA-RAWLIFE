package com.rawsurlife.certificate.repositories;

import java.util.List;

import com.rawsurlife.certificate.models.Currency;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepo extends JpaRepository<Currency, Long> {
    List <Currency> findByIsocodeContainingIgnoreCase( String isocode );
}
