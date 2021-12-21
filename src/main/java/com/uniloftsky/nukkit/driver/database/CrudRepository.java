package com.uniloftsky.nukkit.driver.database;

import org.bson.types.ObjectId;

import java.util.Collection;
import java.util.Optional;

public interface CrudRepository<T> {

    Collection<T> findAll();

    Optional<T> findById(ObjectId id);

    <R> Optional<T> findByCriterion(String field, R criterion);

    T save(T object);

    Collection<T> saveAll(Collection<T> collection);

    void delete(T object);

    <R> void deleteByCriterion(String field, R criterion);

}
