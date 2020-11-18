package com.teamvoy.demo.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "[order]")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "price", nullable = false)
    private double price;
    @Column(name = "quantity", nullable = false)
    private int quantity;
    @Column(name = "item", nullable = false, updatable = false)
    private String item;

    public Order() {
    }

    public Order(double price, int quantity, String item) {
        this.price = price;
        this.quantity = quantity;
        this.item = item;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getItem() {
        return item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id &&
                Double.compare(order.price, price) == 0 &&
                quantity == order.quantity &&
                Objects.equals(item, order.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, quantity, item);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", price=" + price +
                ", quantity=" + quantity +
                ", item='" + item + '\'' +
                '}';
    }
}
