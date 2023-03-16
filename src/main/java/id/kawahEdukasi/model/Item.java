package id.kawahEdukasi.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity (name =  "kawahEdukasi")
@Table(name = "item")
public class Item extends PanacheEntityBase {

    @Id
    @SequenceGenerator(name = "ItemSequence", sequenceName = "Item_sequence", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "ItemSequence", strategy = GenerationType.SEQUENCE)

    @Column(name = "id", nullable = false)
    public Long id;

    @Column(name = "name")
    public String name;

    @Column(name = "price")
    public Integer price;

    @Column(name = "type")
    public String type;

    @Column(name = "description")
    public String description;

    @Column(name = "count")
    public Integer count;

    @CreationTimestamp
    @Column(name = "created_at")
    public LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    public LocalDateTime updatedAt;

}