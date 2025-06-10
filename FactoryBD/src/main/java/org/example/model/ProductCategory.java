package org.example.model;
import javax.persistence.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "product_category")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class ProductCategory extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

//    @Type(type = "jsonb")
//    @Column(name = "attr", columnDefinition = "jsonb")
//    private Map<String, Object> attributes;
    @Type(type = "jsonb")
    @Column(name = "attr", columnDefinition = "jsonb")
    private String attributes;


    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductModel> models = new HashSet<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
//   public Map<String, Object> getAttributes() { return attributes; }
//    public void setAttributes(Map<String, Object> attributes) { this.attributes = attributes; }
    public String getAttributes() { return attributes; }
    public void setAttributes(String attributes) { this.attributes = attributes; }
    public Set<ProductModel> getModels() {
        return models;
    }
    public void setModels(Set<ProductModel> models) {
        this.models = models;
    }
}
