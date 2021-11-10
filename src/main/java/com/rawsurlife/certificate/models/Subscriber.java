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

public class Subscriber implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE , generator = "subscriber_id" )
    @SequenceGenerator( name = "subscriber_id", initialValue = 1, allocationSize = 1 )
    private Long id;

    private String name;

    private String address;

    @Column( length = 13, nullable = true )
    private String phoneNumber;

    private String rccm;

    private String idnat;

    /**
     * One Policy to One User
     */
    @OneToOne( cascade = CascadeType.MERGE )
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
