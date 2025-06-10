package org.example.ui.office;

import org.example.dao.BaseDao;
import org.example.model.*;
import org.example.ui.BaseForm;

import javax.swing.*;
import java.awt.*;

public class WorkshopForm extends BaseForm {
    private Workshop model;
    private final JComboBox<Employee> employeeCombo;
    private final JComboBox<Department> departmentCombo;
    private final JTextField nameField;

    public WorkshopForm(JFrame parent, Workshop model_) {
        super(parent, "Workshop Form", true);
        this.model = model_ != null ? model_ : new Workshop();

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        setSize(400, 300);

        panel.add(new JLabel("Name:"));
        nameField = new JTextField(model.getName());
        panel.add(nameField);

        BaseDao<Engineer> employeeDao = new BaseDao<>(Engineer.class);
        BaseDao<Department> departmentDao = new BaseDao<>(Department.class);

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

        // Workshop selection
        panel.add(new JLabel("Department:"));
        departmentCombo = new JComboBox<>();
        departmentCombo.addItem(null);
        for (Department dp : departmentDao.getAll()) {
            departmentCombo.addItem(dp);
        }
        departmentCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Department) {
                    setText(((Department) value).getName());
                } else {
                    setText("None");
                }
                return this;
            }
        });

        departmentCombo.setSelectedItem(model.getDepartment());

        panel.add(departmentCombo);

        BaseDao<Workshop> dao = new BaseDao<>(Workshop.class);

        // Кнопки
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            model.setHead((Employee) employeeCombo.getSelectedItem());
            model.setDepartment((Department) departmentCombo.getSelectedItem());
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