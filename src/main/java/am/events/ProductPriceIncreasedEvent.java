package am.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductPriceIncreasedEvent {

    private final String id;
    private final double newPrice;

    @JsonCreator
    public ProductPriceIncreasedEvent(
            @JsonProperty("id") String id,
            @JsonProperty("newPrice") double newPrice
    ) {
        this.id = id;
        this.newPrice = newPrice;
    }

    public String getId() {
        return id;
    }

    public double getNewPrice() {
        return newPrice;
    }

    @Override
    public String toString() {
        return "ProductPriceIncreasedEvent{" +
                "id='" + id + '\'' +
                ", newPrice=" + newPrice +
                '}';
    }
}
