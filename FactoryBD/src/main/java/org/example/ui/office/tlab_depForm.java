package org.example.ui.office;

import org.example.dao.BaseDao;
import org.example.ui.BaseForm;

import javax.swing.*;
import java.awt.*;

public class tlab_depForm  {
//    private Brigade model;
//    private final JComboBox<Employee> employeeCombo;
//    private final JComboBox<Workshop> workshopCombo;
//
//    public tlab_depForm(JFrame parent, Brigade model_) {
//        super(parent, "Brigade Form", true);
//        this.model = model_ != null ? model_ : new Brigade();
//
//        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
//        setSize(400, 300);
//
//        BaseDao<Worker> employeeDao = new BaseDao<>(Worker.class);
//        BaseDao<Workshop> workshopDao = new BaseDao<>(Workshop.class);
//
//        // Employee selection
//        panel.add(new JLabel("Head:"));
//        employeeCombo = new JComboBox<>();
//        for (Worker emp : employeeDao.getAll()) {
//            employeeCombo.addItem(emp.getEmployee());
//        }
//        employeeCombo.setRenderer(new DefaultListCellRenderer() {
//            @Override
//            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
//                                                          boolean isSelected, boolean cellHasFocus) {
//                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
//                if (value instanceof Employee) {
//                    setText(((Employee) value).getName());
//                }
//                return this;
//            }
//        });
//        employeeCombo.setSelectedItem(model.getHead());
//        panel.add(employeeCombo);
//
//        // Workshop selection
//        panel.add(new JLabel("Workshop:"));
//        workshopCombo = new JComboBox<>();
//        workshopCombo.addItem(null);
//        for (Workshop ws : workshopDao.getAll()) {
//            workshopCombo.addItem(ws);
//        }
//        workshopCombo.setRenderer(new DefaultListCellRenderer() {
//            @Override
//            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
//                                                          boolean isSelected, boolean cellHasFocus) {
//                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
//                if (value instanceof Workshop) {
//                    setText(((Workshop) value).getId().toString());
//                } else {
//                    setText("None");
//                }
//                return this;
//            }
//        });
//
//        workshopCombo.setSelectedItem(model.getWorkshop());
//
//        panel.add(workshopCombo);
//
//        BaseDao<Brigade> dao = new BaseDao<>(Brigade.class);
//
//        // Кнопки
//        JButton saveButton = new JButton("Save");
//        saveButton.addActionListener(e -> {
//            model.setHead((Employee) employeeCombo.getSelectedItem());
//            model.setWorkshop((Workshop) workshopCombo.getSelectedItem());
//
//            if (model.getId() == null) {
//                dao.save(model);
//            } else {
//                dao.update(model);
//            }
//
//            dispose();
//        });
//
//        JButton cancelButton = new JButton("Cancel");
//        cancelButton.addActionListener(e -> dispose());
//
//        panel.add(cancelButton);
//        panel.add(saveButton);
//        add(panel);
//        pack();
//        setLocationRelativeTo(parent);
//    }
}