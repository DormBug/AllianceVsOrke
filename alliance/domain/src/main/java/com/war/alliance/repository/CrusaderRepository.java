package com.war.alliance.repository;

import com.war.alliance.document.Crusader;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrusaderRepository extends ReactiveMongoRepository<Crusader, ObjectId> {
}