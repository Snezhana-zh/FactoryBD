package org.example.ui.office;

import org.example.dao.BaseDao;
import org.example.model.Company;
import org.example.model.Department;
import org.example.model.Employee;
import org.example.model.Engineer;
import org.example.ui.BaseForm;

import javax.swing.*;
import java.awt.*;

public class DepartmentForm extends BaseForm {
    private Department model;
    private final JTextField nameField;
    private final JComboBox<Employee> employeeCombo;
    private final JComboBox<Company> workshopCombo;

    public DepartmentForm(JFrame parent, Department model_) {
        super(parent, "Department Form", true);
        this.model = model_ != null ? model_ : new Department();

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        setSize(400, 300);

        panel.add(new JLabel("Name:"));
        nameField = new JTextField(model.getName());
        panel.add(nameField);

        BaseDao<Engineer> employeeDao = new BaseDao<>(Engineer.class);
        BaseDao<Company> workshopDao = new BaseDao<>(Company.class);

        // Employee selection
        panel.add(new JLabel("Head:"));
        employeeCombo = new JComboBox<>();
        for (Engineer emp : employeeDao.getAll()) {
            employeeCombo.addItem(emp.getEmployee());
        }
        employeeCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Employee) {
                    setText(((Employee) value).getName());
                }
                return this;
            }
        });
        employeeCombo.setSelectedItem(model.getHead());
        panel.add(employeeCombo);

        // Company selection
        panel.add(new JLabel("Company:"));
        workshopCombo = new JComboBox<>();
        workshopCombo.addItem(null);
        for (Company ws : workshopDao.getAll()) {
            workshopCombo.addItem(ws);
        }
        workshopCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Company) {
                    setText(((Company) value).getName());
                } else {
                    setText("None");
                }
                return this;
            }
        });

        workshopCombo.setSelectedItem(model.getCompany());

        panel.add(workshopCombo);

        BaseDao<Department> dao = new BaseDao<>(Department.class);

        // Кнопки
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            model.setHead((Employee) employeeCombo.getSelectedItem());
            model.setCompany((Company) workshopCombo.getSelectedItem());
            model.setName(nameField.getText());

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