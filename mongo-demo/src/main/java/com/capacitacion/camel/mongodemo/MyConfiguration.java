package com.capacitacion.camel.mongodemo;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.apache.camel.BindToRegistry;
import org.apache.camel.Configuration;
import org.apache.camel.PropertyInject;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Class to configure the Camel application.
 */
@Configuration
public class MyConfiguration {

    @BindToRegistry("mongoClient")
    public MongoClient createMongoClient(@PropertyInject("mongo.user") String user, @PropertyInject("mongo.password") String password,
                                         @PropertyInject("mongo.database") String database, @PropertyInject("mongo.host") String host,
                                         @PropertyInject("mongo.port") int port){
        MongoCredential credential = MongoCredential.createCredential(user, database, password.toCharArray());

        MongoClientSettings settings = MongoClientSettings.builder()
                .credential(credential)
                .applyToSocketSettings(builder -> builder.readTimeout(30000, TimeUnit.MILLISECONDS)
                        .connectTimeout(30000, TimeUnit.MILLISECONDS))
                .applyToClusterSettings(builder ->
                        builder.hosts(List.of(new ServerAddress(host, port))))
                .build();

        return MongoClients.create(settings);
    }

}
