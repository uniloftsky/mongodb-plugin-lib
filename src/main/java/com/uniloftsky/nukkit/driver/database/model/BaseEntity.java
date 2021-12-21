package com.uniloftsky.nukkit.driver.database.model;

import lombok.*;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public abstract class BaseEntity {

    @BsonId
    private ObjectId id;

}
