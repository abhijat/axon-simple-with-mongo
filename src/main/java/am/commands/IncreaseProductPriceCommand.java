package am.commands;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class IncreaseProductPriceCommand {

    @TargetAggregateIdentifier
    private final String id;
    private final double newPrice;

    public IncreaseProductPriceCommand(String id, double newPrice) {
        this.id = id;
        this.newPrice = newPrice;
    }

    public String getId() {
        return id;
    }

    public double getNewPrice() {
        return newPrice;
    }
}
