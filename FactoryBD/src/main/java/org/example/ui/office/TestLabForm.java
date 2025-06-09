package org.example.ui.office;

import org.example.dao.BaseDao;
import org.example.model.TestLab;
import org.example.ui.BaseForm;

import javax.swing.*;
import java.awt.*;

public class TestLabForm extends BaseForm {
    private TestLab model;
    private final JTextField nameField;

    public TestLabForm(JFrame parent, TestLab model_) {
        super(parent, "TestLab Form", true);
        this.model = model_ != null ? model_ : new TestLab();

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        setSize(400, 300);

        panel.add(new JLabel("Title:"));
        nameField = new JTextField(model.getTitle());
        panel.add(nameField);

        BaseDao<TestLab> dao = new BaseDao<>(TestLab.class);

        // Кнопки
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
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