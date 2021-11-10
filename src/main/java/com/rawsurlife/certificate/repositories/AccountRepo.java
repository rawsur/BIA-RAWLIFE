package com.rawsurlife.certificate.repositories;

import java.util.List;

import com.rawsurlife.certificate.models.Account;
import com.rawsurlife.certificate.models.Currency;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {
    public List<Account> findByAccountNumberLike( String acctNumber );
    public List<Account> findByCurrency( Currency currency );
}
