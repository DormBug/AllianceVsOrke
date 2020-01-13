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
public class BuildingArmor {

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
    private Integer arrowsArmor;

    @Column(nullable = false, columnDefinition = "decimal", length = 2)
    private Integer spearsArmor;

    @Column(nullable = false, columnDefinition = "decimal", length = 2)
    private Integer swordArmor;

    @Column(nullable = false, columnDefinition = "decimal", length = 2)
    private Integer catapultsArmor;

    public UUID getId() {
        return id;
    }

    public Integer getArrowsArmor() {
        return arrowsArmor;
    }

    public void setArrowsArmor(Integer arrowsArmor) {
        this.arrowsArmor = arrowsArmor;
    }

    public Integer getSpearsArmor() {
        return spearsArmor;
    }

    public void setSpearsArmor(Integer spearsArmor) {
        this.spearsArmor = spearsArmor;
    }

    public Integer getSwordArmor() {
        return swordArmor;
    }

    public void setSwordArmor(Integer swordArmor) {
        this.swordArmor = swordArmor;
    }

    public Integer getCatapultsArmor() {
        return catapultsArmor;
    }

    public void setCatapultsArmor(Integer catapultsArmor) {
        this.catapultsArmor = catapultsArmor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuildingArmor that = (BuildingArmor) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(arrowsArmor, that.arrowsArmor) &&
                Objects.equals(spearsArmor, that.spearsArmor) &&
                Objects.equals(swordArmor, that.swordArmor) &&
                Objects.equals(catapultsArmor, that.catapultsArmor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, arrowsArmor, spearsArmor, swordArmor, catapultsArmor);
    }
}
