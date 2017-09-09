package am.aggregates;

import am.commands.CreateProductCommand;
import am.commands.IncreaseProductPriceCommand;
import am.events.ProductCreatedEvent;
import am.events.ProductPriceIncreasedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventhandling.EventHandler;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

public class ProductAggregate {

    @AggregateIdentifier
    private String id;

    private String name;
    private double price;

    public ProductAggregate() {
    }

    @CommandHandler
    public ProductAggregate(final CreateProductCommand command) {
        apply(new ProductCreatedEvent(command.getId(), command.getName(), command.getPrice()));
    }

    @CommandHandler
    public void handle(final IncreaseProductPriceCommand command) {
        apply(new ProductPriceIncreasedEvent(command.getId(), command.getNewPrice()));
    }

    @EventHandler
    public void on(final ProductCreatedEvent event) {
        this.id = event.getId();
    }

    @EventHandler
    public void on(final ProductPriceIncreasedEvent event) {
    }

}
