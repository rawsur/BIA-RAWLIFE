package com.rawsurlife.certificate.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
    private String token;
    private String roles;
    private Subscriber subscriber;
    private DAOUser usr;

    public AuthenticationResponse( String tokn , String rols ) {
        this.roles = rols;
        this.token = tokn;
    }

    public AuthenticationResponse( String tokn , DAOUser user ) {
        this.token = tokn;
        this.usr = user;
    }

    public AuthenticationResponse( String tokn , String rols , DAOUser user ) {
        this.roles = rols;
        this.token = tokn;
        this.usr = user;
    }

}
