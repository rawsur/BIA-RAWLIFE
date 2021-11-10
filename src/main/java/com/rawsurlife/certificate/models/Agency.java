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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Agency {
    
    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE , generator = "agency_id" )
    @SequenceGenerator( name = "agency_id", initialValue = 1, allocationSize = 1 )
    private Long id;

    @Column( nullable = false, unique = true, length = 5 )
    private String codeAgence;

    @Column( nullable = false, unique = true )
    private String name;


    /**
     * One Agency  by One Subscriber
     */
    @OneToOne( cascade = CascadeType.MERGE )
    @CreatedBy
    @JoinColumn( insertable = true, updatable = true, nullable = false )
    private Subscriber subscriber;


    /**
     * One Agency created by One User
     */
    @OneToOne( cascade = CascadeType.MERGE )
    @CreatedBy
    private DAOUser user;


    /**
     * One Agency updated by One User
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
