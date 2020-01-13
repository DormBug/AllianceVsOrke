package com.war.orke.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigInteger;

@Entity
public class Colony {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private BigInteger populationCount;

    @Column(nullable = false, unique = true, updatable = false)
    private String name;

    public Integer getId() {
        return id;
    }

    public BigInteger getPopulationCount() {
        return populationCount;
    }

    public void setPopulationCount(BigInteger populationCount) {
        this.populationCount = populationCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
