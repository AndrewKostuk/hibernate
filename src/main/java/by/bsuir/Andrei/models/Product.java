package by.bsuir.Andrei.models;

import javax.persistence.*;

@Entity
@Table(name = "purchase")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "color")
    private String color;

    @Column(name = "cost")
    private Integer cost;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    public Product(String name, String color, Integer cost) {
        this.name = name;
        this.color = color;
        this.cost = cost;
    }

    public Product() {
    }

    public Product(Integer id, String name, String color, Integer cost, User owner) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.cost = cost;
        this.owner = owner;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
