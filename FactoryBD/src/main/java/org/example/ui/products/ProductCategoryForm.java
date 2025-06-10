package org.example.ui.products;

import org.example.dao.BaseDao;
import org.example.model.ProductCategory;
import org.example.ui.BaseForm;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ProductCategoryForm extends BaseForm {
    private JTextField titleField;
    private JTextArea attributesArea;

    private ProductCategory model;
    public ProductCategoryForm(JFrame parent, ProductCategory model_) {
        super(parent, "Category Form", true);
        this.model = model_ != null ? model_ : new ProductCategory();

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        setSize(400, 300);

        panel.add(new JLabel("Title:"));
        titleField = new JTextField(model.getTitle());
        panel.add(titleField);

        panel.add(new JLabel("Attributes (JSON):"));
        if (model.getAttributes() != null) attributesArea = new JTextArea(model.getAttributes().toString());
        else attributesArea = new JTextArea();

        panel.add(new JScrollPane(attributesArea));

        BaseDao<ProductCategory> dao = new BaseDao<>(ProductCategory.class);

        // Кнопки
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            model.setTitle(titleField.getText());

//            Map<String, Object> attributes = new HashMap<>();
//            attributes.put("tags", new String(attributesArea.getText()));
//            model.setAttributes(attributes);
            model.setAttributes(attributesArea.getText());

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