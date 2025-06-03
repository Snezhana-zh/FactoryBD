package org.example.model;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "product")
public class Product extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "model_id", referencedColumnName = "id")
    private ProductModel model;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductLog> logs = new HashSet<>();

    @Override
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public ProductModel getModel() { return model; }
    public void setModel(ProductModel model) { this.model = model; }
}
