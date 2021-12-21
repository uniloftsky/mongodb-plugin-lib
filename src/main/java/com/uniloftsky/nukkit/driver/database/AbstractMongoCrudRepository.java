package com.uniloftsky.nukkit.driver.database;


import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;
import com.uniloftsky.nukkit.driver.MongoDB;
import com.uniloftsky.nukkit.driver.database.model.BaseEntity;
import org.bson.types.ObjectId;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("unchecked")
public abstract class AbstractMongoCrudRepository<T extends BaseEntity> {

    protected final MongoCollection<T> collection;

    public AbstractMongoCrudRepository(String collectionString) {
        Class<T> type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        collection = MongoDB.getDatabase().getCollection(collectionString, type);
    }

    public Collection<T> findAll() {
        List<T> list = new ArrayList<>();
        collection.find().forEach(list::add);
        return list;
    }

    public Optional<T> findById(ObjectId id) {
        return getRecordOptional("_id", id);
    }

    public <R> Optional<T> findByCriterion(String field, R criterion) {
        return getRecordOptional(field, criterion);
    }

    public T save(T object) {
        if (!findById(object.getId()).isPresent()) {
            object.setId(new ObjectId());
            InsertOneResult result = collection.insertOne(object);
            return findById(result.getInsertedId().asObjectId().getValue()).get();
        } else {
            collection.replaceOne(Filters.eq("_id", object.getId()), object);
            return findById(object.getId()).get();
        }
    }

    public Collection<T> saveAll(Collection<T> collection) {
        List<T> outputList = new ArrayList<>();
        for (T object : collection) {
            outputList.add(save(object));
        }
        return outputList;
    }

    public void delete(T object) {
        collection.deleteOne(Filters.eq("_id", object.getId()));
    }

    public <R> void deleteByCriterion(String field, R criterion) {
        collection.deleteMany(Filters.eq(field, criterion));
    }

    private <R> Optional<T> getRecordOptional(String field, R criterion) {
        return Optional.ofNullable(collection.find(Filters.eq(field, criterion)).first());
    }

}
