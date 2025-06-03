package org.example.model;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "product_logs")
public class ProductLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Column(name = "start_work", nullable = false)
    private LocalDateTime startWork;

    @Column(name = "end_work")
    private LocalDateTime endWork;

    @ManyToOne
    @JoinColumn(name = "workshop_id", referencedColumnName = "id")
    private Workshop workshop;

    @ManyToOne
    @JoinColumn(name = "testlab_id", referencedColumnName = "id")
    private TestLab testlab;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public LocalDateTime getStartWork() { return startWork; }
    public void setStartWork(LocalDateTime startWork) { this.startWork = startWork; }
    public LocalDateTime getEndWork() { return endWork; }
    public void setEndWork(LocalDateTime endWork) { this.endWork = endWork; }
    public Workshop getWorkshop() { return workshop; }
    public void setWorkshop(Workshop workshop) { this.workshop = workshop; }
    public TestLab getTestlab() { return testlab; }
    public void setTestlab(TestLab testlab) { this.testlab = testlab; }
}
