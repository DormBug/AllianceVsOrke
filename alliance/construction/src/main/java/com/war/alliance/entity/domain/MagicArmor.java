package com.war.alliance.entity.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@Entity
public class MagicArmor {

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

    @Column(nullable = false, columnDefinition = "decimal", length = 2)
    private Integer fireArmor;

    @Column(nullable = false, columnDefinition = "decimal", length = 2)
    private Integer coldArmor;

    @Column(nullable = false, columnDefinition = "decimal", length = 2)
    private Integer earthArmor;

    @Column(nullable = false, columnDefinition = "decimal", length = 2)
    private Integer waterArmor;

    public UUID getId() {
        return id;
    }

    public Integer getFireArmor() {
        return fireArmor;
    }

    public void setFireArmor(Integer fireArmor) {
        this.fireArmor = fireArmor;
    }

    public Integer getColdArmor() {
        return coldArmor;
    }

    public void setColdArmor(Integer coldArmor) {
        this.coldArmor = coldArmor;
    }

    public Integer getEarthArmor() {
        return earthArmor;
    }

    public void setEarthArmor(Integer earthArmor) {
        this.earthArmor = earthArmor;
    }

    public Integer getWaterArmor() {
        return waterArmor;
    }

    public void setWaterArmor(Integer waterArmor) {
        this.waterArmor = waterArmor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MagicArmor that = (MagicArmor) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(fireArmor, that.fireArmor) &&
                Objects.equals(coldArmor, that.coldArmor) &&
                Objects.equals(earthArmor, that.earthArmor) &&
                Objects.equals(waterArmor, that.waterArmor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fireArmor, coldArmor, earthArmor, waterArmor);
    }
}
