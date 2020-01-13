package com.war.orke.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigInteger;
import java.util.Objects;

public class ColonyDto {

    @JsonIgnore
    private Integer id;
    private BigInteger populationCount;
    private String name;

    @JsonIgnore
    public Integer getId() {
        return id;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColonyDto that = (ColonyDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(populationCount, that.populationCount) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, populationCount, name);
    }
}
