package com.example.erpproject.databases.entity;

import com.example.erpproject.util.dbutil.BaseEntity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@AttributeOverride(name = "uuid", column = @Column(name = "kdv_uuid"))
@Data
public class Kdv extends BaseEntity {

    private int percent = 20;
}
