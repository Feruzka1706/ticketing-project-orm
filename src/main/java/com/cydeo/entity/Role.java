package com.cydeo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "roles")
@Entity
@Where(clause = "is_deleted=false")
public class Role extends BaseEntity{
    private String description;

}
