package org.hexagonexample.adapters.outbound.common;

import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

public class SharedTestMongoContainer extends MongoDBContainer {

    private static final String IMAGE_VERSION = "mongo:6.0.1";

    private static SharedTestMongoContainer MONGO_CONTAINER;

    private SharedTestMongoContainer() {
        super(DockerImageName.parse(IMAGE_VERSION));
    }

    static SharedTestMongoContainer getInstance() {
        if (MONGO_CONTAINER == null) {
            synchronized (SharedTestMongoContainer.class) {
                MONGO_CONTAINER = new SharedTestMongoContainer();
                MONGO_CONTAINER.start();
            }
        }
        return MONGO_CONTAINER;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("spring.data.mongodb.uri", getReplicaSetUrl());
    }

}