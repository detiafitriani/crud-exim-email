package model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

@Entity
@Table(name = "Item")
public class Item extends PanacheEntityBase {
    @Id
    @SequenceGenerator(name = "ItemSequence", sequenceName = "Item_sequence", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "ItemSequence", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    public Long id;

    @Column(name = "name")
    public String name;

    @Column(name = "count")
    public Integer count;

    @Column(name = "price")
    public Double price;

    @Column(name = "type")
    public String type;

    @Column(name = "description")
    public String description;

    @Column(name = "created_at")
    public String createdAt;

    @Column(name = "updated_at")
    public String updatedAt;
}
