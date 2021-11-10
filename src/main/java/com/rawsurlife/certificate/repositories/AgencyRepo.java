package com.rawsurlife.certificate.repositories;

import java.util.List;

import com.rawsurlife.certificate.models.Agency;
import com.rawsurlife.certificate.models.Subscriber;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgencyRepo extends JpaRepository<Agency, Long> {
    public List<Agency> findByCodeAgenceLike( String codeAgence );

    public List<Agency> findBySubscriber( Subscriber subscriber );

    public List<Agency> findBySubscriberNameLike( String name );
}
