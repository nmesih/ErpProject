package com.example.erpproject.util.dbutil;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.UUID;

@MappedSuperclass
@Data
@EntityListeners({AuditingEntityListener.class})
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Bir primary Key olduğunu tanımladık
    private Long id;

    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")   //UUID oluşturuyor.
    @JdbcTypeCode(java.sql.Types.VARCHAR)   //Database'deki sütunun tipi
    private UUID uuid;

    @CreatedDate
    private Date createdDate;

    @LastModifiedDate
    private Date updatedDate;

    @PrePersist // Kayıttan önce çalışan bir listener. Bu listener sayesinde kayıt oluşturulmadan önce çalışan bir method yazabiliriz.
    protected void onCreate() {
        setUuid(UUID.randomUUID());
    }
}
