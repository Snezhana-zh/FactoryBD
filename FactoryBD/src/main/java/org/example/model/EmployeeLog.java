package org.example.model;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "employee_logs")
public class EmployeeLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

    @Enumerated(EnumType.STRING)
    @Column(name = "position", nullable = false)
    private EmployeePosition position;

    @Column(name = "start_work", nullable = false)
    private LocalDateTime startWork;

    @Column(name = "end_work")
    private LocalDateTime endWork;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }
    public EmployeePosition getPosition() { return position; }
    public void setPosition(EmployeePosition position) { this.position = position; }
    public LocalDateTime getStartWork() { return startWork; }
    public void setStartWork(LocalDateTime startWork) { this.startWork = startWork; }
    public LocalDateTime getEndWork() { return endWork; }
    public void setEndWork(LocalDateTime endWork) { this.endWork = endWork; }
}
