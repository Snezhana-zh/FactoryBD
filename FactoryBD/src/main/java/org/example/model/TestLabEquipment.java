package org.example.model;
import javax.persistence.*;

@Entity
@Table(name = "test_lab_equipment")
public class TestLabEquipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "test_lab_id", referencedColumnName = "id")
    private TestLab testLab;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public TestLab getTestLab() { return testLab; }
    public void setTestLab(TestLab testLab) { this.testLab = testLab; }
}