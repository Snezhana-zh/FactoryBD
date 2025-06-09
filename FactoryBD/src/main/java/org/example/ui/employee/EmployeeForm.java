package org.example.ui.employee;

import org.example.dao.BaseDao;
import org.example.model.Employee;
import org.example.model.EmployeePosition;
import org.example.ui.BaseForm;

import javax.swing.*;
import java.awt.*;

public class EmployeeForm extends BaseForm {
    private Employee model;
    private final JTextField nameField;
    private final JComboBox<EmployeePosition> positionCombo;
    public EmployeeForm(JFrame parent, Employee model_) {
        super(parent, "Category Form", true);
        this.model = model_ != null ? model_ : new Employee();

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        setSize(400, 300);

        panel.add(new JLabel("Name:"));
        nameField = new JTextField(model.getName());
        panel.add(nameField);

        panel.add(new JLabel("Position:"));
        positionCombo = new JComboBox<>(EmployeePosition.values());
        if (this.model.getPosition() != null) {
            positionCombo.setSelectedItem(this.model.getPosition());
        }
        panel.add(positionCombo);

        BaseDao<Employee> dao = new BaseDao<>(Employee.class);

        // Кнопки
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            model.setName(nameField.getText());
            model.setPosition((EmployeePosition) positionCombo.getSelectedItem());

            if (model.getId() == null) {
                dao.save(model);
            } else {
                dao.update(model);
            }

            dispose();
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());

        panel.add(cancelButton);
        panel.add(saveButton);
        add(panel);
        pack();
        setLocationRelativeTo(parent);
    }
}