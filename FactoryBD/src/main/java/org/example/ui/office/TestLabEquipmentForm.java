package org.example.ui.office;

import org.example.dao.BaseDao;
import org.example.model.TestLab;
import org.example.model.TestLabEquipment;
import org.example.ui.BaseForm;

import javax.swing.*;
import java.awt.*;

public class TestLabEquipmentForm extends BaseForm {
    private TestLabEquipment model;
    private final JTextField nameField;
    private final JComboBox<TestLab> testLabCombo;

    public TestLabEquipmentForm(JFrame parent, TestLabEquipment model_) {
        super(parent, "TestLab Equipment Form", true);
        this.model = model_ != null ? model_ : new TestLabEquipment();

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        setSize(400, 300);

        panel.add(new JLabel("Title:"));
        nameField = new JTextField(model.getTitle());
        panel.add(nameField);

        BaseDao<TestLab> testLabDao = new BaseDao<>(TestLab.class);

        // Employee selection
        panel.add(new JLabel("TestLab:"));
        testLabCombo = new JComboBox<>();
        for (TestLab tl : testLabDao.getAll()) {
            testLabCombo.addItem(tl);
        }
        testLabCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof TestLab) {
                    setText(((TestLab) value).getTitle());
                }
                else {
                    setText("None");
                }
                return this;
            }
        });
        testLabCombo.setSelectedItem(model.getTestLab());
        panel.add(testLabCombo);


        BaseDao<TestLabEquipment> dao = new BaseDao<>(TestLabEquipment.class);

        // Кнопки
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            model.setTestLab((TestLab) testLabCombo.getSelectedItem());
            model.setTitle(nameField.getText());

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