package com.war.orke.repository;

import com.war.orke.entity.Colony;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColonyRepository extends CrudRepository<Colony, Integer> {
}
