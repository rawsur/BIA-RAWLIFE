package com.rawsurlife.certificate.configs;

import java.util.Arrays;
import java.util.List;

import com.rawsurlife.certificate.models.DAOUser;
import com.rawsurlife.certificate.models.UserDTO;
import com.rawsurlife.certificate.repositories.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
	private UserRepo userDao;

	@Autowired
	private PasswordEncoder bcryptEncoder;

    @Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<SimpleGrantedAuthority> roles=null;
		
        DAOUser user = userDao.findByUsername(username);
		if (user != null) {
			
			//System.err.println("findByUsername : " +user.toString() );
			
			roles = Arrays.asList(new SimpleGrantedAuthority(user.getRole()));

			System.out.println("Roles : " + user.getRole());
			System.out.println("Roles 1 : " + roles.toString() );

			return new User(user.getUsername(), user.getPassword(), roles);
		}        
        throw new UsernameNotFoundException("User not found with username: " + username);
	}


    public DAOUser save(UserDTO user) {

		DAOUser newUser = new DAOUser();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		newUser.setAgency( user.getAgency() );
		newUser.setRole(user.getRole());
		newUser.setSubscriber( user.getSubscriber() );
		return userDao.save(newUser);
	}

    
}
