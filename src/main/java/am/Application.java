package am;

import am.commands.CreateProductCommand;
import am.commands.IncreaseProductPriceCommand;
import am.jms.messages.Email;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.DomainEventStream;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.core.JmsTemplate;

import java.util.UUID;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class.getName());

    private final CommandGateway commandGateway;

    private final EventStore eventStore;

    private final ConfigurableApplicationContext ctx;

    @Autowired
    public Application(CommandGateway commandGateway, EventStore eventStore, ConfigurableApplicationContext ctx) {
        this.commandGateway = commandGateway;
        this.eventStore = eventStore;
        this.ctx = ctx;
    }

    public static void main(String[] args) {
        ApplicationContext c = SpringApplication.run(Application.class, args);
    }

    private void runAxonApp() {
        final String id = UUID.randomUUID().toString();

        commandGateway.send(new CreateProductCommand(id, "dabur lal dunt manjan", 100.0));
        commandGateway.send(new IncreaseProductPriceCommand(id, 200.0));

        DomainEventStream s = eventStore.readEvents(id);
        s.asStream().forEach(m -> LOGGER.info("found domain event message: {}", m.getPayload()));
    }

    private void testJms() {
        JmsTemplate j = ctx.getBean(JmsTemplate.class);
        LOGGER.info("sending an email message");
        j.convertAndSend("mailbox", new Email("hello", "hello"));
    }

    @Override
    public void run(String... args) throws Exception {
        testJms();
        ctx.close();
    }


}
