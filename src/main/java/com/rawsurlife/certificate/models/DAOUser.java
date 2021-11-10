package com.rawsurlife.certificate.models;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table( name = "USER" )

public class DAOUser implements Serializable {

    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	
	@Column
	private String username;

	private String fullname;

	private String password;

	@JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
	}
	
	@Column
	private String role;

	@ManyToMany( cascade = CascadeType.MERGE )
    @JoinColumn( nullable=true)
	private Set<Action> actions;

	@OneToOne( cascade = CascadeType.MERGE )
    @JoinColumn( insertable = true, updatable = true, nullable = true, referencedColumnName = "id" )
    private Subscriber subscriber;


	@OneToOne( cascade = CascadeType.MERGE )
    @JoinColumn( nullable = true )
    private Agency agency;



	@Temporal( TemporalType.TIMESTAMP )
    @CreationTimestamp
    private Date createdAt;

    @Temporal( TemporalType.TIMESTAMP )
    @UpdateTimestamp
    private Date updatedAt;
	


	public DAOUser( String user, String password ) {
		this.username = user;
		this.password = password;
	}


	
	public DAOUser( String user, String password, String rol ) {
		this.username = user;
		this.password = password;
		this.role = rol;
	}


	public DAOUser( String user, String password, String rol , Subscriber subsc ) {
		this.username = user;
		this.password = password;
		this.role = rol;
		this.subscriber = subsc;
	}


	public DAOUser( String user, String password, String rol , Subscriber subsc, Agency agen ) {
		this.username = user;
		this.password = password;
		this.role = rol;
		this.subscriber = subsc;
		this.agency = agen;
	}
    
}
