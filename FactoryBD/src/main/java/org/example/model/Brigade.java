package org.example.model;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "brigade")
public class Brigade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "head", referencedColumnName = "id")
    private Employee head;

    @ManyToOne
    @JoinColumn(name = "workshop_id", referencedColumnName = "id")
    private Workshop workshop;

    @OneToMany(mappedBy = "brigade", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Worker> workers = new HashSet<>();

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Employee getHead() { return head; }
    public void setHead(Employee head) { this.head = head; }
    public Workshop getWorkshop() { return workshop; }
    public void setWorkshop(Workshop workshop) { this.workshop = workshop; }
    // Other getters and setters...
}
