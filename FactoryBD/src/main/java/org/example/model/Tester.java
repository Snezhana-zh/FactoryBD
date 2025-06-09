package org.example.model;
import javax.persistence.*;

@Entity
@Table(name = "testers")
public class Tester extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "test_lab_id", referencedColumnName = "id")
    private TestLab testLab;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }
    public TestLab getTestLab() { return testLab; }
    public void setTestLab(TestLab testLab) { this.testLab = testLab; }
}
