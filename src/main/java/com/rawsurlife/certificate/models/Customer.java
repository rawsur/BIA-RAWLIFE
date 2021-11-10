package com.rawsurlife.certificate.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;

import lombok.Data;


@Data
@Entity
public class Customer implements Serializable {
    
    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE , generator = "customer_id" )
    @SequenceGenerator( name = "customer_id", initialValue = 1, allocationSize = 1 )
    private Long id;

    

    private String fullname;

    private String sex;

    @Temporal( TemporalType.DATE )
    private Date dob;

    private String poBirth;

    private String address;

    @Column( length = 13 )
    private String phoneNumber;


    /**
     * One Customer to One Agency
     */
    @OneToOne( fetch = FetchType.LAZY , cascade = CascadeType.MERGE )
    private Agency  agency;


    /**
     * One Customer to Many Account's
     *
     * @OneToMany( fetch = FetchType.LAZY , cascade = CascadeType.MERGE )
     * private Set<Account>  account; 
     *
    **/


    /**
     * One Customer to One Subscriber
     */
    @OneToOne( cascade = CascadeType.MERGE )
    @CreatedBy
    @JoinColumn( insertable = true, updatable = true, nullable = false )
    private Subscriber subscriber;


    /**
     * One Customer created by One User
     */
    @OneToOne( cascade = CascadeType.MERGE )
    @CreatedBy
    private DAOUser user;


    /**
     * One Customer can be updated by One User
     */
    @OneToOne( cascade = CascadeType.MERGE )
    @CreatedBy
    private DAOUser user_Updated;


    @Temporal( TemporalType.TIMESTAMP )
    @CreationTimestamp
    private Date createdAt;

    @Temporal( TemporalType.TIMESTAMP )
    @UpdateTimestamp
    private Date updatedAt;

}
