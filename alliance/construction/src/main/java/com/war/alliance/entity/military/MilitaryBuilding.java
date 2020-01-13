package com.war.alliance.entity.military;

import com.war.alliance.construction.BuildingLevel;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@Entity
public class MilitaryBuilding {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, columnDefinition = "char", length = 20, unique = true, updatable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "decimal", length = 1)
    private BuildingLevel level;

    @Column(nullable = false, columnDefinition = "decimal", length = 4)
    private Integer healthPoint;

    @Column(nullable = false, columnDefinition = "decimal", length = 3, updatable = false)
    private Integer goldCost;

    @Column(nullable = false, columnDefinition = "decimal", length = 3, updatable = false)
    private Integer woodCost;

    @Column(nullable = false, columnDefinition = "decimal", length = 2, updatable = false)
    private Integer areaX;

    @Column(nullable = false, columnDefinition = "decimal", length = 2, updatable = false)
    private Integer areaY;

    public UUID getId() {
        return id;
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

    public BuildingLevel getLevel() {
        return level;
    }

    public void setLevel(BuildingLevel level) {
        this.level = level;
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
                level == that.level &&
                Objects.equals(healthPoint, that.healthPoint) &&
                Objects.equals(goldCost, that.goldCost) &&
                Objects.equals(woodCost, that.woodCost) &&
                Objects.equals(areaX, that.areaX) &&
                Objects.equals(areaY, that.areaY);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, level, healthPoint, goldCost, woodCost, areaX, areaY);
    }
}
