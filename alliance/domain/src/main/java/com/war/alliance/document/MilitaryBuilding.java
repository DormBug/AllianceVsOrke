package com.war.alliance.document;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document("military_building")
public class MilitaryBuilding {

    private ObjectId id;

    private String name;

    private Integer healthPoint;

    private Integer goldCost;

    private Integer woodCost;

    private Integer areaX;

    private Integer areaY;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHealthPoint() {
        return healthPoint;
    }

    public void setHealthPoint(Integer healthPoint) {
        this.healthPoint = healthPoint;
    }

    public Integer getGoldCost() {
        return goldCost;
    }

    public void setGoldCost(Integer goldCost) {
        this.goldCost = goldCost;
    }

    public Integer getWoodCost() {
        return woodCost;
    }

    public void setWoodCost(Integer woodCost) {
        this.woodCost = woodCost;
    }

    public Integer getAreaX() {
        return areaX;
    }

    public void setAreaX(Integer areaX) {
        this.areaX = areaX;
    }

    public Integer getAreaY() {
        return areaY;
    }

    public void setAreaY(Integer areaY) {
        this.areaY = areaY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MilitaryBuilding that = (MilitaryBuilding) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(healthPoint, that.healthPoint) &&
                Objects.equals(goldCost, that.goldCost) &&
                Objects.equals(woodCost, that.woodCost) &&
                Objects.equals(areaX, that.areaX) &&
                Objects.equals(areaY, that.areaY);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, healthPoint, goldCost, woodCost, areaX, areaY);
    }
}
