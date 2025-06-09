package org.example.model;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "test_lab")
public class TestLab extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "testLab", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TestLabEquipment> equipment = new HashSet<>();

    @ManyToMany(mappedBy = "testLabs")
    private Set<Department> departments = new HashSet<>();

    @OneToMany(mappedBy = "testLab", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Tester> testers = new HashSet<>();

    @OneToMany(mappedBy = "testlab", cascade = CascadeType.ALL)
    private Set<ProductLog> productLogs = new HashSet<>();

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    // Other getters and setters...
}