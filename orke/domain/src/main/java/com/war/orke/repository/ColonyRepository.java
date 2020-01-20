package com.war.orke.repository;

import com.war.orke.entity.Colony;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ColonyRepository extends CrudRepository<Colony, Integer> {

    Optional<Colony> findByName(String name);

    @Modifying
    @Query("DELETE FROM com.war.orke.entity.Colony col WHERE col.name = :colonyName")
    void deleteColonyByName(@Param("colonyName") String colonyName);
}
