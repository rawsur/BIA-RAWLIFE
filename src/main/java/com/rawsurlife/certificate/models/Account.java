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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table( name = "Account" )
public class Account {
    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE , generator = "account_id" )
    @SequenceGenerator( name = "account_id", initialValue = 1, allocationSize = 1 )
    private Long id;
    
    @Column( length = 20 )
    private String accountNumber;

    @OneToOne( cascade = CascadeType.MERGE )
    @JoinColumn( nullable=false )
    private Currency currency;

    @OneToOne( cascade = CascadeType.MERGE )
    @JoinColumn( nullable=false )
    private Customer customer;


    /**
     * One Account created by One user
     */
    @OneToOne( cascade = CascadeType.MERGE )
    @JoinColumn( nullable=false )
    @CreatedBy
    private DAOUser user;

    /**
     * One Account updated by One user
     */
    @OneToOne( cascade = CascadeType.MERGE )
    @JoinColumn( nullable=false )
    private DAOUser user_update;

    @Temporal( TemporalType.TIMESTAMP )
    @CreationTimestamp
    private Date createdAt;

    @Temporal( TemporalType.TIMESTAMP )
    @UpdateTimestamp
    private Date updatedAt;

}
