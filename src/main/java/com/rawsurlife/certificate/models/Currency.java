package com.rawsurlife.certificate.models;


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
@Entity( name = "CURRENCY" )
public class Currency {

    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE , generator = "currency_id" )
    @SequenceGenerator( name = "currency_id", initialValue = 1, allocationSize = 1 )
    private Long id;
    
    private String name;

    @Column( length = 3 , unique = true )
    private String isocode;

    @OneToOne( cascade = CascadeType.MERGE )
    @JoinColumn( nullable=false)
    @CreatedBy
    private DAOUser user;


    @OneToOne( cascade = CascadeType.MERGE )
    @JoinColumn( nullable=false)
    private DAOUser user_update;

    @Temporal( TemporalType.TIMESTAMP )
    @CreationTimestamp
    private Date createdAt;

    @Temporal( TemporalType.TIMESTAMP )
    @UpdateTimestamp
    private Date updatedAt;
    
}

