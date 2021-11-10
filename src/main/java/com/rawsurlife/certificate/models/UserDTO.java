package com.rawsurlife.certificate.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String username;
	private String fullname;
	private String password;
	private String role;
	private Subscriber subscriber;
	private Agency agency;

	public UserDTO( String user, String pass ) {
		this.username=user;
		this.password = pass;
	}

	public UserDTO( String user, String pass , String role ) {
		this.username=user;
		this.password = pass;
		this.role = role;
	}

}
