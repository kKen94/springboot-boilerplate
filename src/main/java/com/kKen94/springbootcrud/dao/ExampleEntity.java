package com.kKen94.springbootcrud.dao;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "EXAMPLE")
public class ExampleEntity {

    @Id
    @GeneratedValue
    @Column(name = "ID", updatable = false, nullable = false)
    @Basic(optional = false)
    private UUID id;

    @Column(name = "DESCRIPTION")
    @Basic
    private String description;
}
