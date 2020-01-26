package com.war.alliance.document;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document("crusader")
public class Crusader {

    private ObjectId id;

    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Crusader crusader = (Crusader) o;
        return Objects.equals(id, crusader.id) &&
                Objects.equals(name, crusader.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}