package am.configuration;

import am.aggregates.ProductAggregate;
import com.mongodb.MongoClient;
import org.axonframework.commandhandling.AggregateAnnotationCommandHandler;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.mongo.eventsourcing.eventstore.DefaultMongoTemplate;
import org.axonframework.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.mongo.eventsourcing.eventstore.MongoTemplate;
import org.axonframework.mongo.eventsourcing.eventstore.documentpercommit.DocumentPerCommitStorageStrategy;
import org.axonframework.mongo.eventsourcing.eventstore.documentperevent.DocumentPerEventStorageStrategy;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Bean
    public Serializer serializer() {
        return new JacksonSerializer();
    }

    @Bean
    public MongoClient mongo() {
        return new MongoClient("127.0.0.1", 27017);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new DefaultMongoTemplate(mongo(), "mydb", "myevents", "mysnapshots");
    }

    @Bean
    public EventStorageEngine eventStorageEngine() {
        return new MongoEventStorageEngine(serializer(), null, mongoTemplate, new DocumentPerCommitStorageStrategy());
    }

    @Bean
    public EventStore eventStore() {
        return new EmbeddedEventStore(eventStorageEngine());
    }

    @Bean
    public EventSourcingRepository<ProductAggregate> repository() {
        return new EventSourcingRepository<>(ProductAggregate.class, eventStore());
    }

    @Bean
    public AggregateAnnotationCommandHandler<ProductAggregate> handler() {
        return new AggregateAnnotationCommandHandler<>(ProductAggregate.class, repository());
    }

}
