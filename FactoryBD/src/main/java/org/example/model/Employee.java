package org.example.model;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "employee")
public class Employee extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "position", columnDefinition = "employee_position", nullable = false)
    private EmployeePosition position;

    @OneToOne(mappedBy = "head")
    private Department headedDepartment;

    @OneToOne(mappedBy = "head")
    private Workshop headedWorkshop;

    @OneToOne(mappedBy = "head")
    private Brigade headedBrigade;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private Worker worker;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private Engineer engineer;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private Tester tester;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EmployeeLog> logs = new HashSet<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public EmployeePosition getPosition() { return position; }
    public void setPosition(EmployeePosition position) { this.position = position; }
}