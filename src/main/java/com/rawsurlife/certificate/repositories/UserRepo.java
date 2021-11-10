package com.rawsurlife.certificate.repositories;

import com.rawsurlife.certificate.models.DAOUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<DAOUser, Long> {
    DAOUser findByUsername(String username);
    DAOUser findByUsernameAndPassword(String username, String password);
}
