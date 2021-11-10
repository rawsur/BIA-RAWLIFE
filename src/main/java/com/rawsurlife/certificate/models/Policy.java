package com.rawsurlife.certificate.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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

public class Policy implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE , generator = "policy_id" )
    @SequenceGenerator( name = "policy_id", initialValue = 1, allocationSize = 1 )
    private Long id;

    @Column( nullable = false, unique = true )
    private String ref;

    @OneToOne
    @JoinColumn( insertable = true, updatable = true, nullable = false, referencedColumnName = "id" )
    private Package pkge;

    @Temporal( TemporalType.DATE )
    @Column( nullable = false )
    private Date effect_date;

    @Column( nullable = false )
    private String typeofGarantie;

    @Temporal( TemporalType.DATE )
    @Column( nullable = false )
    private Date expiringDate;


    /**
     * One Policy to One Account
     */
    @OneToOne( cascade = CascadeType.MERGE )
    @JoinColumn( insertable = true, updatable = true, nullable = false, referencedColumnName = "id" )
    private Account account;


    /**
     * One Policy to One Customer
     */
    @OneToOne( cascade = CascadeType.MERGE )
    @JoinColumn( insertable = true, updatable = true, nullable = false, referencedColumnName = "id" )
    private Customer customer;



    /**
     * One Policy to One Subscriber
     */
    @OneToOne( cascade = CascadeType.MERGE)
    @JoinColumn( insertable = true, updatable = true, nullable = true, referencedColumnName = "id" )
    private Subscriber subscriber;


    /**
     * One Policy to One User
     */
    @OneToOne( cascade = CascadeType.MERGE )
    @JoinColumn( insertable = true, updatable = false, nullable = false, referencedColumnName = "id" )
    @CreatedBy
    private DAOUser user;

    /**
     * One Policy to One User who updated
     */
    @OneToOne( cascade = CascadeType.MERGE )
    @JoinColumn( insertable = true, updatable = false, nullable = false, referencedColumnName = "id" )
    private DAOUser user_Update;

    @Temporal( TemporalType.TIMESTAMP )
    @CreationTimestamp
    @Column( nullable = false )
    private Date createdAt;

    @Temporal( TemporalType.TIMESTAMP )
    @UpdateTimestamp
    private Date updatedAt;
    
}
