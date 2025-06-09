package org.example.model;
import javax.persistence.*;
@Entity
@Table(name = "workers")
public class Worker extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "brigade_id", referencedColumnName = "id")
    private Brigade brigade;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }
    public Brigade getBrigade() { return brigade; }
    public void setBrigade(Brigade brigade) { this.brigade = brigade; }
}