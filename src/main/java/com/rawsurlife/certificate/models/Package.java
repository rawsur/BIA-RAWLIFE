package com.rawsurlife.certificate.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

public class Package implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE , generator = "package_id" )
    @SequenceGenerator( name = "package_id", initialValue = 1, allocationSize = 1 )
    private Long id;

    @Column( nullable=false )
    private String name;

    @Column( nullable = false )
    private double capital;

    @Column( nullable = false)
    private double premium = 0.0;

    @ManyToOne( cascade = CascadeType.MERGE )
    @JoinColumn( insertable = true, updatable = true, nullable = true, referencedColumnName = "id" )
    private Subscriber subscriber;

    /**
     * One Package can be created by One User
     */
    @OneToOne( cascade = CascadeType.MERGE )
    @JoinColumn( insertable = true, updatable = false, nullable = false, referencedColumnName = "id" )
    @CreatedBy
    private DAOUser user;
    /**
     * One Package can be updated by One User
     */
    @OneToOne( cascade = CascadeType.MERGE )
    @JoinColumn( insertable = true, updatable = false, nullable = false, referencedColumnName = "id" )
    @CreatedBy
    private DAOUser user_Updated;

    @Temporal( TemporalType.TIMESTAMP )
    @CreationTimestamp
    private Date createdAt;

    @Temporal( TemporalType.TIMESTAMP )
    @UpdateTimestamp
    private Date updatedAt;
}
