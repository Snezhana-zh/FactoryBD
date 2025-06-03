package org.example.model;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "workshop")
public class Workshop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;

    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(name = "head", referencedColumnName = "id")
    private Employee head;

    @OneToMany(mappedBy = "workshop", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Brigade> brigades = new HashSet<>();

    @OneToMany(mappedBy = "workshop", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Engineer> engineers = new HashSet<>();

    @OneToMany(mappedBy = "workshop", cascade = CascadeType.ALL)
    private Set<ProductLog> productLogs = new HashSet<>();

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Employee getHead() { return head; }
    public void setHead(Employee head) { this.head = head; }
    // Other getters and setters...
}