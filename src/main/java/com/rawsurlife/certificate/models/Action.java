package com.rawsurlife.certificate.models;

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
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Action {
    
    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE , generator = "action_id" )
    @SequenceGenerator( name = "action_id", initialValue = 1, allocationSize = 1 )
    private Long id;

    @Column( nullable = false, unique = true )
    private String actionName;

    /**
     * Many Actions to Many Users
     */
    @ManyToMany( cascade = CascadeType.MERGE )
    @JoinColumn( nullable=false, unique = true  , referencedColumnName = "id" )
    private Set<DAOUser> user;


    /**
     * One Action created by One User
     */
    @OneToOne( cascade = CascadeType.MERGE )
    @JoinColumn( nullable=false )
    @CreatedBy
    private DAOUser userCreated;
    

    /**
     * One Action updated by One User
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
