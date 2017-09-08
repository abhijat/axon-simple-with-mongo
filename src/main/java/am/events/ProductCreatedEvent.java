package am.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductCreatedEvent {

    private final String id;
    private final String name;
    private final double price;

    @JsonCreator
    public ProductCreatedEvent(
            @JsonProperty("id") String id,
            @JsonProperty("name") String name,
            @JsonProperty("price") double price
    ) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "ProductCreatedEvent{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
