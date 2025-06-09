package org.example.ui.employee;

import org.example.dao.BaseDao;
import org.example.model.Brigade;
import org.example.model.Employee;
import org.example.model.Worker;
import org.example.ui.BaseForm;

import javax.swing.*;
import java.awt.*;

public class WorkersForm extends BaseForm {
    private Worker model;
    private final JComboBox<Employee> employeeCombo;
    private final JComboBox<Brigade> brigadeCombo;

    public WorkersForm(JFrame parent, Worker model_) {
        super(parent, "Worker Form", true);
        this.model = model_ != null ? model_ : new Worker();

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        setSize(400, 300);

        BaseDao<Employee> employeeDao = new BaseDao<>(Employee.class);
        BaseDao<Brigade> brigadeDao = new BaseDao<>(Brigade.class);

        // Employee selection
        panel.add(new JLabel("Employee:"));
        employeeCombo = new JComboBox<>();
        for (Employee emp : employeeDao.getAll()) {
            employeeCombo.addItem(emp);
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
        employeeCombo.setSelectedItem(model.getEmployee());
        panel.add(employeeCombo);

        // Brigade selection
        panel.add(new JLabel("Brigade:"));
        brigadeCombo = new JComboBox<>();
        brigadeCombo.addItem(null);
        for (Brigade br : brigadeDao.getAll()) {
            brigadeCombo.addItem(br);
        }
        brigadeCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Brigade) {
                    setText(((Brigade) value).getId().toString());
                } else {
                    setText("None");
                }
                return this;
            }
        });

        brigadeCombo.setSelectedItem(model.getBrigade());

        panel.add(brigadeCombo);

        BaseDao<Worker> dao = new BaseDao<>(Worker.class);

        // Кнопки
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            model.setEmployee((Employee) employeeCombo.getSelectedItem());
            model.setBrigade((Brigade) brigadeCombo.getSelectedItem());

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