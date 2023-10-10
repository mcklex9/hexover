package org.hexagonexample.adapters.outbound.common;

import org.bson.BsonDocument;
import org.hexagonexample.AccountsSpringApplication;
import org.hexagonexample.adapters.outbound.MongoConfig;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@DataMongoTest
@ContextConfiguration(classes = {AccountsSpringApplication.class, MongoConfig.class})
public class MongoRepositoryTest {

    @Rule
    protected static final MongoDBContainer MONGO_DB = SharedTestMongoContainer.getInstance();

    private static final BsonDocument DOCUMENT = new BsonDocument();

    @Autowired
    protected MongoTemplate mongoTemplate;

    @BeforeEach
    void cleanup() {
        mongoTemplate.getCollectionNames().forEach(this::clearCollection);
    }

    private void clearCollection(String collectionName) {
        mongoTemplate.getCollection(collectionName)
                .deleteMany(DOCUMENT);
    }


}
