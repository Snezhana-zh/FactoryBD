package org.example.ui.employee;

import org.example.dao.BaseDao;
import org.example.model.Employee;
import org.example.model.TestLab;
import org.example.model.Tester;
import org.example.ui.BaseForm;

import javax.swing.*;
import java.awt.*;

public class TestersForm extends BaseForm {
    private Tester model;
    private final JComboBox<Employee> employeeCombo;
    private final JComboBox<TestLab> testlabCombo;

    public TestersForm(JFrame parent, Tester model_) {
        super(parent, "Worker Form", true);
        this.model = model_ != null ? model_ : new Tester();

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        setSize(400, 300);

        BaseDao<Employee> employeeDao = new BaseDao<>(Employee.class);
        BaseDao<TestLab> brigadeDao = new BaseDao<>(TestLab.class);

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
        testlabCombo = new JComboBox<>();
        testlabCombo.addItem(null);
        for (TestLab tl : brigadeDao.getAll()) {
            testlabCombo.addItem(tl);
        }
        testlabCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof TestLab) {
                    setText(((TestLab) value).getId().toString());
                } else {
                    setText("None");
                }
                return this;
            }
        });

        testlabCombo.setSelectedItem(model.getTestLab());

        panel.add(testlabCombo);

        BaseDao<Tester> dao = new BaseDao<>(Tester.class);

        // Кнопки
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            model.setEmployee((Employee) employeeCombo.getSelectedItem());
            model.setTestLab((TestLab) testlabCombo.getSelectedItem());

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