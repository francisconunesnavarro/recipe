package com.abn.recipe.infrastructure.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * Abstract class with the common attributes.
 */
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable {
    
    static final long serialVersionUID = -8889304037255447987L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    
    @CreatedDate
    Instant createdDate;
    
    @LastModifiedDate
    Instant updatedDate;
    
    @Version
    Long version;
    
    @PrePersist
    public void prePersistData() {
        setCreatedDate(Instant.now());
        setUpdatedDate(Instant.now());
    }
    
    @PreUpdate
    public void preUpdateData() {
        setUpdatedDate(Instant.now());
    }
}
