package com.war.alliance.repository;

import com.war.alliance.document.MilitaryBuilding;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MilitaryBuildingRepository extends ReactiveMongoRepository<MilitaryBuilding, ObjectId> {
}