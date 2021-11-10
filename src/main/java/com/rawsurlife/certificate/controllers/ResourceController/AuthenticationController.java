package com.rawsurlife.certificate.controllers.ResourceController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import com.rawsurlife.certificate.configs.CustomUserDetailsService;
import com.rawsurlife.certificate.configs.JwtUtil;
import com.rawsurlife.certificate.models.AuthenticationRequest;
import com.rawsurlife.certificate.models.AuthenticationResponse;
import com.rawsurlife.certificate.models.DAOUser;
import com.rawsurlife.certificate.models.UserDTO;
import com.rawsurlife.certificate.repositories.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.impl.DefaultClaims;


@RestController
@CrossOrigin(origins = "*" , allowedHeaders = "*" )
public class AuthenticationController {

    @Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private UserRepo repo;

	@PostMapping(value = "/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {
		try {
			authenticationManager.authenticate( 
				new UsernamePasswordAuthenticationToken
				(
					authenticationRequest.getUsername(), 
					authenticationRequest.getPassword()
				) 
			);
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED : ", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS : ", e);
		}
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		
		System.out.println( "userDetails To String : " + userDetails.getAuthorities().toString()  );

		final String token = jwtTokenUtil.generateToken(userDetails);
		
		
		String roles = userDetails.getAuthorities().toString();
		
		DAOUser user = new DAOUser();
		user = this.repo.findByUsername( authenticationRequest.getUsername() );

		return ResponseEntity.ok( new AuthenticationResponse( token , roles, user ) );
	}


    @RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser( @RequestBody UserDTO user ) throws Exception {

		if ( this.repo.findByUsername( user.getUsername() ) != null  )  {
			throw new RuntimeException("User " + user.getUsername() + " Exists!");
		}
			
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername( user.getUsername() );
		userDTO.setFullname( user.getFullname() );
		userDTO.setPassword( user.getPassword() );

		if( user.getRole().equals(null)  ) {
			userDTO.setRole( "ROLE_USER" );
		}
		
		userDTO.setAgency( user.getAgency() );
		userDTO.setRole( user.getRole() );
		userDTO.setSubscriber( user.getSubscriber() );
		
		
		
		return ResponseEntity.ok( userDetailsService.save(userDTO) );
	}


	@RequestMapping(value = "/refreshtoken", method = RequestMethod.GET)
	public ResponseEntity<?> refreshtoken(HttpServletRequest request) throws Exception {
		// From the HttpRequest get the claims
		DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) request.getAttribute("claims");

		Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
		String token = jwtTokenUtil.doGenerateRefreshToken(expectedMap, expectedMap.get("sub").toString());
		token.toString();
		return ResponseEntity.ok( new String("OK") /**new AuthenticationResponse(token, )**/);
	}

	public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
		Map<String, Object> expectedMap = new HashMap<String, Object>();
		for (Entry<String, Object> entry : claims.entrySet()) {
			expectedMap.put(entry.getKey(), entry.getValue());
		}
		return expectedMap;
	}


	@GetMapping(value="get/fname/{name}")
    public DAOUser getUserByName(@PathVariable( value = "name" ) String name) {
        return this.repo.findByUsername(name);        
    }

	@GetMapping( value="list/" )
	public List<DAOUser> getUsers() {
		return this.repo.findAll();
	}

	@PostMapping( value = "password/reset/" )
	public DAOUser resetPassword( @RequestBody DAOUser usr ) {
		
		DAOUser returnedUser = new DAOUser();

		Optional<DAOUser> user = this.repo.findById( usr.getId() );
		
		if ( user.isPresent()  ) {
			user.get().setPassword( bcryptEncoder.encode( user.get().getPassword() ) );
			returnedUser = user.get();
		}
		else {
			throw new RuntimeException("User not Found!");
		}
		return returnedUser;
	}



	@PostMapping( value = "password/change/{id}" )
	public DAOUser changePassword( @RequestBody String newPassword , @PathVariable( value = "id" ) Long id ) throws Exception {

		DAOUser userr = new DAOUser();
		Optional<DAOUser> user = this.repo.findById( id );

		if ( user.isPresent()  ) {
			user.get().setPassword( bcryptEncoder.encode( user.get().getPassword() ) );
			userr = user.get();
			userr.setPassword( bcryptEncoder.encode(newPassword) );
		}
		else {
			throw new RuntimeException("Unable to change Password of User id {0} not Found!" + id );
		}
		
		return this.repo.save(userr);
	}

    
}