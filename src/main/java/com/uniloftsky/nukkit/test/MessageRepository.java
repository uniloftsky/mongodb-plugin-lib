package com.uniloftsky.nukkit.test;

import com.uniloftsky.nukkit.database.AbstractMongoCrudRepository;

public class MessageRepository extends AbstractMongoCrudRepository<Message> {

    private static MessageRepository INSTANCE;

    private MessageRepository(String collectionString) {
        super(collectionString);
    }

    public static MessageRepository getInstance(String collectionString) {
        if (INSTANCE == null) {
            INSTANCE = new MessageRepository(collectionString);
        }
        return INSTANCE;
    }
}
